package com.digginroom.digginroom.util;

public interface PasswordEncoder {

    String encode(final String plainText);

    boolean matches(final String plainText, final String encoded);
}
