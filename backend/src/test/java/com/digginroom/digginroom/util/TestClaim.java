package com.digginroom.digginroom.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestClaim implements Claim {

    private final String value;

    public TestClaim(final String value) {
        this.value = value;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Boolean asBoolean() {
        return null;
    }

    @Override
    public Integer asInt() {
        return null;
    }

    @Override
    public Long asLong() {
        return null;
    }

    @Override
    public Double asDouble() {
        return null;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public Date asDate() {
        return null;
    }

    @Override
    public <T> T[] asArray(final Class<T> tClazz) throws JWTDecodeException {
        return null;
    }

    @Override
    public <T> List<T> asList(final Class<T> tClazz) throws JWTDecodeException {
        return null;
    }

    @Override
    public Map<String, Object> asMap() throws JWTDecodeException {
        return null;
    }

    @Override
    public <T> T as(final Class<T> tClazz) throws JWTDecodeException {
        return null;
    }
}
