package com.sgai.vbp.config;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.sgai.vbp.config.client.DefaultProfileUtil;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CfgApplicationStarter {

	/**
	 * 用户管理系统启动界面
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CfgApplicationStarter.class);
		app.setBannerMode(Mode.OFF);
		DefaultProfileUtil.addDefaultProfile(app);
		app.run(args);
	}
}
