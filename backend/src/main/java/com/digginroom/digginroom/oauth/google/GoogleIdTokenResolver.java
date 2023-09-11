package com.digginroom.digginroom.oauth.google;

import com.digginroom.digginroom.exception.OAuthResolverException.IdTokenNotReadableException;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import com.digginroom.digginroom.oauth.OAuthIdTokenResolver;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Component;

@Component
public class GoogleIdTokenResolver implements OAuthIdTokenResolver {

    private static final NetHttpTransport NET_HTTP_TRANSPORT = new NetHttpTransport();

    @Override
    public String resolve(final String idToken) {
        GsonFactory defaultInstance = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier tokenVerifier = new GoogleIdTokenVerifier(NET_HTTP_TRANSPORT, defaultInstance);
        try {
            GoogleIdToken googleIdToken = GoogleIdToken.parse(defaultInstance, idToken);
            if (tokenVerifier.verify(googleIdToken)) {
                return googleIdToken.getPayload().getSubject();
            }
            throw new InvalidIdTokenException();
        } catch (IllegalArgumentException e) {
            throw new InvalidIdTokenException();
        } catch (IOException | GeneralSecurityException e) {
            throw new IdTokenNotReadableException();
        }
    }
}
