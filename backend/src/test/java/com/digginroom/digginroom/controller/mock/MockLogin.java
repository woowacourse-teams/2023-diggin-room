package com.digginroom.digginroom.controller.mock;

import org.springframework.web.reactive.function.client.ClientResponse;

public class MockLogin {

    private final String sessionId;

    public MockLogin(final ClientResponse response) {
        this.sessionId = response.cookies().get("SESSION").get(0).getValue();
    }
}
