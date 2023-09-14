package com.digginroom.digginroom.oauth;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.ExpireIdTokenException;
import com.digginroom.digginroom.exception.OAuthResolverException.IdTokenNotReadableException;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdTokenResolver {

    private final MyJwkProviders myJwkProviders;

    public Map<String, Claim> resolve(final String idToken, final Provider provider) {
        JwkProvider jwkProvider = myJwkProviders.getJwkProvider(provider);

        try {
            DecodedJWT jwtOrigin = JWT.decode(idToken);
            Jwk jwk = jwkProvider.get(jwtOrigin.getKeyId());

            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(idToken).getClaims();
        } catch (InvalidPublicKeyException e) {
            throw new IdTokenNotReadableException();
        } catch (JwkException | JWTDecodeException e) {
            throw new InvalidIdTokenException();
        } catch (TokenExpiredException e) {
            throw new ExpireIdTokenException();
        }
    }
}
