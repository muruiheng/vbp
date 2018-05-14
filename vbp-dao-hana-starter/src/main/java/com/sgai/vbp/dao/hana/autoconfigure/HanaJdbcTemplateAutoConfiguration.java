package com.sgai.vbp.dao.hana.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgai.vbp.dao.hana.HanaVbpJdbcTemplate;
import com.sgai.vbp.dao.hana.datasource.HanaDataSource;

@Configuration
@ConditionalOnClass(HanaVbpJdbcTemplate.class)
public class HanaJdbcTemplateAutoConfiguration {
	
	@Autowired
	private HanaDataSource hanaDataSource;
	
	@Bean
	public HanaVbpJdbcTemplate hanaJdbcTemplate() {
		HanaVbpJdbcTemplate jdbcTemplate = new HanaVbpJdbcTemplate();
		jdbcTemplate.setDataSource(hanaDataSource);
		return jdbcTemplate;
	}
}
