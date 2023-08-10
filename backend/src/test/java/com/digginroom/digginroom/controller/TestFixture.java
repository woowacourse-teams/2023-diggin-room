package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.domain.Track;
import java.util.List;

//TODO: 픽스쳐 분리
@SuppressWarnings("NonAsciiCharacters")
public class TestFixture {

    public static final String MEMBER_PASSWORD = "power1234@";
    public static final String MEMBER_USERNAME = "power";
    public static final MemberLoginRequest MEMBER_LOGIN_REQUEST = new MemberLoginRequest(MEMBER_USERNAME,
            MEMBER_PASSWORD);
    public static final MemberSaveRequest MEMBER_SAVE_REQUEST = new MemberSaveRequest(MEMBER_USERNAME, MEMBER_PASSWORD);
    public static final String MEMBER2_PASSWORD = "blackcat1234@";
    public static final String MEMBER2_USERNAME = "blackcat";
    public static final MemberLoginRequest MEMBER2_LOGIN_REQUEST = new MemberLoginRequest(MEMBER2_USERNAME,
            MEMBER2_PASSWORD);

    public static Member 파워() {
        return new Member(MEMBER_USERNAME, MEMBER_PASSWORD);
    }

    public static Member 블랙캣() {
        return new Member(MEMBER2_USERNAME, MEMBER2_PASSWORD);
    }

    public static final CommentRequest COMMENT_REQUEST = new CommentRequest("베리는 REST API 고수");

    public static Room 나무() {
        Track track = Track.builder()
                .artist("코건")
                .title("나무")
                .superGenre(Genre.ROCK)
                .subGenres(List.of("Alternative Rock", "Noise Rock"))
                .build();
        return new Room(new MediaSource("lQcnNPqy2Ww"), track);
    }

    public static Room 차이() {
        Track track = Track.builder()
                .artist("콩하나")
                .title("차이코프스키 6번 교향곡")
                .superGenre(Genre.CLASSICAL_MUSIC)
                .subGenres(List.of("Alternative Rock", "Noise Rock"))
                .build();
        return new Room(new MediaSource("2VkWaOOF4Rc"), track);
    }
}
