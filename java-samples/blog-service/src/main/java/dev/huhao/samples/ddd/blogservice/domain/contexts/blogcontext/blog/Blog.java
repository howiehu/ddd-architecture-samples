package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.concepts.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Blog implements AggregateRoot {
    private final UUID id;
    private final UUID authorId;
    private final Instant createdAt;
    private Draft draft;

    Blog(String draftTitle, String draftBody, UUID authorId) {
        verifyAuthor(authorId);
        verifyTitle(draftTitle);

        this.id = UUID.randomUUID();
        this.authorId = authorId;
        // FIXME: Java的Instant精度（纳秒）与MySQL的Timestamp精度（微秒）不一致，会导致从数据库取出的Instant精度降低。
        this.createdAt = Instant.now();
        this.draft = new Draft(draftTitle, draftBody, this.createdAt);
    }

    void saveDraft(String title, String body) {
        verifyTitle(title);
        verifyBody(body);
        this.draft = new Draft(title, body, Instant.now());
    }

    private void verifyTitle(String title) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("the title cannot be blank");
    }

    private void verifyBody(String body) {
        if (body == null || body.trim().isEmpty()) throw new IllegalArgumentException("the body cannot be blank");
    }

    private void verifyAuthor(UUID authorId) {
        if (authorId == null) throw new IllegalArgumentException("the blog must have author");
    }
}
