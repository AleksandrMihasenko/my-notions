package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.PageCreateRequest;
import com.aleksandrm.mynotions.dto.PageResponse;
import com.aleksandrm.mynotions.dto.PageUpdateRequest;
import com.aleksandrm.mynotions.model.Page;
import com.aleksandrm.mynotions.repository.PageRepository;
import com.aleksandrm.mynotions.security.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {
    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

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
            throw new RuntimeException("Workspace not found");
        }

        return toResponse(saved.get());
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
            throw new RuntimeException("Page not found");
        }

        return toResponse(page.get());
    }

    public PageResponse update(Long pageId, PageUpdateRequest request) {
        PrincipalUser user = currentUser();

        Page page = new Page();
        page.setTitle(request.getTitle());
        page.setContent(request.getContent());
        page.setParentId(request.getParentId());

        Optional<Page> updated = pageRepository.updateByIdAndOwnerId(page, pageId, user.getId());
        if (updated.isEmpty()) {
            throw new RuntimeException("Page not found");
        }

        return toResponse(updated.get());
    }

    public void delete(Long pageId) {
        PrincipalUser user = currentUser();

        boolean deleted = pageRepository.softDeleteByIdAndOwnerId(pageId, user.getId());
        if (!deleted) {
            throw new RuntimeException("Page not found");
        }
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
}
