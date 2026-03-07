package com.aleksandrm.mynotions.dto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String eventType,
        Long userId,
        LocalDateTime createdAt,
        String metadata
) {}
