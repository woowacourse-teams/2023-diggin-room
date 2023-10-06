package com.digginroom.digginroom;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.domain.track.Track;
import com.digginroom.digginroom.oauth.jwk.GoogleJwkProvider;
import com.digginroom.digginroom.oauth.jwk.IdTokenResolver;
import com.digginroom.digginroom.oauth.jwk.KakaoJwkProvider;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
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
        return Member.self(MEMBER_USERNAME, MEMBER_PASSWORD);
    }

    public static Member 블랙캣() {
        return Member.self(MEMBER2_USERNAME, MEMBER2_PASSWORD);
    }

    public static final CommentRequest COMMENT_REQUEST = new CommentRequest("베리는 REST API 고수");
    public static final CommentRequest COMMENT_UPDATE_REQUEST = new CommentRequest("파워는 인텔리제이 평생 무료일듯?");

    public static final String ID_TOKEN_SAMPLE_GOOGLE = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxMWUzOWUyNzkyOGFlOWYxZTlkMWUyMTY0NmRlOTJkMTkzNTFiNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjIwMzcyNTY1NjAtNm45b20wZm1ibTlkZ2FmN3RtdGFpamZhbmVkdjFybTEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjIwMzcyNTY1NjAtMWo1dTN2YnQ0NHRtMHJtaGI5cWo0bWg4bGFsbzNlMXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQxMDYwMDM2NjEyMjg5MjAzNzEiLCJlbWFpbCI6ImtpbWppbnVrMTk5OUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Iuq5gOynhOyasSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRkeUxlcXFheE9wS3l1QUZmNnpqbUJNX0xuRGJkNkJTMTNmSmtGczdQWHg9czk2LWMiLCJnaXZlbl9uYW1lIjoi7KeE7JqxIiwiZmFtaWx5X25hbWUiOiLquYAiLCJsb2NhbGUiOiJrbyIsImlhdCI6MTY5MTQ3NTcxMCwiZXhwIjoxNjkxNDc5MzEwfQ.O41Jfdb_Y0swfZ2r1oDG9NDO73dYYmPStzh8l90BEbwPoPQPeySBmRO9UnCnFaL_B6p18vKXfpgxOn6RDBwknHB6dRrRqElKFhEaCTp63TMMc2g-EijdsKDbhnUQWsTyuWqVflnNsCk0HQPU9_MevCuPIH-_gDdIHV6SQwVuZDWMjlzAdZXZCyyH09q22QMCsB1-zaPZHKHLShtGspRN5HUVvINhshB6Lip1OOe3cUFD-g41o3pFAOpDFbMMif1WVK5Lv6031SQL4GbhGN3Uum62-wgiU268uLwHYRFLNqkjR1guMNK4ieBmt46SkG1VOpfzcPE6I4p7x1lhUdN9Pg";
    public static final String ID_TOKEN_SAMPLE_KAKAO = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJrYWthb3VzZXJpZCIsIm5pY2tuYW1lIjoia2FrYW9uaWNrbmFtZSIsImlhdCI6MTUxNjIzOTAyMiwiaXNzIjoiaHR0cHM6Ly9rYXV0aC5rYWthby5jb20ifQ.VyFKtBiiqZfAQNQ-fBzMUj0MoD-7ohiCKd9EEN6Fwn4";

    public static final IdTokenPayload ID_TOKEN_PAYLOAD = new IdTokenPayload() {
        @Override
        public String getNickname() {
            return 파워().getNickname();
        }

        @Override
        public String getUsername() {
            return 파워().getUsername();
        }

        @Override
        public Provider getProvider() {
            return Provider.KAKAO;
        }
    };

    public static final IdTokenResolver ID_TOKEN_RESOLVER = new IdTokenResolver(List.of(
            new KakaoJwkProvider(),
            new GoogleJwkProvider()
    ));

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
