package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.domain.blogcontext.blog.PublishedBlog;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import java.time.Instant;

// Lombok annotations
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
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
        if (published == null) return null;
        return PublishedBlogPO.builder()
                .publishedTitle(published.getTitle())
                .publishedBody(published.getBody())
                .publishedAt(published.getPublishedAt())
                .build();
    }
}
