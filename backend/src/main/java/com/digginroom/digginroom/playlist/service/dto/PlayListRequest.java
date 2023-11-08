package com.digginroom.digginroom.playlist.service.dto;

import java.util.List;

public record PlayListRequest(
        String title,
        List<String> videoIds,
        String code
) {
}
