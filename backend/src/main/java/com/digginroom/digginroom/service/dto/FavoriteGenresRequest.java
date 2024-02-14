package com.digginroom.digginroom.service.dto;

import com.digginroom.digginroom.domain.room.Genre;
import java.util.List;

public record FavoriteGenresRequest(List<Genre> favoriteGenres) {
}
