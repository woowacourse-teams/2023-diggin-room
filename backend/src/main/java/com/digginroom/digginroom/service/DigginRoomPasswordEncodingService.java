package com.digginroom.digginroom.service;

import com.digginroom.digginroom.util.HashAlgorithm;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DigginRoomPasswordEncodingService {

    private static final int SALT_LENGTH = 36;
    private static final int SALT_START_INDEX = 0;

    public String encode(String plainText) {
        UUID salt = UUID.randomUUID();
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return salt + hashed;
    }

    public boolean matches(String plainText, String encoded) {
        String salt = encoded.substring(SALT_START_INDEX, SALT_LENGTH);
        String hashed = HashAlgorithm.encrypt(salt + plainText);
        return (salt + hashed).equals(encoded);
    }
}
