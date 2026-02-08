package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PageRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Page> save(Page page, Long ownerId) {
        String sql = "INSERT INTO pages (workspace_id, title, content, parent_id, created_by) " +
                "SELECT ?, ?, ?, ?, ? " +
                "WHERE EXISTS (SELECT 1 FROM workspaces WHERE id = ? AND owner_id = ?) " +
                "RETURNING id, workspace_id, title, content, parent_id, created_by, created_at, updated_at";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapPage(rs),
                page.getWorkspaceId(),
                page.getTitle(),
                page.getContent(),
                page.getParentId(),
                page.getCreatedBy(),
                page.getWorkspaceId(),
                ownerId
        ).stream().findFirst();
    }

    public List<Page> findAllByWorkspaceIdAndOwnerId(Long workspaceId, Long ownerId) {
        String sql = "SELECT p.id, p.workspace_id, p.title, p.content, p.parent_id, p.created_by, p.created_at, p.updated_at " +
                "FROM pages p " +
                "JOIN workspaces w ON p.workspace_id = w.id " +
                "WHERE p.workspace_id = ? AND w.owner_id = ? AND p.is_deleted = false " +
                "ORDER BY p.updated_at DESC";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapPage(rs),
                workspaceId,
                ownerId
        );
    }

    public Optional<Page> findByIdAndOwnerId(Long pageId, Long ownerId) {
        String sql = "SELECT p.id, p.workspace_id, p.title, p.content, p.parent_id, p.created_by, p.created_at, p.updated_at " +
                "FROM pages p " +
                "JOIN workspaces w ON p.workspace_id = w.id " +
                "WHERE p.id = ? AND w.owner_id = ? AND p.is_deleted = false";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapPage(rs),
                pageId,
                ownerId
        ).stream().findFirst();
    }

    public Optional<Page> updateByIdAndOwnerId(Page page, Long pageId, Long ownerId) {
        String sql = "UPDATE pages p " +
                "SET title = ?, content = ?, parent_id = ?, updated_at = CURRENT_TIMESTAMP " +
                "FROM workspaces w " +
                "WHERE p.workspace_id = w.id AND p.id = ? AND w.owner_id = ? AND p.is_deleted = false " +
                "RETURNING p.id, p.workspace_id, p.title, p.content, p.parent_id, p.created_by, p.created_at, p.updated_at";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapPage(rs),
                page.getTitle(),
                page.getContent(),
                page.getParentId(),
                pageId,
                ownerId
        ).stream().findFirst();
    }

    public boolean softDeleteByIdAndOwnerId(Long pageId, Long ownerId) {
        String sql = "UPDATE pages p " +
                "SET is_deleted = true, updated_at = CURRENT_TIMESTAMP " +
                "WHERE p.id = ? AND p.is_deleted = false " +
                "AND EXISTS (SELECT 1 FROM workspaces w WHERE w.id = p.workspace_id AND w.owner_id = ?)";

        int affectedRows = jdbcTemplate.update(sql, pageId, ownerId);
        return affectedRows > 0;
    }

    private Page mapPage(java.sql.ResultSet rs) throws java.sql.SQLException {
        Page page = new Page();
        page.setId(rs.getLong("id"));
        page.setWorkspaceId(rs.getLong("workspace_id"));
        page.setTitle(rs.getString("title"));
        page.setContent(rs.getString("content"));

        Long parentId = rs.getLong("parent_id");
        if (rs.wasNull()) {
            parentId = null;
        }
        page.setParentId(parentId);

        page.setCreatedBy(rs.getLong("created_by"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            page.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            page.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return page;
    }
}
