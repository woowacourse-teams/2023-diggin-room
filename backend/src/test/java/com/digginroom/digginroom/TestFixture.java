package com.digginroom.digginroom;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.domain.track.Track;
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
    public static final CommentRequest COMMENT_UPDATE_REQUEST = new CommentRequest("파워는 인텔리제이 평생 무료일듯?");

    public static Room 나무() {
        Track track = Track.builder()
                .artist("코건")
                .title("나무")
                .superGenre(Genre.ROCK)
                .subGenres(List.of("Alternative Rock", "Noise Rock"))
                .description("코건은 코건")
                .build();
        return new Room("lQcnNPqy2Ww", track);
    }

    public static Room 차이() {
        Track track = Track.builder()
                .artist("콩하나")
                .title("차이코프스키 6번 교향곡")
                .superGenre(Genre.CLASSICAL_MUSIC)
                .subGenres(List.of("Alternative Rock", "Noise Rock"))
                .description("콩하나는 콩둘")
                .build();
        return new Room("2VkWaOOF4Rc", track);
    }
}
