package com.sgai.vbp.umc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.sgai.vbp.config.client.DefaultProfileUtil;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class UmcApplicationStarter {

	/**
	 * 用户管理系统启动界面
	 * @param args
	 */
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UmcApplicationStarter.class);
		DefaultProfileUtil.addDefaultProfile(app);
		app.run(args);
	}
}
