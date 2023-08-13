package com.digginroom.digginroom.admin.controller.dto;

import com.digginroom.digginroom.domain.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadRequest(
        @NotBlank String title,
        @NotBlank String artist,
        @NotNull Genre superGenre,
        String description,
        String subGenre,
        @NotBlank String youtubeVideoId
) {
}
