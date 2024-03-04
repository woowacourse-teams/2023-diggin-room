package com.digginroom.digginroom.config;

import java.util.Arrays;

public enum DatabaseTableName {

    MEMBER("member"),
    MEMBER_GENRE("member_genre"),
    SPRING_SESSION("spring_session"),
    SPRING_SESSION_ATTRIBUTES("spring_session_attributes"),
    EMPTY("");

    private final String name;

    DatabaseTableName(final String name) {
        this.name = name;
    }

    public static DatabaseTableName of(final String name) {
        return Arrays.stream(values())
                .filter(databaseTableName -> databaseTableName.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(EMPTY);
    }

    public String getName() {
        return name;
    }
}
