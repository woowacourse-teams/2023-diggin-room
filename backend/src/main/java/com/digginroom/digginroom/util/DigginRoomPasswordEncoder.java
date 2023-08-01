package com.digginroom.digginroom.util;

import com.digginroom.digginroom.util.HashAlgorithm;
import java.util.UUID;

public class DigginRoomPasswordEncoder {

    private static final int SALT_LENGTH = 36;
    private static final int SALT_START_INDEX = 0;

    public static String encode(final String plainText) {
        UUID salt = UUID.randomUUID();
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return salt + hashed;
    }

    public static boolean matches(final String plainText, final String encoded) {
        String salt = encoded.substring(SALT_START_INDEX, SALT_LENGTH);
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return (salt + hashed).equals(encoded);
    }
}
