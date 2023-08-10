package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    public static final Password EMPTY = new Password();

    private static final String EMPTY_PASSWORD = "{EMPTY}";

    private String value = EMPTY_PASSWORD;

    public Password(final String value) {
        this.value = DigginRoomPasswordEncoder.encode(value);
    }

    public boolean doesNotMatch(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, value);
    }

    public boolean isEmpty() {
        return value.equals(EMPTY_PASSWORD);
    }
}
