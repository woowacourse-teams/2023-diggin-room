package com.digginroom.roomrecommender;

public class MemberGenre {

    private Long genreId;
    private int weight = 1000;

    public MemberGenre(final Long genreId) {
        this.genreId = genreId;
    }

    public void increaseWeight() {
        weight += 100;
    }

    public void decreaseWeight() {
        weight += 100;
    }

    public Long getGenreId() {
        return genreId;
    }

    public int getWeight() {
        return weight;
    }
}
