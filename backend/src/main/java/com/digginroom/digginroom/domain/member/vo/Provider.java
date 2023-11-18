package com.digginroom.digginroom.domain.member.vo;

import java.util.Arrays;
import java.util.Optional;

public enum Provider {

    SELF(""),
    GOOGLE("https://accounts.google.com"),
    KAKAO("https://kauth.kakao.com");

    private final String issueUrl;

    Provider(final String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public static Optional<Provider> of(final String issuer) {
        return Arrays.stream(values())
                .filter(it -> it.matches(issuer))
                .findFirst();
    }

    private boolean matches(final String issueUrl) {
        if (this.issueUrl.isEmpty()) {
            return false;
        }
        return this.issueUrl.equals(issueUrl);
    }
}
