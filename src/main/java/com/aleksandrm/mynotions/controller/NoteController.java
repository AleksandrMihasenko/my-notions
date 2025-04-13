package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aleksandrm.mynotions.repository.NoteRepository;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping
    public List<Note> getTodos() {
        return noteRepository.getAllNotes();
    }

    @PostMapping
    public void addTodo(@RequestBody Note note) {
        noteRepository.createNote(note);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable int id, @RequestBody Note note) {
        noteRepository.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable int id) {
        noteRepository.deleteNote(id);
    }

    @GetMapping("/search")
    public List<Note> searchByTitle(@RequestParam String query) {
        return noteRepository.searchByTitle(query);
    }
}
