package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.PageCreateRequest;
import com.aleksandrm.mynotions.dto.PageResponse;
import com.aleksandrm.mynotions.dto.PageUpdateRequest;
import com.aleksandrm.mynotions.events.PageCreatedEvent;
import com.aleksandrm.mynotions.events.PageDeletedEvent;
import com.aleksandrm.mynotions.events.PageUpdatedEvent;
import com.aleksandrm.mynotions.exception.PageNotFoundException;
import com.aleksandrm.mynotions.exception.WorkspaceNotFoundException;
import com.aleksandrm.mynotions.model.Page;
import com.aleksandrm.mynotions.repository.PageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {
    private final PageRepository pageRepository;
    private final ApplicationEventPublisher publisher;
    private final ObjectMapper objectMapper;
    private final CurrentUserProvider currentUserProvider;

    public PageService(PageRepository pageRepository, ApplicationEventPublisher publisher, ObjectMapper objectMapper, CurrentUserProvider currentUserProvider) {
        this.pageRepository = pageRepository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Transactional
    public PageResponse create(Long workspaceId, PageCreateRequest request) {
        Long userId = currentUser();

        Page page = new Page();
        page.setWorkspaceId(workspaceId);
        page.setTitle(request.getTitle());
        page.setContent(request.getContent());
        page.setParentId(request.getParentId());
        page.setCreatedBy(userId);

        Optional<Page> saved = pageRepository.save(page, userId);
        if (saved.isEmpty()) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }

        Page savedPage = saved.get();
        String metadata = createPageMetadata(savedPage);
        publisher.publishEvent(new PageCreatedEvent(userId, metadata));

        return toResponse(savedPage);
    }

    public List<PageResponse> getWorkspacePages(Long workspaceId) {
        Long userId = currentUser();

        return pageRepository.findAllByWorkspaceIdAndOwnerId(workspaceId, userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PageResponse getById(Long pageId) {
        Long userId = currentUser();

        Optional<Page> page = pageRepository.findByIdAndOwnerId(pageId, userId);
        if (page.isEmpty()) {
            throw new PageNotFoundException("Page not found");
        }

        return toResponse(page.get());
    }

    @Transactional
    public PageResponse update(Long pageId, PageUpdateRequest request) {
        Long userId = currentUser();

        Page page = new Page();
        page.setTitle(request.getTitle());
        page.setContent(request.getContent());
        page.setParentId(request.getParentId());

        Optional<Page> updated = pageRepository.updateByIdAndOwnerId(page, pageId, userId);
        if (updated.isEmpty()) {
            throw new PageNotFoundException("Page not found");
        }

        Page updatedPage = updated.get();
        String metadata = createPageMetadata(updatedPage);
        publisher.publishEvent(new PageUpdatedEvent(userId, metadata));

        return toResponse(updatedPage);
    }

    @Transactional
    public void delete(Long pageId) {
        Long userId = currentUser();
        Page existingPage = pageRepository.findByIdAndOwnerId(pageId, userId)
                .orElseThrow(() -> new PageNotFoundException("Page not found"));

        boolean deleted = pageRepository.softDeleteByIdAndOwnerId(pageId, userId);
        if (!deleted) {
            throw new PageNotFoundException("Page not found");
        }

        String metadata = createPageMetadata(existingPage);
        publisher.publishEvent(new PageDeletedEvent(userId, metadata));
    }

    private Long currentUser() {
        return currentUserProvider.getCurrentUserId();
    }

    private PageResponse toResponse(Page page) {
        PageResponse response = new PageResponse();
        response.setId(page.getId());
        response.setWorkspaceId(page.getWorkspaceId());
        response.setTitle(page.getTitle());
        response.setContent(page.getContent());
        response.setParentId(page.getParentId());
        response.setCreatedBy(page.getCreatedBy());
        response.setCreatedAt(page.getCreatedAt());
        response.setUpdatedAt(page.getUpdatedAt());
        return response;
    }

    private String createPageMetadata(Page page) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "pageId", page.getId(),
                    "workspaceId", page.getWorkspaceId(),
                    "title", page.getTitle(),
                    "entityType", "PAGE",
                    "entityId", page.getId()
            ));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize page metadata", e);
        }
    }
}
