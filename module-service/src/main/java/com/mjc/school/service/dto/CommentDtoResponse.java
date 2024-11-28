package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record CommentDtoResponse(
        Long id,
        String content,
        NewsDtoResponse newsDto,
        LocalDateTime created,
        LocalDateTime modified
) {
}
