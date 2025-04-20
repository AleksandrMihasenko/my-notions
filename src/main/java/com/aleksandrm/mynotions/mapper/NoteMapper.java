package com.aleksandrm.mynotions.mapper;

import com.aleksandrm.mynotions.dto.NoteRequestDto;
import com.aleksandrm.mynotions.dto.NoteResponseDto;
import com.aleksandrm.mynotions.model.Note;

public class NoteMapper {

    public static Note toEntity(NoteRequestDto dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setAuthor(dto.getAuthor());

        return note;
    }

    public static NoteResponseDto toDto(Note note) {
        NoteResponseDto dto = new NoteResponseDto();

        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setAuthor(note.getAuthor());

        if (note.getCreatedAt() != null) {
            dto.setCreatedAt(note.getCreatedAt().toLocalDateTime());
        }

        return dto;
    }
}
