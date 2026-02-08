package com.aleksandrm.mynotions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUpdateRequest {
    @NotBlank(message = "Page title must not be blank")
    @Size(max = 255, message = "Page title must be at most 255 characters")
    private String title;

    @Size(max = 50000, message = "Page content must be at most 50000 characters")
    private String content;

    private Long parentId;
}
