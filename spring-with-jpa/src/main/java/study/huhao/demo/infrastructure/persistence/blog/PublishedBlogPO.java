package study.huhao.demo.infrastructure.persistence.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.huhao.demo.domain.models.blog.PublishedBlog;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PublishedBlogPO implements PersistenceObject<PublishedBlog> {
    @Column(name = "published_title")
    private String title;
    @Column(name = "published_body")
    private String body;
    private Instant publishedAt;

    // JPA's entity needs to reflect the table structure.
    // The domain model and persistence object may have more different.
    // So, manual to convert between them is better than use object mapper like Orika.
    @Override
    public PublishedBlog toDomainModel() {
        return new PublishedBlog(title, body, publishedAt);
    }

    // JPA's entity needs to reflect the table structure.
    // The domain model and persistence object may have more different.
    // So, manual to convert between them is better than use object mapper like Orika.
    static PublishedBlogPO of(PublishedBlog published) {
        if (published == null) return null;
        return PublishedBlogPO.builder()
                .title(published.getTitle())
                .body(published.getBody())
                .publishedAt(published.getPublishedAt())
                .build();
    }
}
