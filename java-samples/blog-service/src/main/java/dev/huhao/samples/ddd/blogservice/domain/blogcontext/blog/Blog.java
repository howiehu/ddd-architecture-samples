package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Blog {
    private final UUID id;
    private final UUID authorId;
    private final Instant createdAt;
    private final Draft draft;

    Blog(String draftTitle, String draftBody, UUID authorId) {

        if (authorId == null) throw new IllegalArgumentException("the blog must have author");

        this.id = UUID.randomUUID();
        this.authorId = authorId;
        // FIXME: Java的Instant精度（纳秒）与MySQL的Timestamp精度（微秒）不一致，会导致从数据库取出的Instant精度降低。
        this.createdAt = Instant.now();
        this.draft = new Draft(draftTitle, draftBody, this.createdAt);
    }
}
