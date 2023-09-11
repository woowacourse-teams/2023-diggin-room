package com.digginroom.digginroom.oauth.kakao;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.digginroom.digginroom.exception.OAuthResolverException.IdTokenNotReadableException;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import com.digginroom.digginroom.oauth.IdTokenVerifier;
import java.security.interfaces.RSAPublicKey;
import org.springframework.stereotype.Component;

@Component
public class KakaoIdTokenVerifier implements IdTokenVerifier {

    private final JwkProvider jwkProvider;

    public KakaoIdTokenVerifier(final JwkProvider jwkProvider) {
        this.jwkProvider = jwkProvider;
    }

    @Override
    public DecodedJWT verify(final String idToken) {
        DecodedJWT jwtOrigin = JWT.decode(idToken);
        try {
            Jwk jwk = jwkProvider.get(jwtOrigin.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(idToken);
        } catch (InvalidPublicKeyException e) {
            throw new IdTokenNotReadableException();
        } catch (JwkException e) {
            throw new InvalidIdTokenException();
        }
    }
}
