package com.aleksandrm.mynotions.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {
    @NotBlank(message = "Tag name must not be blank")
    private String name;
}
