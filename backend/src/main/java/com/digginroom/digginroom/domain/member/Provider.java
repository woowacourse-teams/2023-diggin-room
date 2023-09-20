package com.digginroom.digginroom.domain.member;

import java.util.Arrays;
import java.util.Optional;

public enum Provider {

    SELF(""),
    GOOGLE("https://accounts.google.com"),
    KAKAO("https://kauth.kakao.com");

    private final String issueUrl;

    Provider(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public static Optional<Provider> of(String issuer) {
        return Arrays.stream(values())
                .filter(it -> it.matches(issuer))
                .findFirst();
    }

    private boolean matches(String issueUrl) {
        if (this.issueUrl.isEmpty()) {
            return false;
        }
        return this.issueUrl.equals(issueUrl);
    }
}
