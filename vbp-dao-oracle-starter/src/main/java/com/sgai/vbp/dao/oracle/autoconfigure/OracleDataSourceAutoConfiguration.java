package com.sgai.vbp.dao.oracle.autoconfigure;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.sgai.vbp.dao.oracle.datasource.OracleDataSource;

@Configuration
@ConditionalOnClass(OracleDataSource.class)
public class OracleDataSourceAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(OracleDataSourceAutoConfiguration.class);
	@Value("${spring.datasource.oracle.url}")
	private String dbUrl;

	@Value("${spring.datasource.oracle.username}")
	private String username;

	@Value("${spring.datasource.oracle.password}")
	private String password;

	@Value("${spring.datasource.oracle.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.oracle.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.oracle.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.oracle.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.oracle.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.oracle.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.oracle.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.oracle.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.oracle.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.oracle.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.oracle.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.oracle.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.oracle.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.oracle.filters}")
	private String filters;

	@Value("{spring.datasource.oracle.connectionProperties}")
	private String connectionProperties;

	@Value("${spring.datasource.oracle.logSlowSql}")
	private String logSlowSql;

	@Value("${spring.datasource.oracle.profileEnable}")
	private String profileEnable;

	@Value("${spring.datasource.oracle.exclusions}")
	private String exclusions;

	@Value("${spring.datasource.oracle.urlPattern}")
	private String urlPattern;

	@Bean // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	@ConditionalOnClass(OracleDataSource.class)
	public OracleDataSource msyqlDataSource() {
		OracleDataSource datasource = new OracleDataSource();

		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
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