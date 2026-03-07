package com.aleksandrm.mynotions.dto;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        Integer page,
        Integer pageSize,
        Integer totalItems,
        Integer totalPages,
        boolean last
) {}
