package com.aleksandrm.mynotions.service;

import com.aleksandrm.mynotions.dto.NoteRequestDto;
import com.aleksandrm.mynotions.dto.NoteResponseDto;
import com.aleksandrm.mynotions.mapper.NoteMapper;
import com.aleksandrm.mynotions.model.Note;
import com.aleksandrm.mynotions.model.Tag;
import com.aleksandrm.mynotions.repository.NoteRepository;
import com.aleksandrm.mynotions.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;

    public NoteService(NoteRepository noteRepository, TagRepository tagRepository) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    public NoteResponseDto createNote(NoteRequestDto dto) {
        Note note = NoteMapper.toEntity(dto);

        List<Tag> tags = (dto.getTagIds() != null && !dto.getTagIds().isEmpty())
                ? tagRepository.findByIds(dto.getTagIds())
                : List.of();
        note.setTags(tags);

        noteRepository.createNoteWithTags(note);

        return NoteMapper.toDto(note);
    }


    public List<NoteResponseDto> getAllNotes() {
        return noteRepository.getAllNotes().stream()
                .map(NoteMapper::toDto)
                .collect(Collectors.toList());
    }

    public NoteResponseDto updateNote(int id, NoteRequestDto dto) {
        Note updatedNote = NoteMapper.toEntity(dto);
        updatedNote.setId(id);

        List<Tag> tags = tagRepository.findByIds(dto.getTagIds());
        updatedNote.setTags(tags);

        noteRepository.updateNote(updatedNote);

        return NoteMapper.toDto(updatedNote);
    }

    public void deleteNote(int id) {
        noteRepository.deleteNote(id);
    }

    public List<NoteResponseDto> searchByTitle(String query) {
        return noteRepository.searchByTitle(query).stream()
                .map(NoteMapper::toDto)
                .collect(Collectors.toList());
    }
}
