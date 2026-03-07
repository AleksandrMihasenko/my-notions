package com.aleksandrm.mynotions.model;

import java.time.LocalDateTime;

public record EventFilter(
        String type,
        String entityType,
        Long entityId,
        LocalDateTime from,
        LocalDateTime to
) {}
