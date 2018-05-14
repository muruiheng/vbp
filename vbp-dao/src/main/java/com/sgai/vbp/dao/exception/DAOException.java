package com.sgai.vbp.dao.exception;

public class DAOException extends RuntimeException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3733765964078816175L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	
}
