package com.aleksandrm.mynotions.repository;

import com.aleksandrm.mynotions.dto.EventResponse;
import com.aleksandrm.mynotions.model.EventFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EventRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void logEvent(String eventType, Long userId, String metadata) {
        String sql = "INSERT INTO events (event_type, user_id, metadata) VALUES (:eventType, :userId, CAST(:metadata AS jsonb))";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("eventType", eventType)
                .addValue("userId", userId)
                .addValue("metadata", metadata);

        jdbcTemplate.update(sql, params);
    }

    public List<EventResponse> findEvents(Long userId, EventFilter filter, int limit, int offset) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String whereClause = buildWhereClause(userId, filter, params);

        String sql = "SELECT id, event_type, user_id, created_at, metadata FROM events"
                + whereClause
                + " ORDER BY created_at DESC, id DESC LIMIT :limit OFFSET :offset";

        params.addValue("limit", limit);
        params.addValue("offset", offset);

        return jdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) -> new EventResponse(
                        rs.getLong("id"),
                        rs.getString("event_type"),
                        rs.getLong("user_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("metadata")
                )
        );
    }

    public long countEvents(Long userId, EventFilter filter) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String whereClause = buildWhereClause(userId, filter, params);
        String sql = "SELECT COUNT(*) FROM events" + whereClause;

        Long count = jdbcTemplate.queryForObject(sql, params, Long.class);
        return count == null ? 0L : count;
    }

    private String buildWhereClause(Long userId, EventFilter filter, MapSqlParameterSource params) {
        StringBuilder where = new StringBuilder(" WHERE user_id = :userId");
        params.addValue("userId", userId);

        if (filter == null) {
            return where.toString();
        }

        if (filter.type() != null && !filter.type().isBlank()) {
            where.append(" AND event_type = :type");
            params.addValue("type", filter.type());
        }
        if (filter.entityType() != null && !filter.entityType().isBlank()) {
            where.append(" AND metadata->>'entityType' = :entityType");
            params.addValue("entityType", filter.entityType());
        }
        if (filter.entityId() != null) {
            where.append(" AND (metadata->>'entityId')::bigint = :entityId");
            params.addValue("entityId", filter.entityId());
        }
        if (filter.from() != null) {
            where.append(" AND created_at >= :fromDateTime");
            params.addValue("fromDateTime", filter.from());
        }
        if (filter.to() != null) {
            where.append(" AND created_at <= :toDateTime");
            params.addValue("toDateTime", filter.to());
        }

        return where.toString();
    }
}
