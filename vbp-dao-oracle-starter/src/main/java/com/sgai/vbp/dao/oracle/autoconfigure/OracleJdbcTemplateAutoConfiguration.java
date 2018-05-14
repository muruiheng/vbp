package com.sgai.vbp.dao.oracle.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.sgai.vbp.dao.datasource.VbpDruidDatasource;
import com.sgai.vbp.dao.oracle.OracleVbpJdbcTemplate;

@Configuration
@EnableAutoConfiguration
public class OracleJdbcTemplateAutoConfiguration {
	
	@Autowired
	private VbpDruidDatasource oracleDataSource;
	
	@Bean
	@Primary
	@ConditionalOnClass(OracleVbpJdbcTemplate.class)
	public OracleVbpJdbcTemplate oracleJdbcTemplate() {
		OracleVbpJdbcTemplate jdbcTemplate = new OracleVbpJdbcTemplate();
		jdbcTemplate.setDataSource(oracleDataSource);
		return jdbcTemplate;
	}
}
