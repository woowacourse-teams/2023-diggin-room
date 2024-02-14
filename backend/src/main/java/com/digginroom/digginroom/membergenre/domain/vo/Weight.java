package com.digginroom.digginroom.membergenre.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class Weight {

    @Column(name = "weight")
    private final Integer value;

    public Weight() {
        this.value = guaranteeLowerBound(WeightStatus.DEFAULT.getValue());
    }

    public Weight(final Integer value) {
        this.value = guaranteeLowerBound(value);
    }

    private Integer guaranteeLowerBound(final Integer value) {
        return Integer.max(value, WeightStatus.LOWER_BOUND.getValue());
    }

    public Weight add(final Weight other) {
        return new Weight(value + other.value);
    }

    public Weight adjust(final WeightFactor factor) {
        return new Weight(this.value + factor.getValue());
    }

    public boolean isLessThan(final int value) {
        return this.value < value;
    }

    public Integer getValue() {
        return value;
    }
}
