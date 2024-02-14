package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import com.digginroom.digginroom.membergenre.domain.MemberGenreEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultMemberGenreEvent implements MemberGenreEvent {

    private final Genre genre;
    private final Long memberId;
    private final WeightFactor weightFactor;
}
