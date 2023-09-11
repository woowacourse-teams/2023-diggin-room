package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkException;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface IdTokenVerifier {

    DecodedJWT verify(String idToken) throws JwkException;
}
