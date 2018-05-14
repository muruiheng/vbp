package com.sgai.vbp.dao;

import com.alibaba.druid.filter.config.ConfigTools;

public class EncryptUtil {
	private static final String PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMPPXlBr9rPk6LIuXELF/VvodiDpjf5ui6JObTWuRuwYO39AFjhogrnI1UCJsjk74pQYRPJ6MW26Egv5U4+7jN9InyxJynLsehdyVs7PyigcHmPVunEqWASgH48DCmYhw+PGJ6yhgEoilADqe7g2hoTxbaqZ+8bLQ7DyCQONZ4HnAgMBAAECgYBXoayh7U+BfwatB5Z+w8D5qvWDEUNdBPg08Uhq584Rx16JOpM3N36Zysm1kmpStvxWLu5cXldUthtxhGnjP5pxYY3aszZY10IsO3jxsFc5Fuk/Wrrr0WrU9hV+Bq2Vluhn1G+cl7d5T9NimwBWyJsXTaiU7LoyNfNXj3rTpRQF0QJBAPqzKKujPasc3StAii1Ehrq1Daymrgmyt36USCrTFYEdqDa4N0AYUv9o9Vs7UWEB17116bySEgW3IMy0xqGo5CUCQQDH8yJZc9vzf8vH7GE3qaF3dMStjWl3w/yPp+eMRCAubmruEnVnYCu4+Ve4EvtPoLZp22qVUzs88CyedN8ylgobAkBEWNGusWtfmhfl8ct5+eOtRSz8M9jgxftmo/Bk1HVZZQd2G5TcgYUnIqs5ext9ke4KNfpKMZx+OGXVyTcZ3ZyNAkBz+oWMGXYVkaUkO+pQHSa5YZPLj2EDxTy0HxJh/AompImFulM7OT9jNc45yVjYYUItuMXkA7kyqaVX7Dai4wDNAkEAobtEsnsxftsJtCOiarRAHgwsb8/QInnA+KHqsseoQy5UEmiWxuzZgoE1Vx8YNiY8vdYPHPXrfEU6vJta7PXLaQ==";
	public static final String PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDz15Qa/az5OiyLlxCxf1b6HYg6Y3+bouiTm01rkbsGDt/QBY4aIK5yNVAibI5O+KUGETyejFtuhIL+VOPu4zfSJ8sScpy7HoXclbOz8ooHB5j1bpxKlgEoB+PAwpmIcPjxiesoYBKIpQA6nu4NoaE8W2qmfvGy0Ow8gkDjWeB5wIDAQAB";
	/**
	 * 数据源加密
	 * @param plainText
	 * @return
	 */
	public static String encrypt(String plainText) {
		try {
			return ConfigTools.encrypt(PRIVATE_KEY_STRING, plainText);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 解密算法
	 * @param cipherText
	 * @return
	 */
	public static String decrypt(String cipherText) {
		try {
			return ConfigTools.decrypt(PUBLIC_KEY_STRING, cipherText);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 工具类方法，用来生成用户名密码的加密数据
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args==null || args.length < 2) {
			throw new IllegalArgumentException("请输入用户名及密码！格式：\"username\" \"password\"");
		}
		String userName = args[0];
		
		
		System.out.println("加密后的用户名：" + encrypt(userName));
		String password = args[1];
		
		System.out.println("加密后的密码：" + encrypt(password));
	}
}
