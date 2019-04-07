CREATE TABLE `blog`
(
    `id`             CHAR(36) PRIMARY KEY,
    `title`          VARCHAR(80),
    `body`           TEXT,
    `author`         CHAR(36),
    `status`         VARCHAR(16),
    `createdAt`      TIMESTAMP(6),
    `publishedAt`    TIMESTAMP(6),
    `lastModifiedAt` TIMESTAMP(6),
    `draftTitle`     VARCHAR(80),
    `draftBody`      TEXT,
    `draftSavedAt`   TIMESTAMP(6)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
