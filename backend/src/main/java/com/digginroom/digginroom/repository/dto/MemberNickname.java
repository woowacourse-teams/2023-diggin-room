package com.digginroom.digginroom.repository.dto;

import org.springframework.beans.factory.annotation.Value;

public interface MemberNickname {

    @Value("#{target.nickname}")
    String getNickname();
}
