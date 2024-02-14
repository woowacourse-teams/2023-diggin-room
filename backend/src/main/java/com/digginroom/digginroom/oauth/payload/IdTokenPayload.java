package com.digginroom.digginroom.oauth.payload;

import com.digginroom.digginroom.domain.member.vo.Provider;

public interface IdTokenPayload {

    String getNickname();

    String getUsername();

    Provider getProvider();
}
