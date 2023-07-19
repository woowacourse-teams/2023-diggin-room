package com.digginroom.digginroom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;

    public MediaSource(final String identifier) {
        validateNotNull(identifier);
        validateNotBlank(identifier);
        this.identifier = identifier;
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
