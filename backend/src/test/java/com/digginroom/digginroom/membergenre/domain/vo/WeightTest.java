package com.digginroom.digginroom.membergenre.domain.vo;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeightTest {

    @Test
    void 가중치_기본값은_1000이다() {
        assertThat(new Weight()).isEqualTo(new Weight(1000));
    }

    @Test
    void 가중치는_하한값_이상임이_보장된다() {
        assertThat(new Weight(99)).isEqualTo(new Weight(100));
    }

    @Test
    void 서로_더할_수_있다() {
        assertThat(new Weight(100).add(new Weight(100))).isEqualTo(new Weight(200));
    }

    @Test
    void 가중치는_가중치_요인에_의해서_조절된다() {
        assertThat(new Weight(100).adjust(WeightFactor.SCRAP)).isEqualTo(new Weight(2100));
    }

    @Test
    void 뺐을때_최솟값보다_작으면_하한값이_된다() {
        assertThat(new Weight(200).adjust(WeightFactor.UNSCRAP)).isEqualTo(new Weight(100));
    }
}
