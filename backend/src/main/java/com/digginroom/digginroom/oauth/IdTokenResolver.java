package com.digginroom.digginroom.oauth;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.digginroom.digginroom.exception.OAuthResolverException.ExpireIdTokenException;
import com.digginroom.digginroom.exception.OAuthResolverException.IdTokenNotReadableException;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import com.digginroom.digginroom.oauth.cache.CachedJwkProviders;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
import com.digginroom.digginroom.oauth.payload.Issuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;

@Component
@RequiredArgsConstructor
public class IdTokenResolver {

    private final CachedJwkProviders cachedJwkProviders;

    public IdTokenPayload resolve(final String rawIdToken) {
        return verify(rawIdToken).resolve(rawIdToken);
    }

    private Issuer verify(final String rawIdToken) {
        try {
            DecodedJWT decoded = JWT.decode(rawIdToken);
            Issuer issuer = Issuer.of(decoded.getIssuer());

            JwkProvider jwkProvider = cachedJwkProviders.getJwkProviderFor(issuer);
            Jwk jwk = jwkProvider.get(decoded.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();

            verifier.verify(rawIdToken);
            return issuer;
        } catch (InvalidPublicKeyException e) {
            throw new IdTokenNotReadableException();
        } catch (JwkException | JWTDecodeException e) {
            throw new InvalidIdTokenException();
        } catch (TokenExpiredException e) {
            throw new ExpireIdTokenException();
        }
    }
}
