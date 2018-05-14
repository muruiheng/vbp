package com.sgai.vbp.log;

import org.slf4j.LoggerFactory;


public class Logger {
	
		private final org.slf4j.Logger logger;
		private final org.slf4j.Logger loggerBiz;
		
		private Logger (Class<?> clazz) {
			logger = LoggerFactory.getLogger(clazz);
			loggerBiz = LoggerFactory.getLogger("BIZ");
		}
		public static Logger getLogger (Class<?> clazz) {
			return new Logger(clazz);
		}
		
		/**
		 * debug log
		 * @param message
		 */
		public void debug(String message) {
			if (logger.isDebugEnabled()) {
				logger.debug(message);
			}

		}
		
		/**
		 * 
		 * @param message
		 * @param t Throwable
		 */
		public void debug(String message, Throwable t) {
			if (logger.isDebugEnabled()) {
				logger.debug(message.toString(), t);
			}
		}
		
		/**
		 * 
		 * @param pattern String
		 * @param arguments Object...
		 */
		public void debug(String formatter, Object... arguments) {
			if (logger.isDebugEnabled()) {
				logger.debug(formatter,arguments);
			}

		}

		/**
		 * 
		 * @param message
		 */
		public void info(String message) {
			if (logger.isInfoEnabled()) {
				logger.info(message);
			}
		}
		
		/**
		 * 
		 * @param message
		 * @param t
		 */
		public void info(String message, Throwable t) {
			if (logger.isInfoEnabled()) {
				logger.info(message,t);
			}

		}

		
		/**
		 * 
		 * @param pattern
		 * @param arguments
		 */
		public void info(String formatter, Object... arguments) {
			if (logger.isInfoEnabled()) {
				logger.info(formatter, arguments);
			}

		}


		/**
		 * 
		 * @param message
		 */
		public void warn(String message) {
			if (logger.isWarnEnabled()) {
				logger.warn(message);
			}
		}

		/**
		 * 
		 * @param message
		 * @param t
		 */
		public void warn(String message, Throwable t) {
			if (logger.isWarnEnabled()) {
				logger.warn(message, t);
			}
		}

		/**
		 * 
		 * @param pattern
		 * @param arguments
		 */
		public void warn(String formatter, Object... arguments) {
			if (logger.isWarnEnabled()) {
				logger.warn(formatter, arguments);
			}
		}

		/**
		 * 
		 * @param message
		 */
		public void error(String message) {
			if (logger.isErrorEnabled()) {
				logger.error(message);
			}
		}

		/**
		 * 
		 * @param message
		 * @param t
		 */
		public void error(String message, Throwable t) {
			if (logger.isErrorEnabled()) {
				logger.error(message, t);
			}
		}
		

		/**
		 * 
		 * @param pattern
		 * @param arguments
		 */
		public void error(String formatter, Object... arguments) {
			if (logger.isErrorEnabled()) {
				logger.error(formatter, arguments);
			}
		}


		/**
		 * 输出业务日志
		 * @param message
		 */
		public void biz(String message) {
			loggerBiz.info(message);
		}
		
		
		/**
		 * 输出业务日志
		 * @param pattern
		 * @param arguments
		 */
		public void biz(String formatter, Object...arguments) {
			loggerBiz.info(formatter, arguments);
		}
		
}
