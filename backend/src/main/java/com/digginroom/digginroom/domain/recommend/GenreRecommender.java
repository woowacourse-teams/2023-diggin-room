package com.digginroom.digginroom.domain.recommend;

import com.digginroom.digginroom.domain.Genre;

public interface GenreRecommender {

    Genre recommend(final Long memberId);
}
