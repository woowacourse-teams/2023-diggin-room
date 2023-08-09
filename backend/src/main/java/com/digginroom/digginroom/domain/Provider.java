package com.digginroom.digginroom.domain;

public enum Provider {

    SELF("{self}"),
    GOOGLE("{google}");

    private final String identifier;

    Provider(final String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
