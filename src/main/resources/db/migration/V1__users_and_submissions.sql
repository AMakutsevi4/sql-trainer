CREATE TABLE app_users (
    id              BIGSERIAL PRIMARY KEY,
    login           VARCHAR(64)  NOT NULL UNIQUE,
    password_hash   VARCHAR(100) NOT NULL,
    role            VARCHAR(32)  NOT NULL,
    active          BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE submissions (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT       NOT NULL REFERENCES app_users(id),
    task_id         VARCHAR(64)  NOT NULL,
    sql_text        TEXT         NOT NULL,
    passed          BOOLEAN      NOT NULL,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_submissions_user_task ON submissions(user_id, task_id);
CREATE INDEX idx_submissions_user_created ON submissions(user_id, created_at DESC);
