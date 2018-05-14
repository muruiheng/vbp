package com.sgai.vbp.dao.sqlserver.autoconfigure;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.sgai.vbp.dao.sqlserver.datasource.SqlServerDataSource;

@Configuration
public class SqlServerDataSourceAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(SqlServerDataSourceAutoConfiguration.class);
	@Value("${spring.datasource.sqlserver.url}")
	private String dbUrl;

	@Value("${spring.datasource.sqlserver.username}")
	private String username;

	@Value("${spring.datasource.sqlserver.password}")
	private String password;

	@Value("${spring.datasource.sqlserver.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.sqlserver.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.sqlserver.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.sqlserver.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.sqlserver.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.sqlserver.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.sqlserver.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.sqlserver.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.sqlserver.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.sqlserver.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.sqlserver.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.sqlserver.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.sqlserver.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.sqlserver.filters}")
	private String filters;

	@Value("{spring.datasource.sqlserver.connectionProperties}")
	private String connectionProperties;

	@Value("${spring.datasource.sqlserver.logSlowSql}")
	private String logSlowSql;

	@Value("${spring.datasource.sqlserver.profileEnable}")
	private String profileEnable;

	@Value("${spring.datasource.sqlserver.exclusions}")
	private String exclusions;

	@Value("${spring.datasource.sqlserver.urlPattern}")
	private String urlPattern;

	@Bean // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	@ConditionalOnClass(SqlServerDataSource.class)
	public SqlServerDataSource msyqlDataSource() {
		SqlServerDataSource datasource = new SqlServerDataSource();

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