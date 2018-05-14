/*
 * #{copyright}#
 */
package com.sgai.vbp.security.encrypt;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;

/**
 * @author
 * @Date
 */
final class HexEncodingTextEncryptor implements TextEncryptor {

    private final BytesEncryptor encryptor;

    HexEncodingTextEncryptor(BytesEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encrypt(String text) {
        return new String(Hex.encode(this.encryptor.encrypt(Utf8.encode(text))));
    }

    public String decrypt(String encryptedText) {
        return Utf8.decode(this.encryptor.decrypt(Hex.decode(encryptedText)));
    }

}
