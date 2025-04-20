package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.TagRequestDto;
import com.aleksandrm.mynotions.dto.TagResponseDto;
import com.aleksandrm.mynotions.mapper.TagMapper;
import com.aleksandrm.mynotions.model.Tag;
import com.aleksandrm.mynotions.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagResponseDto addTag(TagRequestDto dto) {
        Tag tag = TagMapper.toEntity(dto);
        tagRepository.addTag(tag);

        return TagMapper.toDto(tag);
    }

    public List<TagResponseDto> getAllTags() {
        return tagRepository.getTags().stream()
                .map(TagMapper::toDto)
                .toList();
    }

    public TagResponseDto updateTag(int id, TagRequestDto dto) {
        Tag updatedTag = TagMapper.toEntity(dto);
        updatedTag.setId(id);
        tagRepository.updateTag(id, updatedTag);

        return TagMapper.toDto(updatedTag);
    }

    public void deleteTag(int id) {
        tagRepository.deleteTag(id);
    }
}
