package com.digginroom.digginroom.controller.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

@Scope("prototype")
@Component
public class MockLoginServer {

    private static final String HOST = "http://localhost:";
    private static final String PATH = "/login/guest";

    private final WebClient.Builder webClientBuilder;
    private final List<MockLogin> mockLogins;

    public MockLoginServer(final Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        this.mockLogins = new ArrayList<>();
    }

    public String getCachedLoginValue(final int port) {
        if (mockLogins.isEmpty()) {
            addMockLogin(port);
        }
        return mockLogins.get(0).getLoginValue();
    }

    private void addMockLogin(final int port) {
        this.mockLogins.add(webClientBuilder.baseUrl(HOST + port + PATH)
                .build()
                .post()
                .exchangeToMono(response -> Mono.just(new MockSessionLogin(response)))
                .block());
    }

    public List<String> getCachedLoginValues(final int port, final int size) {
        validateSize(size);

        while (mockLogins.size() < size) {
            addMockLogin(port);
        }

        return mockLogins.stream()
                .map(MockLogin::getLoginValue)
                .collect(Collectors.toList());
    }

    private void validateSize(final int size) {
        if (size >= 10) {
            throw new IllegalArgumentException("사이즈의 입력이 너무 많습니다.");
        }
    }
}

