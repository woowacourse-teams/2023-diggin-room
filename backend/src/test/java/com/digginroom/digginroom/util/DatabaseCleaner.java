package com.digginroom.digginroom.util;

import jakarta.annotation.PostConstruct;

public interface DatabaseCleaner {

    @PostConstruct
    void setUpDatabaseTableNames();

    void clear();
}
