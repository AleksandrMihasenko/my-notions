package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.dto.PageCreateRequest;
import com.aleksandrm.mynotions.dto.PageResponse;
import com.aleksandrm.mynotions.dto.PageUpdateRequest;
import com.aleksandrm.mynotions.service.PageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PageController {
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/api/workspaces/{workspaceId}/pages")
    public ResponseEntity<PageResponse> create(
            @PathVariable Long workspaceId,
            @Valid @RequestBody PageCreateRequest request
    ) {
        PageResponse response = pageService.create(workspaceId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/workspaces/{workspaceId}/pages")
    public ResponseEntity<List<PageResponse>> getWorkspacePages(@PathVariable Long workspaceId) {
        List<PageResponse> pages = pageService.getWorkspacePages(workspaceId);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/api/pages/{id}")
    public ResponseEntity<PageResponse> getById(@PathVariable Long id) {
        PageResponse page = pageService.getById(id);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/api/pages/{id}")
    public ResponseEntity<PageResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PageUpdateRequest request
    ) {
        PageResponse updated = pageService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/api/pages/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
