package com.digginroom.digginroom.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeightStatus {
    DEFAULT(1000),
    LOWER_BOUND(0);

    private final int value;
}
