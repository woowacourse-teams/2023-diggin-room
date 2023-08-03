package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
public class TestFixture {

    private static final String MEMBER_PASSWORD = "power1234@";
    private static final String MEMBER_USERNAME = "power";
    public static final MemberLoginRequest MEMBER_LOGIN_REQUEST = new MemberLoginRequest(MEMBER_USERNAME,
            MEMBER_PASSWORD);
    public static final MemberSaveRequest MEMBER_SAVE_REQUEST = new MemberSaveRequest(MEMBER_USERNAME, MEMBER_PASSWORD);

    public static Member 파워() {
        return new Member(MEMBER_USERNAME, MEMBER_PASSWORD);
    }
}
