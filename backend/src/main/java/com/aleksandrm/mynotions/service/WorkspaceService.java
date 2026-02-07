package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.WorkspaceCreateRequest;
import com.aleksandrm.mynotions.dto.WorkspaceResponse;
import com.aleksandrm.mynotions.model.Workspace;
import com.aleksandrm.mynotions.repository.WorkspaceRepository;
import com.aleksandrm.mynotions.security.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser user = (PrincipalUser) auth.getPrincipal();

        Workspace workspace = new Workspace();
        workspace.setOwnerId(user.getId());
        workspace.setName(request.getName());

        Workspace saved = workspaceRepository.save(workspace);
        return toResponse(saved);
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
