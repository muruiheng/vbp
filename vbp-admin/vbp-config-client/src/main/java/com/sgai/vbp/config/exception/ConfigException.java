package com.sgai.vbp.config.exception;

public class ConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 997449338047969302L;

	public ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}
}
