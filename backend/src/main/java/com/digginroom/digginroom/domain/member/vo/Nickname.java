package com.digginroom.digginroom.domain.member.vo;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    private String nickname;

    public static Nickname of(final String nickname) {
        return new Nickname(nickname);
    }

    public static Nickname random() {
        return new Nickname("user-" + UUID.randomUUID().toString().split("-")[0]);
    }
}
