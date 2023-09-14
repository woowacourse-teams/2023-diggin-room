package com.digginroom.digginroom.domain.member;

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

    private String password = EMPTY_PASSWORD;

    public Password(final String password) {
        this.password = DigginRoomPasswordEncoder.encode(password);
    }

    public boolean doesNotMatch(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, this.password);
    }

    public boolean isEmpty() {
        return password.equals(EMPTY_PASSWORD);
    }
}
