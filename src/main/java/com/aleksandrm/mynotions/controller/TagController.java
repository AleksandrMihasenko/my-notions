package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.model.Tag;
import com.aleksandrm.mynotions.repository.TagRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagRepository.getTags();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable int id) {
        return tagRepository.getTagById(id);
    }

    @PostMapping
    public void addTag(@RequestBody Tag tag) {
        tagRepository.addTag(tag.getName());
    }

    @PutMapping("/{id}")
    public void updateTag(@PathVariable int id, @RequestBody Tag tag) {
        tagRepository.updateTag(id, tag.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id) {
        tagRepository.deleteTag(id);
    }
}
