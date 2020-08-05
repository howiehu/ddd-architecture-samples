package study.huhao.demo.adapters.outbound.persistence.blog;

import study.huhao.demo.adapters.outbound.persistence.PersistenceObject;
import study.huhao.demo.domain.contexts.blogcontext.blog.PublishedBlog;

import java.time.Instant;

public class PublishedBlogPO implements PersistenceObject<PublishedBlog> {
    private String publishedTitle;
    private String publishedBody;
    private Instant publishedAt;

    // Mybatis 等持久化框架需要一个无参构造函数
    PublishedBlogPO() {
    }

    PublishedBlogPO(String publishedTitle, String publishedBody, Instant publishedAt) {
        this.publishedTitle = publishedTitle;
        this.publishedBody = publishedBody;
        this.publishedAt = publishedAt;
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

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    @Override
    public PublishedBlog toDomainModel() {
        return new PublishedBlog(publishedTitle, publishedBody, publishedAt);
    }

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    static PublishedBlogPO of(PublishedBlog published) {
        return published == null ? null : new PublishedBlogPO(
                published.getTitle(),
                published.getBody(),
                published.getPublishedAt());
    }
}
