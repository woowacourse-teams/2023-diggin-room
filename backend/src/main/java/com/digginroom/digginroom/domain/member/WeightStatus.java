package com.digginroom.digginroom.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeightStatus {
    DEFAULT(1000),
    LOWER_BOUND(100);

    private final int value;
}
