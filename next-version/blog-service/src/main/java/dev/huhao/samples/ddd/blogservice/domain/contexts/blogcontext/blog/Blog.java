package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.concepts.AggregateRoot;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.exceptions.NoNeedToPublishException;

import java.time.Instant;
import java.util.UUID;

public class Blog implements AggregateRoot {
    private final UUID id;
    private final UUID authorId;
    private final Instant createdAt;
    private Draft draft;
    private PublishedBlog published;

    // 全参构造函数仅用于数据结构转换或持久化框架
    public Blog(UUID id, UUID authorId, Instant createdAt, Draft draft, PublishedBlog published) {
        this.id = id;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.draft = draft;
        this.published = published;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getAuthorId() {
        return this.authorId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Draft getDraft() {
        return this.draft;
    }

    public PublishedBlog getPublished() {
        return this.published;
    }

    Blog(String draftTitle, String draftBody, UUID authorId) {
        verifyAuthor(authorId);
        verifyTitle(draftTitle);

        this.id = UUID.randomUUID();
        this.authorId = authorId;
        // FIXME: Java的Instant精度（纳秒）与MySQL的Timestamp精度（微秒）不一致，会导致从数据库取出的Instant精度降低。
        this.createdAt = Instant.now();
        this.draft = new Draft(draftTitle, draftBody, this.createdAt);
    }

    void updateDraft(String title, String body) {
        verifyTitle(title);
        verifyBody(body);
        this.draft = new Draft(title, body, Instant.now());
    }

    void publish() {
        assert this.draft != null;
        assert this.draft.getTitle() != null && !this.draft.getTitle().trim().isEmpty();

        verifyBody(this.draft.getBody());
        verifyIsNeedToPublish();

        Instant now = Instant.now();
        Instant publishedAt = this.published == null ? now : this.published.getPublishedAt();
        this.published = new PublishedBlog(this.draft.getTitle(), this.draft.getBody(), publishedAt, now);
    }

    private void verifyIsNeedToPublish() {
        if (this.published != null
                && this.draft.getTitle().equals(this.published.getTitle())
                && this.draft.getBody().equals(this.published.getBody()))
            throw new NoNeedToPublishException();
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
