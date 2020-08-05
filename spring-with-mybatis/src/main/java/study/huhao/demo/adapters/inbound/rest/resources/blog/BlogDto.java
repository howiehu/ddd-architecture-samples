package study.huhao.demo.adapters.inbound.rest.resources.blog;

import study.huhao.demo.adapters.inbound.rest.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

import java.time.Instant;
import java.util.UUID;

class BlogDto implements ResponseDto {
    private final UUID id;
    private final String title;
    private final String body;
    private final UUID authorId;
    private final Blog.Status status;
    private final Instant createdAt;
    private final Instant savedAt;

    public BlogDto(UUID id, String title, String body, UUID authorId, Blog.Status status, Instant createdAt, Instant savedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.status = status;
        this.createdAt = createdAt;
        this.savedAt = savedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public UUID getAuthorId() {
        return this.authorId;
    }

    public Blog.Status getStatus() {
        return this.status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getSavedAt() {
        return this.savedAt;
    }

    static BlogDto of(Blog blog) {
        return new BlogDto(
                blog.getId(),
                blog.getTitle(),
                blog.getBody(),
                blog.getAuthorId(),
                blog.getStatus(),
                blog.getCreatedAt(),
                blog.getSavedAt());
    }
}
