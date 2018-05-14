package com.sgai.vbp.bi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sgai.vbp.config.client.DefaultProfileUtil;


@SpringBootApplication
public class BiApplicationStarter {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BiApplicationStarter.class);
		DefaultProfileUtil.addDefaultProfile(app);
		app.run(args);
		
	}
}
