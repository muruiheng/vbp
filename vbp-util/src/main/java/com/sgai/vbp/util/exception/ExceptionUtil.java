package com.sgai.vbp.util.exception;

public class ExceptionUtil {

	/**
	 * throws CheckException 
	 * @param message
	 */
	public static void throwCheckException(String message) {
		throw new CheckException(message);
	}
	
	/**
	 * throws CheckException 
	 * @param t
	 */
	public static void throwCheckException(Throwable t) {
		throw new CheckException(t);
	}
	
	/**
	 * throws CheckException 
	 * @param message
	 * @param t
	 */
	public static void throwCheckException(String message, Throwable t) {
		throw new CheckException(message, t);
	}
	
}
