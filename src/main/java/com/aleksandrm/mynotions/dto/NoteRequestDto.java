package com.aleksandrm.mynotions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDto {
    private String title;
    private String content;
    private String author;
}
