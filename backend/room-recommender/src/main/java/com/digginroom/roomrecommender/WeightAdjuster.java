package com.digginroom.roomrecommender;

public class WeightAdjuster {

    public static MemberGenre increaseWeight(MemberGenre targetGenre) {
        targetGenre.increaseWeight();
        return targetGenre;
    }

    public static MemberGenre decreaseWeight(MemberGenre targetGenre) {
        targetGenre.decreaseWeight();
        return targetGenre;
    }
}
