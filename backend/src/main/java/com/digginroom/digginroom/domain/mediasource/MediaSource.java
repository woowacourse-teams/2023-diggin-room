package com.digginroom.digginroom.domain.mediasource;

import static com.digginroom.digginroom.exception.MediaSourceException.NoIdentifierException;

import com.digginroom.digginroom.domain.BaseEntity;
import jakarta.persistence.Entity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaSource extends BaseEntity {

    private String identifier;

    public MediaSource(final String identifier) {
        validateNotNull(identifier);
        validateNotBlank(identifier);
        this.identifier = identifier;
    }

    private void validateNotNull(final String identifier) {
        if (Objects.isNull(identifier)) {
            throw new NoIdentifierException();
        }
    }

    private void validateNotBlank(final String identifier) {
        if (identifier.isBlank()) {
            throw new NoIdentifierException();
        }
    }
}
