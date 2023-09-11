package com.digginroom.digginroom.oauth;

public interface OAuthIdTokenResolver {
    String resolve(String idToken);
}
