package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.WorkspaceCreateRequest;
import com.aleksandrm.mynotions.dto.WorkspaceResponse;
import com.aleksandrm.mynotions.dto.WorkspaceUpdateRequest;
import com.aleksandrm.mynotions.exception.WorkspaceNotFoundException;
import com.aleksandrm.mynotions.model.Workspace;
import com.aleksandrm.mynotions.repository.WorkspaceRepository;
import com.aleksandrm.mynotions.security.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<WorkspaceResponse> getUserWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAllByOwnerId(currentUser().getId());
        return workspaces
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WorkspaceResponse getWorkspaceById(Long workspaceId) {
        Optional<Workspace> workspace = workspaceRepository.findByIdAndOwnerId(workspaceId, currentUser().getId());
        if (workspace.isEmpty()) {
            throw new WorkspaceNotFoundException("Workspace not found");
        } else {
            return toResponse(workspace.get());
        }
    }

    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        Workspace workspace = new Workspace();
        workspace.setOwnerId(currentUser().getId());
        workspace.setName(request.getName());

        Workspace saved = workspaceRepository.save(workspace);
        return toResponse(saved);
    }

    public WorkspaceResponse update(Long id, WorkspaceUpdateRequest request) {
        PrincipalUser user = currentUser();

        Workspace workspace = new Workspace();
        workspace.setOwnerId(user.getId());
        workspace.setName(request.getName());

        Optional<Workspace> updated = workspaceRepository.update(workspace, id, user.getId());

        if (updated.isEmpty()) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }

        return toResponse(updated.get());
    }

    public void delete(Long id) {
        boolean isDeleted = workspaceRepository.deleteByIdAndOwnerId(id, currentUser().getId());

        if (!isDeleted) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }
    }

    private PrincipalUser currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (PrincipalUser) auth.getPrincipal();
    }

    private WorkspaceResponse toResponse(Workspace workspace) {
        WorkspaceResponse dto = new WorkspaceResponse();
        dto.setId(workspace.getId());
        dto.setName(workspace.getName());
        dto.setCreatedAt(workspace.getCreatedAt());
        dto.setUpdatedAt(workspace.getUpdatedAt());
        return dto;
    }
}
