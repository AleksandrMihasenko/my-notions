package com.aleksandrm.mynotions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUpdateRequest {
    @NotBlank(message = "Workspace name must not be blank")
    @Size(min = 3, max = 30, message = "Workspace name must be between 3 and 30 characters")
    private String name;
}
