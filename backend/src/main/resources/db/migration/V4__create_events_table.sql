CREATE TABLE events (
                       id BIGSERIAL PRIMARY KEY,
                       event_type VARCHAR(64) NOT NULL,
                       user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       metadata JSONB NULL
);

CREATE INDEX idx_events_user_id_created_at ON events(user_id, created_at);
CREATE INDEX idx_events_event_type_created_at ON events(event_type, created_at);
