package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.domain.models.blog.PublishedBlog;
import study.huhao.demo.infrastructure.persistence.Dto;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PublishedBlogDto implements Dto {
    private String publishedTitle;
    private String publishedBody;
    private Instant publishedAt;

    public static PublishedBlogDto of(PublishedBlog draft) {
        return PublishedBlogDto.builder()
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
