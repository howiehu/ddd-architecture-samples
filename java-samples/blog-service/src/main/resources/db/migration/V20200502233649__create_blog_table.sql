CREATE TABLE `blog`
(
    `id`              CHAR(36) PRIMARY KEY,
    `title`           VARCHAR(80),
    `body`            TEXT,
    `author_id`       CHAR(36),
    `created_at`      TIMESTAMP(6),
    `saved_at`        TIMESTAMP(6)
)
