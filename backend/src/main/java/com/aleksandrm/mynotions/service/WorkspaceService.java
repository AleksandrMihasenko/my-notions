package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.WorkspaceCreateRequest;
import com.aleksandrm.mynotions.dto.WorkspaceResponse;
import com.aleksandrm.mynotions.dto.WorkspaceUpdateRequest;
import com.aleksandrm.mynotions.events.WorkspaceCreatedEvent;
import com.aleksandrm.mynotions.events.WorkspaceDeletedEvent;
import com.aleksandrm.mynotions.events.WorkspaceUpdatedEvent;
import com.aleksandrm.mynotions.exception.WorkspaceNotFoundException;
import com.aleksandrm.mynotions.model.Workspace;
import com.aleksandrm.mynotions.repository.WorkspaceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final ApplicationEventPublisher publisher;
    private final ObjectMapper objectMapper;
    private final CurrentUserProvider currentUserProvider;

    public WorkspaceService(WorkspaceRepository workspaceRepository, ApplicationEventPublisher publisher, ObjectMapper objectMapper, CurrentUserProvider currentUserProvider) {
        this.workspaceRepository = workspaceRepository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
        this.currentUserProvider = currentUserProvider;
    }

    public List<WorkspaceResponse> getUserWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAllByOwnerId(currentUser());
        return workspaces
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WorkspaceResponse getWorkspaceById(Long workspaceId) {
        Optional<Workspace> workspace = workspaceRepository.findByIdAndOwnerId(workspaceId, currentUser());
        if (workspace.isEmpty()) {
            throw new WorkspaceNotFoundException("Workspace not found");
        } else {
            return toResponse(workspace.get());
        }
    }

    @Transactional
    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        Workspace workspace = new Workspace();
        workspace.setOwnerId(currentUser());
        workspace.setName(request.getName());

        Workspace saved = workspaceRepository.save(workspace);

        String metadata = createWorkspaceMetadata(saved.getId(), saved.getName());
        publisher.publishEvent(new WorkspaceCreatedEvent(saved.getOwnerId(), metadata));

        return toResponse(saved);
    }

    @Transactional
    public WorkspaceResponse update(Long id, WorkspaceUpdateRequest request) {
        Long userId = currentUser();
        Workspace workspace = new Workspace();

        workspace.setOwnerId(userId);
        workspace.setName(request.getName());

        Optional<Workspace> updated = workspaceRepository.update(workspace, id, userId);
        Workspace updatedWorkspace = updated.orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));

        String metadata = createWorkspaceMetadata(updatedWorkspace.getId(), updatedWorkspace.getName());
        publisher.publishEvent(new WorkspaceUpdatedEvent(updatedWorkspace.getOwnerId(), metadata));

        return toResponse(updatedWorkspace);
    }

    @Transactional
    public void delete(Long id) {
        Long userId = currentUser();

        Workspace existing = workspaceRepository.findByIdAndOwnerId(id, userId).orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));
        boolean isDeleted = workspaceRepository.deleteByIdAndOwnerId(id, userId);

        if (!isDeleted) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }

        String metadata = createWorkspaceMetadata(existing.getId(), existing.getName());
        publisher.publishEvent(new WorkspaceDeletedEvent(existing.getOwnerId(), metadata));
    }

    private Long currentUser() {
        return currentUserProvider.getCurrentUserId();
    }

    private WorkspaceResponse toResponse(Workspace workspace) {
        WorkspaceResponse dto = new WorkspaceResponse();
        dto.setId(workspace.getId());
        dto.setName(workspace.getName());
        dto.setCreatedAt(workspace.getCreatedAt());
        dto.setUpdatedAt(workspace.getUpdatedAt());
        return dto;
    }

    private String createWorkspaceMetadata(Long id, String name) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "workspaceId", id,
                    "name", name,
                    "entityType", "WORKSPACE",
                    "entityId", id
            ));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize workspace metadata", e);
        }
    }
}
