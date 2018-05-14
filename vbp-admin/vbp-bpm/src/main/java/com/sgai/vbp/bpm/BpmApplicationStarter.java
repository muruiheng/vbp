package com.sgai.vbp.bpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sgai.vbp.config.client.DefaultProfileUtil;

@SpringBootApplication
public class BpmApplicationStarter {

	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BpmApplicationStarter.class);
		DefaultProfileUtil.addDefaultProfile(app);
		app.run(args);
	}
}
