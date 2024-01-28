package com.digginroom.digginroom.controller.mock;

import org.springframework.web.reactive.function.client.ClientResponse;

public class MockSessionLogin implements MockLogin {

    private static final String SESSION = "SESSION";

    private final String sessionId;

    public MockSessionLogin(final ClientResponse response) {
        this.sessionId = response.cookies()
                .get(SESSION)
                .get(0)
                .toString();
    }

    @Override
    public String getLoginValue() {
        return sessionId;
    }

    @Override
    public String toString() {
        return "MockSessionLogin{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }
}
