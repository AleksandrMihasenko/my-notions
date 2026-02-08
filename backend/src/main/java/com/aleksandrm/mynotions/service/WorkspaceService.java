package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.WorkspaceCreateRequest;
import com.aleksandrm.mynotions.dto.WorkspaceResponse;
import com.aleksandrm.mynotions.dto.WorkspaceUpdateRequest;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        List<Workspace> workspaces = workspaceRepository.findAllByOwnerId(user.getId());
        return workspaces
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WorkspaceResponse getWorkspaceById(Long workspaceId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        Optional<Workspace> workspace = workspaceRepository.findByIdAndOwnerId(workspaceId, user.getId());
        if (workspace.isEmpty()) {
            throw new RuntimeException("Workspace not found");
        } else {
            return toResponse(workspace.get());
        }
    }

    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        Workspace workspace = new Workspace();
        workspace.setOwnerId(user.getId());
        workspace.setName(request.getName());

        Workspace saved = workspaceRepository.save(workspace);
        return toResponse(saved);
    }

    public WorkspaceResponse update(Long id, WorkspaceUpdateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        Workspace workspace = new Workspace();
        workspace.setOwnerId(user.getId());
        workspace.setName(request.getName());

        Optional<Workspace> updated = workspaceRepository.update(workspace, id, user.getId());

        if (updated.isEmpty()) {
            throw new RuntimeException("Workspace not found");
        }

        return toResponse(updated.get());
    }

    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        boolean isDeleted = workspaceRepository.deleteByIdAndOwnerId(id, user.getId());

        if (!isDeleted) {
            throw new RuntimeException("Workspace not found");
        }
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
