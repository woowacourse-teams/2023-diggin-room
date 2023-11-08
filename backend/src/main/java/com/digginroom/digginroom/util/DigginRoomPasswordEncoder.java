package com.digginroom.digginroom.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DigginRoomPasswordEncoder implements PasswordEncoder {

    private static final int SALT_LENGTH = 36;
    private static final int SALT_START_INDEX = 0;

    @Override
    public String encode(final String plainText) {
        UUID salt = UUID.randomUUID();
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return salt + hashed;
    }

    @Override
    public boolean matches(final String plainText, final String encoded) {
        String salt = encoded.substring(SALT_START_INDEX, SALT_LENGTH);
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return (salt + hashed).equals(encoded);
    }
}
