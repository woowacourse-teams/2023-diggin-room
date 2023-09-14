package com.digginroom.digginroom.oauth;

import com.digginroom.digginroom.exception.OAuthVerifierException.InvalidJwkUrlException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Getter;

@Getter
public record JwkUrl(URL url) {

    public static JwkUrl of(String url) {
        try {
            return new JwkUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new InvalidJwkUrlException();
        }
    }
}
