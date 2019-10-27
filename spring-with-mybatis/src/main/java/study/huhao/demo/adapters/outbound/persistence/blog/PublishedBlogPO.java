package study.huhao.demo.adapters.outbound.persistence.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.huhao.demo.domain.contexts.blogcontext.blog.PublishedBlog;
import study.huhao.demo.adapters.outbound.persistence.PersistenceObject;

import java.time.Instant;

// Lombok annotations
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PublishedBlogPO implements PersistenceObject<PublishedBlog> {
    private String publishedTitle;
    private String publishedBody;
    private Instant publishedAt;

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
