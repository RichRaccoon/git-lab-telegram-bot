CREATE TABLE IF NOT EXISTS pipeline_history
(
    id          BIGINT    NOT NULL PRIMARY KEY,
    message     TEXT      NOT NULL,
    create_date timestamp NOT NULL
);
