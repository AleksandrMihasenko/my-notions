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
import com.aleksandrm.mynotions.security.PrincipalUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public PageService(PageRepository pageRepository, ApplicationEventPublisher publisher, ObjectMapper objectMapper) {
        this.pageRepository = pageRepository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public PageResponse create(Long workspaceId, PageCreateRequest request) {
        PrincipalUser user = currentUser();

        Page page = new Page();
        page.setWorkspaceId(workspaceId);
        page.setTitle(request.getTitle());
        page.setContent(request.getContent());
        page.setParentId(request.getParentId());
        page.setCreatedBy(user.getId());

        Optional<Page> saved = pageRepository.save(page, user.getId());
        if (saved.isEmpty()) {
            throw new WorkspaceNotFoundException("Workspace not found");
        }

        Page savedPage = saved.get();
        String metadata = createPageMetadata(savedPage);
        publisher.publishEvent(new PageCreatedEvent(user.getId(), metadata));

        return toResponse(savedPage);
    }

    public List<PageResponse> getWorkspacePages(Long workspaceId) {
        PrincipalUser user = currentUser();

        return pageRepository.findAllByWorkspaceIdAndOwnerId(workspaceId, user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PageResponse getById(Long pageId) {
        PrincipalUser user = currentUser();

        Optional<Page> page = pageRepository.findByIdAndOwnerId(pageId, user.getId());
        if (page.isEmpty()) {
            throw new PageNotFoundException("Page not found");
        }

        return toResponse(page.get());
    }

    @Transactional
    public PageResponse update(Long pageId, PageUpdateRequest request) {
        PrincipalUser user = currentUser();

        Page page = new Page();
        page.setTitle(request.getTitle());
        page.setContent(request.getContent());
        page.setParentId(request.getParentId());

        Optional<Page> updated = pageRepository.updateByIdAndOwnerId(page, pageId, user.getId());
        if (updated.isEmpty()) {
            throw new PageNotFoundException("Page not found");
        }

        Page updatedPage = updated.get();
        String metadata = createPageMetadata(updatedPage);
        publisher.publishEvent(new PageUpdatedEvent(user.getId(), metadata));

        return toResponse(updatedPage);
    }

    @Transactional
    public void delete(Long pageId) {
        PrincipalUser user = currentUser();
        Page existingPage = pageRepository.findByIdAndOwnerId(pageId, user.getId())
                .orElseThrow(() -> new PageNotFoundException("Page not found"));

        boolean deleted = pageRepository.softDeleteByIdAndOwnerId(pageId, user.getId());
        if (!deleted) {
            throw new PageNotFoundException("Page not found");
        }

        String metadata = createPageMetadata(existingPage);
        publisher.publishEvent(new PageDeletedEvent(user.getId(), metadata));
    }

    private PrincipalUser currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (PrincipalUser) auth.getPrincipal();
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
