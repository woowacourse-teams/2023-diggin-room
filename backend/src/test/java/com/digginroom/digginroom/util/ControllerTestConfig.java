package com.digginroom.digginroom.util;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ControllerTestConfig {

    @Bean
    @Profile("default")
    public DatabaseCleaner getDatabaseCleanerBean() {
        return new DatabaseCleaner(Set.of());
    }

    @Bean
    @Profile("auth")
    public DatabaseCleaner getAuthDatabaseCleanerBean() {
        return new DatabaseCleaner(Set.of(DatabaseTableName.MEMBER));
    }
}
