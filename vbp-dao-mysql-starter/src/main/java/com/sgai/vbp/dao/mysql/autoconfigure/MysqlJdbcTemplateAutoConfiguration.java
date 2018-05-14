package com.sgai.vbp.dao.mysql.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgai.vbp.dao.mysql.MysqlVbpJdbcTemplate;
import com.sgai.vbp.dao.mysql.datasource.MysqlDataSource;

@Configuration
@ConditionalOnClass(MysqlVbpJdbcTemplate.class)
public class MysqlJdbcTemplateAutoConfiguration {
	
	@Autowired
	private MysqlDataSource mysqlDataSource;
	
	@Bean
	public MysqlVbpJdbcTemplate mysqlJdbcTemplate() {
		MysqlVbpJdbcTemplate jdbcTemplate = new MysqlVbpJdbcTemplate();
		jdbcTemplate.setDataSource(mysqlDataSource);
		return jdbcTemplate;
	}
}
