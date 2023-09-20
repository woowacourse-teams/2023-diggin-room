package com.digginroom.digginroom.oauth.jwk;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.*;
import com.digginroom.digginroom.oauth.payload.GoogleIdTokenPayload;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
import com.digginroom.digginroom.oauth.payload.KakaoIdTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ThirdPartyJwkProviders {

    private final static Map<Provider, Function<Map<String, Claim>, IdTokenPayload>> PAYLOAD_CONSTRUCTORS = Map.of(
            Provider.KAKAO, KakaoIdTokenPayload::new,
            Provider.GOOGLE, GoogleIdTokenPayload::new
    );

    private final List<ThirdPartyJwkProvider> thirdPartyJwkProviders;

    public void verify(final String rawIdToken) {
        try {
            DecodedJWT decoded = JWT.decode(rawIdToken);
            Provider provider = Provider.of(decoded.getIssuer())
                    .orElseThrow(() -> new UnsupportedIdTokenException(decoded.getIssuer()));

            Jwk jwk = getJwkProviderFor(provider).getJwkBy(decoded.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(rawIdToken);
        } catch (InvalidPublicKeyException e) {
            throw new IdTokenNotReadableException();
        } catch (JWTDecodeException e) {
            throw new InvalidIdTokenException();
        } catch (TokenExpiredException e) {
            throw new ExpireIdTokenException();
        }
    }

    private ThirdPartyJwkProvider getJwkProviderFor(final Provider provider) {
        return thirdPartyJwkProviders.stream()
                .filter(it -> it.supports(provider))
                .findFirst()
                .orElseThrow(() -> new UnsupportedProviderException(provider.name()));
    }

    public IdTokenPayload resolve(final String rawIdToken) {
        DecodedJWT decoded = JWT.decode(rawIdToken);
        Provider provider = Provider.of(decoded.getIssuer())
                .orElseThrow(() -> new UnsupportedIdTokenException(decoded.getIssuer()));
        return getConstructorFor(provider).apply(
                decoded.getClaims()
        );
    }

    private Function<Map<String, Claim>, IdTokenPayload> getConstructorFor(final Provider provider) {
        if (PAYLOAD_CONSTRUCTORS.containsKey(provider)) {
            return PAYLOAD_CONSTRUCTORS.get(provider);
        }
        throw new UnsupportedProviderException(provider.name());
    }
}
