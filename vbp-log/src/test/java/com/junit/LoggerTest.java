package com.junit;

import com.sgai.vbp.log.Logger;

public class LoggerTest {

	private static final  Logger logger = Logger.getLogger(LoggerTest.class);
	
	public static void main(String[] args) {
		logger.biz("adsfadsfads");
		logger.info("LoggerTest");
	}
}
