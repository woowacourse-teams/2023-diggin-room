package com.digginroom.digginroom.admin.controller.dto;

import com.digginroom.digginroom.domain.room.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UploadRequest(
        @NotBlank @Size(min = 1, max = 255) String title,
        @NotBlank @Size(min = 1, max = 255) String artist,
        @NotNull Genre superGenre,
        @Size(max = 500) String description,
        @Size(max = 255) String subGenre,
        @NotBlank @Size(min = 1, max = 255) String youtubeVideoId
) {
}
