/*
 * #{copyright}#
 */
package com.sgai.vbp.security.encrypt;

/**
 * @author
 * @Date
 */
public interface BytesEncryptor {
	
	/**
	 * 加密
	 * @param paramArrayOfByte
	 * @return
	 */
    byte[] encrypt(byte[] paramArrayOfByte);

    /**
     * 解密
     * @param paramArrayOfByte
     * @return
     */
    byte[] decrypt(byte[] paramArrayOfByte);
}
