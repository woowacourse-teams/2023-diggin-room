package com.digginroom.digginroom.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Weight {
    DEFAULT(1000),
    SCRAP(2000),
    UNSCRAP(-2000),
    DISLIKE(-300),
    UNDISLIKE(300),
    LOWER_BOUND(0);

    private final int weight;
}
