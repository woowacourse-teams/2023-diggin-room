package com.digginroom.digginroom.domain.member;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.member.vo.Nickname;
import com.digginroom.digginroom.domain.member.vo.Password;
import com.digginroom.digginroom.domain.member.vo.Provider;
import com.digginroom.digginroom.domain.room.Room;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @NonNull
    private String username;
    @Embedded
    private Password password;
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    @Embedded
    private Nickname nickname;
    @Embedded
    private final ScrapRooms scrapRooms = new ScrapRooms();

    private Member(final String username, final Password password, final Provider provider, final Nickname nickname) {
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.nickname = nickname;
    }

    public static Member self(final String username, final Password password) {
        return new Member(username, password, Provider.SELF, Nickname.random());
    }

    public static Member social(final String username, final Provider provider, final String nickname) {
        return new Member(username, Password.EMPTY, provider, Nickname.of(nickname));
    }

    public static Member guest() {
        return new Member(
                "guest-" + UUID.randomUUID().toString().split("-")[0],
                Password.EMPTY,
                Provider.SELF,
                Nickname.random()
        );
    }

    public void scrap(final Room room) {
        scrapRooms.scrap(room);
    }

    public void unscrap(final Room room) {
        scrapRooms.unscrap(room);
    }

    public boolean hasScrapped(final Room pickedRoom) {
        return scrapRooms.hasScrapped(pickedRoom);
    }

    public List<Room> getScrapRooms() {
        return scrapRooms.getScrapRooms();
    }

    public String getNickname() {
        return nickname.getNickname();
    }
}
