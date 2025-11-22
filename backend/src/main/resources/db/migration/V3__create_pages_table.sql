CREATE TABLE pages (
                       id BIGSERIAL PRIMARY KEY,
                       workspace_id BIGINT NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
                       title VARCHAR(255) NOT NULL,
                       content TEXT,
                       parent_id BIGINT REFERENCES pages(id) ON DELETE CASCADE,
                       created_by BIGINT NOT NULL REFERENCES users(id),
                       is_deleted BOOLEAN DEFAULT FALSE,  -- soft delete для страниц
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pages_workspace_id ON pages(workspace_id);
CREATE INDEX idx_pages_parent_id ON pages(parent_id);
CREATE INDEX idx_pages_created_by ON pages(created_by);
CREATE INDEX idx_pages_is_deleted ON pages(is_deleted);