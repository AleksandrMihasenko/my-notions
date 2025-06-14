package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Note;
import com.aleksandrm.mynotions.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class NoteRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagRepository tagRepository;

    @Autowired
    public NoteRepository(JdbcTemplate jdbcTemplate, TagRepository tagRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRepository = tagRepository;
    }

    public void createNote(Note note) {
        String sql = "INSERT INTO notes (title, content, author) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, note.getTitle(), note.getContent(), note.getAuthor());
    }

    public List<Note> getAllNotes() {
        String sql = "SELECT * FROM notes";

        List<Note> notes = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getTimestamp("created_at")
                )
        );

        for (Note note : notes) {
            List<Tag> tags = tagRepository.findTagsByNoteId(note.getId());
            note.setTags(tags);
        }

        return notes;
    }

    public void updateNote(Note note) {
        String updateSql = "UPDATE notes SET title = ?, content = ?, author = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, note.getTitle(), note.getContent(), note.getAuthor(), note.getId());

        String deleteTagsSql = "DELETE FROM note_tags WHERE note_id = ?";
        jdbcTemplate.update(deleteTagsSql, note.getId());

        if (note.getTags() != null) {
            for (Tag tag : note.getTags()) {
                String insertTagSql = "INSERT INTO note_tags (note_id, tag_id) VALUES (?, ?)";
                jdbcTemplate.update(insertTagSql, note.getId(), tag.getId());
            }
        }
    }

    public void deleteNote(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Note> searchByTitle(String query) {
        String sql = "SELECT * FROM notes WHERE title LIKE ?";
        String likePattern = "%" + query + "%";

        List<Note> notes = jdbcTemplate.query(sql, new Object[]{likePattern}, (rs, rowNum) ->
                new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getTimestamp("created_at")
                )
        );

        for (Note note : notes) {
            List<Tag> tags = tagRepository.findTagsByNoteId(note.getId());
            note.setTags(tags);
        }

        return notes;
    }


    public void createNoteWithTags(Note note) {
        String insertNoteSql = "INSERT INTO notes (title, content, author, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(insertNoteSql, note.getTitle(), note.getContent(), note.getAuthor());

        Integer noteId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        note.setId(noteId);

        if (note.getTags() != null) {
            for (Tag tag : note.getTags()) {
                String insertRelationSql = "INSERT INTO note_tags (note_id, tag_id) VALUES (?, ?)";
                jdbcTemplate.update(insertRelationSql, noteId, tag.getId());
            }
        }
    }
}
