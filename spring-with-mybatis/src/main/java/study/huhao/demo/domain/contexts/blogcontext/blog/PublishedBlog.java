package study.huhao.demo.domain.contexts.blogcontext.blog;

import study.huhao.demo.domain.core.concepts.ValueObject;

import java.time.Instant;
import java.util.Objects;

public class PublishedBlog implements ValueObject {
    private final String title;
    private final String body;
    private final Instant publishedAt;

    public PublishedBlog(String title, String body, Instant publishedAt) {
        this.title = title;
        this.body = body;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishedBlog that = (PublishedBlog) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(body, that.body) &&
                Objects.equals(publishedAt, that.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, publishedAt);
    }
}
