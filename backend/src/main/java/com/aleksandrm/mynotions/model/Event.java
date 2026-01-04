package com.aleksandrm.mynotions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String eventType;
    private Long userId;
    private String metadata;
    private LocalDateTime createdAt;
}
