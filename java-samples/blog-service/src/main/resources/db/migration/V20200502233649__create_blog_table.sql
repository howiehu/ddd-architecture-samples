CREATE TABLE `blog`
(
    `id`              CHAR(36) PRIMARY KEY,
    `author_id`       CHAR(36),
    `created_at`      TIMESTAMP(6),
    `draft_title`           VARCHAR(80),
    `draft_body`            TEXT,
    `draft_saved_at`        TIMESTAMP(6)
)
