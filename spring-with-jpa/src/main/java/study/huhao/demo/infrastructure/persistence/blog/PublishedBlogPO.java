package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.domain.models.blog.PublishedBlog;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PublishedBlogPO implements PersistenceObject {
    private String publishedTitle;
    private String publishedBody;
    private Instant publishedAt;

    public static PublishedBlogPO of(PublishedBlog draft) {
        return PublishedBlogPO.builder()
                .publishedTitle(draft.getTitle())
                .publishedBody(draft.getBody())
                .publishedAt(draft.getPublishedAt())
                .build();
    }

    @Override
    public PublishedBlog toDomainModel() {
        return new PublishedBlog(publishedTitle, publishedBody, publishedAt);
    }
}
