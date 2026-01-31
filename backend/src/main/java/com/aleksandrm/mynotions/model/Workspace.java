package com.aleksandrm.mynotions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    private Long id;
    private String name;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
