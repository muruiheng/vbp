package com.sgai.vbp.dao.hana.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgai.vbp.dao.EncryptUtil;
import com.sgai.vbp.dao.hana.datasource.HanaDataSource;

@Configuration
@ConditionalOnClass(HanaDataSource.class)
@ConditionalOnProperty(name="spring.datasource.hana.url")
public class HanaDataSourceAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(HanaDataSourceAutoConfiguration.class);
	@Value("${spring.datasource.hana.url}")
	private String dbUrl;

	@Value("${spring.datasource.hana.username}")
	private String username;

	@Value("${spring.datasource.hana.password}")
	private String password;

	@Value("${spring.datasource.hana.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.hana.schema}")
	private String schema;
	
	@Value("${spring.datasource.hana.encrypt}")
	private boolean encrypt;

	@Bean // 声明其为Bean实例
	public HanaDataSource hanaDataSource() {
		HanaDataSource datasource = new HanaDataSource();

		datasource.setUrl(this.dbUrl);
		if (encrypt) {
			datasource.setUser(EncryptUtil.decrypt(username));
			datasource.setPassword(EncryptUtil.decrypt(password));
		} else {
			datasource.setUser(username);
			datasource.setPassword(password);
		}
		datasource.setSchema(schema);
		logger.debug("HanaDataSource .....  ..   inited!");
		return datasource;
	}
}