package com.digginroom.digginroom.config;

import static com.digginroom.digginroom.config.DatabaseTableName.MEMBER;
import static com.digginroom.digginroom.config.DatabaseTableName.MEMBER_GENRE;
import static com.digginroom.digginroom.config.DatabaseTableName.SPRING_SESSION;
import static com.digginroom.digginroom.config.DatabaseTableName.SPRING_SESSION_ATTRIBUTES;

import java.util.Set;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class ControllerTestConfig {

    @Bean
    @Profile("default")
    public DatabaseCleaner getDatabaseCleanerBean() {
        return new DatabaseCleaner(Set.of());
    }

    @Bean
    @Profile("auth")
    public DatabaseCleaner getAuthDatabaseCleanerBean() {
        return new DatabaseCleaner(Set.of(MEMBER, MEMBER_GENRE, SPRING_SESSION, SPRING_SESSION_ATTRIBUTES));
    }

    @Bean
    @Profile("auth")
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
