package com.digginroom.digginroom.membergenre.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeightFactor {
    SCRAP(2000),
    UNSCRAP(-2000),
    DISLIKE(-300),
    UNDISLIKE(300),
    FAVORITE(10000);

    private final int value;
}
