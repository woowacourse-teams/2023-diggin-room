package com.digginroom.digginroom.util;

import com.digginroom.digginroom.exception.EncryptException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm {

    private static final String ALGORITHM = "SHA-256";
    private static final String BYTE_TO_HEX_FORMAT = "%02x";

    public static String encrypt(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(text.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte singleByte : bytes) {
            builder.append(String.format(BYTE_TO_HEX_FORMAT, singleByte));
        }
        return builder.toString();
    }
}
