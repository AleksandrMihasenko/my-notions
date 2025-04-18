package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NoteRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createNote(Note note) {
        String sql = "INSERT INTO notes (title, content, author) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, note.getTitle(), note.getContent(), note.getAuthor());
    }

    public List<Note> getAllNotes() {
        String sql = "SELECT * FROM notes";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Note(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("author"),
                rs.getTimestamp("created_at")
        ));
    }

    public void updateNote(int id, Note note) {
        String sql = "UPDATE notes SET title = ?, content = ?, author = ? WHERE id = ?";

        jdbcTemplate.update(sql, note.getTitle(), note.getContent(), note.getAuthor(), id);
    }

    public void deleteNote(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    public List<Note> searchByTitle(String query) {
        String sql = "SELECT * FROM notes WHERE notes.title LIKE ?";
        String likePattern = "%" + query + "%";

        return jdbcTemplate.query(
                sql,
                new Object[]{likePattern},
                (rs, rowNum) -> new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getTimestamp("created_at")
                )
        );
    }
}
