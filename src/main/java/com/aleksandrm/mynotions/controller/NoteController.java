package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.dto.NoteRequestDto;
import com.aleksandrm.mynotions.dto.NoteResponseDto;
import com.aleksandrm.mynotions.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAll() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@RequestBody NoteRequestDto dto) {
        return ResponseEntity.ok(noteService.createNote(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> update(@PathVariable int id, @RequestBody NoteRequestDto dto) {
        return ResponseEntity.ok(noteService.updateNote(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<NoteResponseDto>> searchByTitle(@RequestParam String query) {
        return ResponseEntity.ok(noteService.searchByTitle(query));
    }
}