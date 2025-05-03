package com.aleksandrm.mynotions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDto {
    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    private String content;

    @NotBlank(message = "Author must not be blank")
    private String author;

    private List<Integer> tagIds = List.of();
}
