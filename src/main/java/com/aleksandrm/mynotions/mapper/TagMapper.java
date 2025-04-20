package com.aleksandrm.mynotions.mapper;

import com.aleksandrm.mynotions.dto.TagRequestDto;
import com.aleksandrm.mynotions.dto.TagResponseDto;
import com.aleksandrm.mynotions.model.Tag;

public class TagMapper {

    public static Tag toEntity(TagRequestDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());

        return tag;
    }

    public static TagResponseDto toDto(Tag tag) {
        TagResponseDto dto = new TagResponseDto();

        dto.setId(tag.getId());
        dto.setName(tag.getName());

        return new TagResponseDto(tag.getId(), tag.getName());
    }
}
