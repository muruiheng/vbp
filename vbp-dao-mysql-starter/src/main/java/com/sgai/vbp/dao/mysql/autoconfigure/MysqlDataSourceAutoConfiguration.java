package com.sgai.vbp.dao.mysql.autoconfigure;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgai.vbp.dao.EncryptUtil;
import com.sgai.vbp.dao.mysql.datasource.MysqlDataSource;

@Configuration
@ConditionalOnClass(MysqlDataSource.class)
public class MysqlDataSourceAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(MysqlDataSourceAutoConfiguration.class);
	@Value("${spring.datasource.mysql.url}")
	private String dbUrl;

	@Value("${spring.datasource.mysql.username}")
	private String username;

	@Value("${spring.datasource.mysql.password}")
	private String password;

	@Value("${spring.datasource.mysql.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.mysql.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.mysql.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.mysql.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.mysql.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.mysql.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.mysql.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.mysql.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.mysql.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.mysql.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.mysql.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.mysql.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.mysql.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.mysql.filters}")
	private String filters;

	@Value("{spring.datasource.mysql.connectionProperties}")
	private String connectionProperties;

	@Value("${spring.datasource.mysql.logSlowSql}")
	private String logSlowSql;

	@Value("${spring.datasource.mysql.profileEnable}")
	private String profileEnable;

	@Value("${spring.datasource.mysql.exclusions}")
	private String exclusions;

	@Value("${spring.datasource.mysql.urlPattern}")
	private String urlPattern;
	
	@Value("${spring.datasource.mysql.encrypt}")
	private boolean encrypt;

	@Bean // 声明其为Bean实例
	public MysqlDataSource mysqlDataSource() {
		MysqlDataSource datasource = new MysqlDataSource();

		datasource.setUrl(this.dbUrl);
		if (encrypt) {
			datasource.setUsername(EncryptUtil.decrypt(username));
			datasource.setPassword(EncryptUtil.decrypt(password));
		} else {
			datasource.setUsername(username);
			datasource.setPassword(password);
		}
		datasource.setDriverClassName(driverClassName);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		datasource.setConnectionProperties(connectionProperties);
		try {
			datasource.setFilters(filters);
			datasource.init();
		} catch (SQLException e) {
			logger.error("mysql druid  datasource configuration initialization filter", e);
		}
		return datasource;
	}
}