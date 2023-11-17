package com.digginroom.digginroom.util;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        SpringExtension.getApplicationContext(context)
                .getBean(DatabaseCleaner.class)
                .clear();
    }
}
