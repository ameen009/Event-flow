CREATE TABLE task_execution_history (
    id                 BIGSERIAL   PRIMARY KEY,
    task_id            BIGINT      NOT NULL REFERENCES tasks (id) ON DELETE CASCADE,
    status             VARCHAR(20) NOT NULL,
    start_time         TIMESTAMPTZ,
    end_time           TIMESTAMPTZ,
    execution_duration BIGINT,
    error_message      TEXT,
    CONSTRAINT chk_exec_status CHECK (status IN ('RUNNING', 'SUCCESS', 'FAILED'))
);

CREATE INDEX idx_execution_history_task_id ON task_execution_history (task_id);
CREATE INDEX idx_execution_history_status  ON task_execution_history (status);
