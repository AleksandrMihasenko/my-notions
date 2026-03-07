package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.model.EventFilter;
import com.aleksandrm.mynotions.dto.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void logEvent(String eventType, Long userId, String metadata) {
        jdbcTemplate.update("INSERT INTO events (event_type, user_id, metadata) VALUES (?, ?, ?::jsonb)", eventType, userId, metadata);
    }

    public List<EventResponse> findEvents(Long userId, EventFilter filter, int limit, int offset) {
        return jdbcTemplate.query(
                "SELECT * FROM events WHERE user_id = ? ORDER BY created_at DESC, id DESC LIMIT ? OFFSET ?",
                (rs, rowNum) -> new EventResponse(
                        rs.getLong("id"),
                        rs.getString("event_type"),
                        rs.getLong("user_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("metadata")
                ),
                userId,
                limit,
                offset
            );
    }

    public Long countEvents(Long userId, EventFilter filter) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM events WHERE user_id = ?",
                Long.class,
                userId
        );
    }
}
