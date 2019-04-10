CREATE TABLE `blog`
(
    `id`              CHAR(36) PRIMARY KEY,
    `title`           VARCHAR(80),
    `body`            TEXT,
    `author`          CHAR(36),
    `status`          VARCHAR(16),
    `created_at`      TIMESTAMP(6),
    `saved_at`        TIMESTAMP(6),
    `published_title` VARCHAR(80),
    `published_body`  TEXT,
    `published_at`    TIMESTAMP(6)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_bin;
