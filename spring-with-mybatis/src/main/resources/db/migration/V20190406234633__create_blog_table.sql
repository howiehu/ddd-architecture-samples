CREATE TABLE `blog`
(
    `id`              CHAR(36) PRIMARY KEY,
    `title`           VARCHAR(80),
    `body`            TEXT,
    `author_id`       CHAR(36),
    `status`          VARCHAR(16),
    `created_at`      TIMESTAMP(6),
    `saved_at`        TIMESTAMP(6),
    `published_title` VARCHAR(80),
    `published_body`  TEXT,
    `published_at`    TIMESTAMP(6)
)
