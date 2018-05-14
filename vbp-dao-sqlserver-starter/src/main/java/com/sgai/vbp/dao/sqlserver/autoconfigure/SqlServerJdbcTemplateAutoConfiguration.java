package com.sgai.vbp.dao.sqlserver.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.sgai.vbp.dao.datasource.VbpDruidDatasource;
import com.sgai.vbp.dao.sqlserver.SqlServerVbpJdbcTemplate;

@Configuration
@EnableAutoConfiguration
public class SqlServerJdbcTemplateAutoConfiguration {

	@Autowired
	private VbpDruidDatasource sqlServerDataSource;
	
	@Bean
	@Primary
	@ConditionalOnClass(SqlServerVbpJdbcTemplate.class)
	public SqlServerVbpJdbcTemplate oracleJdbcTemplate() {
		SqlServerVbpJdbcTemplate jdbcTemplate = new SqlServerVbpJdbcTemplate();
		jdbcTemplate.setDataSource(sqlServerDataSource);
		return jdbcTemplate;
	}
}
