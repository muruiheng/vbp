package com.sgai.vbp.config.client;

/**
 * 
 * @author mrh
 *
 */
public class Constants {
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev"; 
	public static final String SPRING_PROFILE_PRODUCTION = "prod"; 
	private Constants() {}

	
	/**
	 * Zookeeper session timeout
	 */
	public static int ZK_SESSION_TIMEOUT = 5000;
	
	public static final String ZK_SPLITOR = "/";
	
	public static final String ZK_CONFIG_PATH = "/com/sgai/vbp";
	
	public static final String ZK_CONFIG_ENV_PATH = "/com/sgai/vbp/env";
}
