CREATE TABLE `blog`
(
    `id`               CHAR(36) PRIMARY KEY,
    `title`            VARCHAR(80),
    `body`             TEXT,
    `author`           CHAR(36),
    `status`           VARCHAR(16),
    `created_at`       TIMESTAMP(6),
    `published_at`     TIMESTAMP(6),
    `last_modified_at` TIMESTAMP(6),
    `draft_title`      VARCHAR(80),
    `draft_body`       TEXT,
    `draft_saved_at`   TIMESTAMP(6)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_bin;
