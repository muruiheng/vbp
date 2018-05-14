package com.sgai.vbp.cache;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@EnableCaching
public class RedisCacheAutoconfigure extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.sentinel.nodes}")
	private String redisNodes;

	@Value("${spring.redis.sentinel.master}")
	private String master;

	/**
	 * redis哨兵配置
	 * 
	 * @return
	 */
	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration() {
		RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
		String[] host = redisNodes.split(",");
		for (String redisHost : host) {
			String[] item = redisHost.split(":");
			String ip = item[0];
			String port = item[1];
			configuration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
		}
		configuration.setMaster(master);
		return configuration;
	}

	/**
	 * 连接redis的工厂类
	 *
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration());
		return factory;
	}

	/**
	 * 配置RedisTemplate 设置添加序列化器 key 使用string序列化器 value 使用Json序列化器
	 * 还有一种简答的设置方式，改变defaultSerializer对象的实现。
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name="redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		// StringRedisTemplate的构造方法中默认设置了stringSerializer
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		 //设置开启事务
        template.setEnableTransactionSupport(true);
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
       
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
		return template;
	}
	
	@Bean
	@ConditionalOnMissingBean(StringRedisTemplate.class)
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		// StringRedisTemplate的构造方法中默认设置了stringSerializer
		StringRedisTemplate template = new StringRedisTemplate();
		 //设置开启事务
        template.setEnableTransactionSupport(true);
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
		return template;
	}


	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory ) {
		RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
		return redisCacheManager;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName()).append(".");
                sb.append(method.getName()).append(".");
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
	}

}
