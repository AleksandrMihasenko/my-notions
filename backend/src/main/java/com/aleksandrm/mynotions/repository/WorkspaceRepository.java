package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkspaceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WorkspaceRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Workspace> findAllByOwnerId(Long ownerId) {
        String sql = "SELECT * FROM workspaces WHERE owner_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Workspace w = new Workspace();
            w.setId(rs.getLong("id"));
            w.setName(rs.getString("name"));
            w.setOwnerId(rs.getLong("owner_id"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                w.setCreatedAt(createdAt.toLocalDateTime());
            }

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                w.setUpdatedAt(updatedAt.toLocalDateTime());
            }

            return w;
        }, ownerId);
    }

    public Optional<Workspace> findByIdAndOwnerId(Long workspaceId, Long ownerId) {
        String sql = "SELECT * FROM workspaces WHERE id = ? AND owner_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Workspace w = new Workspace();
            w.setId(rs.getLong("id"));
            w.setName(rs.getString("name"));
            w.setOwnerId(rs.getLong("owner_id"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                w.setCreatedAt(createdAt.toLocalDateTime());
            }

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                w.setUpdatedAt(updatedAt.toLocalDateTime());
            }

            return w;
        }, workspaceId, ownerId)
                .stream()
                .findFirst();
    }

    public Workspace save(Workspace workspace) {
        String sql = "INSERT INTO workspaces (name, owner_id) " +
                "VALUES (?, ?) " +
                "RETURNING id, name, owner_id, created_at, updated_at";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Workspace w = new Workspace();
                    w.setId(rs.getLong("id"));
                    w.setName(rs.getString("name"));
                    w.setOwnerId(rs.getLong("owner_id"));

                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        w.setCreatedAt(createdAt.toLocalDateTime());
                    }

                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    if (updatedAt != null) {
                        w.setUpdatedAt(updatedAt.toLocalDateTime());
                    }

                    return w;
                },
                workspace.getName(),
                workspace.getOwnerId()
        );
    }

    public Optional<Workspace> update(Workspace workspace, Long workspaceId, Long ownerId) {
        String sql = "UPDATE workspaces " +
                "SET name = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? " +
                "RETURNING id, name, owner_id, created_at, updated_at";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Workspace w = new Workspace();
                    w.setId(rs.getLong("id"));
                    w.setName(rs.getString("name"));
                    w.setOwnerId(rs.getLong("owner_id"));

                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        w.setCreatedAt(createdAt.toLocalDateTime());
                    }

                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    if (updatedAt != null) {
                        w.setUpdatedAt(updatedAt.toLocalDateTime());
                    }

                    return w;
                },
                workspace.getName(),
                workspaceId,
                ownerId
        )
                .stream()
                .findFirst();
    }

    public boolean deleteByIdAndOwnerId(Long workspaceId, Long ownerId) {
        String sql = "DELETE FROM workspaces " +
                "WHERE id = ? AND owner_id = ?";

        int affectedRows = jdbcTemplate.update(sql, workspaceId, ownerId);
        return affectedRows > 0;
    }
}
