package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.blog;

import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Draft;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.PublishedBlog;

import java.time.Instant;
import java.util.UUID;

public class BlogPO {
    private String id;
    private String authorId;
    private Instant createdAt;
    private String draftTitle;
    private String draftBody;
    private Instant draftSavedAt;
    private String publishedTitle;
    private String publishedBody;
    private Instant publishedAt;
    private Instant updatedAt;

    // Mybatis 等持久化框架需要一个无参构造函数
    BlogPO() {
    }

    BlogPO(String id, String authorId, Instant createdAt, String draftTitle, String draftBody, Instant draftSavedAt, String publishedTitle, String publishedBody, Instant publishedAt, Instant updatedAt) {
        this.id = id;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.draftTitle = draftTitle;
        this.draftBody = draftBody;
        this.draftSavedAt = draftSavedAt;
        this.publishedTitle = publishedTitle;
        this.publishedBody = publishedBody;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return this.id;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public String getDraftTitle() {
        return this.draftTitle;
    }

    public String getDraftBody() {
        return this.draftBody;
    }

    public Instant getDraftSavedAt() {
        return this.draftSavedAt;
    }

    public String getPublishedTitle() {
        return this.publishedTitle;
    }

    public String getPublishedBody() {
        return this.publishedBody;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    public Blog toDomainModel() {
        return new Blog(
                UUID.fromString(id),
                UUID.fromString(authorId),
                createdAt,
                new Draft(draftTitle, draftBody, draftSavedAt),
                publishedAt == null
                        ? null
                        : new PublishedBlog(publishedTitle, publishedBody, publishedAt, updatedAt)
        );
    }

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    static BlogPO of(Blog blog) {
        if (blog == null) return null;
        PublishedBlog published = blog.getPublished();
        return new BlogPO(
                blog.getId().toString(),
                blog.getAuthorId().toString(),
                blog.getCreatedAt(),
                blog.getDraft().getTitle(),
                blog.getDraft().getBody(),
                blog.getDraft().getSavedAt(),
                published == null ? null : published.getTitle(),
                published == null ? null : published.getBody(),
                published == null ? null : published.getPublishedAt(),
                published == null ? null : published.getUpdatedAt());
    }
}
