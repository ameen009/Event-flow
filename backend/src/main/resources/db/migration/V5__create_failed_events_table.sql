CREATE TABLE failed_events (
    id             BIGSERIAL   PRIMARY KEY,
    event_id       VARCHAR(36) UNIQUE,
    payload        JSONB       NOT NULL,
    failure_reason TEXT,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_failed_events_created_at ON failed_events (created_at DESC);
