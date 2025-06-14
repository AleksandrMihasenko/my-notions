CREATE TABLE note_tags (
                           note_id BIGINT NOT NULL,
                           tag_id BIGINT NOT NULL,
                           PRIMARY KEY (note_id, tag_id),
                           FOREIGN KEY (note_id) REFERENCES notes(id) ON DELETE CASCADE,
                           FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);
