package com.aleksandrm.mynotions.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
}
