package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;

@Repository
public class WorkspaceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WorkspaceRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
}
