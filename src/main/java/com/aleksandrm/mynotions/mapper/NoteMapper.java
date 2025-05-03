package com.aleksandrm.mynotions.mapper;

import com.aleksandrm.mynotions.dto.NoteRequestDto;
import com.aleksandrm.mynotions.dto.NoteResponseDto;
import com.aleksandrm.mynotions.model.Note;

import com.aleksandrm.mynotions.model.Tag;

import java.util.List;


public class NoteMapper {

    public static Note toEntity(NoteRequestDto dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setAuthor(dto.getAuthor());

        return note;
    }

    public static NoteResponseDto toDto(Note note) {
        return new NoteResponseDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getAuthor(),
                note.getCreatedAt() != null ? note.getCreatedAt().toLocalDateTime() : null,
                note.getTags() != null
                        ? note.getTags().stream().map(Tag::getName).toList()
                        : List.of()
        );
    }
}
