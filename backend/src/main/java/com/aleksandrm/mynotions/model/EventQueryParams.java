package com.aleksandrm.mynotions.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record EventQueryParams(
    String type,
    String entityType,
    Long entityId,
    LocalDateTime from,
    LocalDateTime to,
    @Min(0) Integer page,
    @Min(1) @Max(100) Integer pageSize
) {
    public int pageOrDefault() {
        return page == null ? 0 : page;
    }

    public int pageSizeOrDefault() {
        return pageSize == null ? 20 : pageSize;
    }
}
