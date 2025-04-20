package com.aleksandrm.mynotions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {
    private int id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
