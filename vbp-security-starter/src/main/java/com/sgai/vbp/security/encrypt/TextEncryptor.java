/*
 * #{copyright}#
 */
package com.sgai.vbp.security.encrypt;

/**
 * @author
 * @Date
 */
public interface TextEncryptor {
    String encrypt(String paramString);

    String decrypt(String paramString);
}
