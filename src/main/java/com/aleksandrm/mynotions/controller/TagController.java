package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.dto.TagRequestDto;
import com.aleksandrm.mynotions.dto.TagResponseDto;
import com.aleksandrm.mynotions.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> addTag(@RequestBody TagRequestDto dto) {
        return ResponseEntity.ok(tagService.addTag(dto));
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAll() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> update(@PathVariable int id, @RequestBody TagRequestDto dto) {
        return ResponseEntity.ok(tagService.updateTag(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
