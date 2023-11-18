package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;

public interface MemberGenreEvent {

    Genre getGenre();

    Long getMemberId();

    WeightFactor getWeightFactor();
}
