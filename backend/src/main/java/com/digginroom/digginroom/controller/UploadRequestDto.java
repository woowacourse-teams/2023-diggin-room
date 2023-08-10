package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.domain.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String artist,
        @NotNull
        Genre superGenre,
        String subGenre,
        @NotBlank
        String youtubeVideoId
) {
}
