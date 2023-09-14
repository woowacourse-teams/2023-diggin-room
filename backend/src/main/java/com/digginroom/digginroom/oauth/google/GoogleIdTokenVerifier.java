package com.digginroom.digginroom.oauth.google;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.digginroom.digginroom.exception.OAuthVerifierException.ExpireIdTokenException;
import com.digginroom.digginroom.exception.OAuthVerifierException.IdTokenNotReadableException;
import com.digginroom.digginroom.exception.OAuthVerifierException.InvalidIdTokenException;
import com.digginroom.digginroom.oauth.IdTokenVerifier;
import com.digginroom.digginroom.oauth.JwkUrl;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class GoogleIdTokenVerifier implements IdTokenVerifier {

    private static final JwkUrl url = JwkUrl.of("https://www.googleapis.com/oauth2/v3/certs");

    private static final JwkProvider jwkProvider = new JwkProviderBuilder(url.url())
            .cached(10, 7, TimeUnit.DAYS)
            .build();

    @Override
    public DecodedJWT verify(final String idToken) {
        try {
            DecodedJWT jwtOrigin = JWT.decode(idToken);
            Jwk jwk = jwkProvider.get(jwtOrigin.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(idToken);
        } catch (InvalidPublicKeyException e) {
            throw new IdTokenNotReadableException();
        } catch (JwkException | JWTDecodeException e) {
            throw new InvalidIdTokenException();
        } catch (TokenExpiredException e) {
            throw new ExpireIdTokenException();
        }
    }
}
