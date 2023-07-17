package com.digginroom.digginroom.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class MediaSource {

    private final MediaType mediaType;
    private final String identifier;

    public MediaSource(final MediaType mediaType, final String identifier) {
        validateNotNull(mediaType);
        validateNotNull(identifier);
        validateNotBlank(identifier);
        this.mediaType = mediaType;
        this.identifier = identifier;
    }

    private void validateNotNull(final MediaType mediaType) {
        if (Objects.isNull(mediaType)) {
            throw new IllegalArgumentException("미디어 타입이 지정되지 않았습니다");
        }
    }

    private void validateNotNull(final String identifier) {
        if (Objects.isNull(identifier)) {
            throw new IllegalArgumentException("식별자가 지정되지 않았습니다");
        }
    }

    private void validateNotBlank(final String identifier) {
        if (identifier.isBlank()) {
            throw new IllegalArgumentException("식별자는 공백일 수 없습니다");
        }
    }
}
