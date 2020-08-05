package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import study.huhao.demo.adapters.inbound.rest.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.PublishedBlog;

import java.time.Instant;
import java.util.UUID;

class PublishedBlogDto implements ResponseDto {
    private final UUID id;
    private final String title;
    private final String body;
    private final UUID authorId;
    private final Instant publishedAt;

    public PublishedBlogDto(UUID id, String title, String body, UUID authorId, Instant publishedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.publishedAt = publishedAt;
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

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    static PublishedBlogDto of(Blog blog) {
        PublishedBlog published = blog.getPublished();
        return new PublishedBlogDto(
                blog.getId(),
                published.getTitle(),
                published.getBody(),
                blog.getAuthorId(),
                published.getPublishedAt());
    }
}
