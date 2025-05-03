package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTag(Tag tag) {
        String sql = "INSERT INTO tags (name) VALUES (?)";
        jdbcTemplate.update(sql, tag.getName());
    }

    public List<Tag> getTags() {
        String sql = "SELECT * FROM tags";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Tag(rs.getInt("id"), rs.getString("name"))
        );
    }

    public Tag getTagById(int id) {
        String sql = "SELECT * FROM tags WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Tag(rs.getInt("id"), rs.getString("name")), id
        );
    }

    public List<Tag> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        String placeholders = ids.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM tags WHERE id IN (" + placeholders + ")";

        return jdbcTemplate.query(sql, ids.toArray(), (rs, rowNum) ->
                new Tag(rs.getInt("id"), rs.getString("name"))
        );
    }

    public List<Tag> findTagsByNoteId(int noteId) {
        String sql = """
        SELECT t.id, t.name
        FROM tags t
        JOIN note_tags nt ON t.id = nt.tag_id
        WHERE nt.note_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{noteId}, (rs, rowNum) ->
                new Tag(rs.getInt("id"), rs.getString("name"))
        );
    }


    public void updateTag(int id, Tag tag) {
        String sql = "UPDATE tags SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, tag.getName(), id);
    }

    public void deleteTag(int id) {
        String sql = "DELETE FROM tags WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
