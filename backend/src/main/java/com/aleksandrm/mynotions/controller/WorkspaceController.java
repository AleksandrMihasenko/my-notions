package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.dto.WorkspaceCreateRequest;
import com.aleksandrm.mynotions.dto.WorkspaceResponse;
import com.aleksandrm.mynotions.dto.WorkspaceUpdateRequest;
import com.aleksandrm.mynotions.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> getUserWorkspaces() {
        List<WorkspaceResponse> workspaces = workspaceService.getUserWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> getWorkspaceById(@PathVariable Long id) {
        WorkspaceResponse workspace = workspaceService.getWorkspaceById(id);
        return ResponseEntity.ok(workspace);
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createUserWorkspace(@Valid @RequestBody WorkspaceCreateRequest request) {
        WorkspaceResponse created = workspaceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> updateUserWorkspace(@PathVariable Long id, @Valid @RequestBody WorkspaceUpdateRequest request) {
        WorkspaceResponse updated = workspaceService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserWorkspace(@PathVariable Long id) {
        workspaceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
