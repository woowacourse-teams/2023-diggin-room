package com.digginroom.digginroom.service;

public interface OAuthUsernameResolver {
    String resolve(String idToken);
}
