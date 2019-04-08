package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.infrastructure.persistence.Dto;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.user.UserId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "blog")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BlogDto implements Dto<Blog> {

    @Id
    private String id;
    private String title;
    private String body;
    private String author;
    @Enumerated(EnumType.STRING)
    private Blog.PublishStatus status;
    private Instant createdAt;
    private Instant publishedAt;
    private Instant lastModifiedAt;
    private BlogDraftDto draft;

    public static BlogDto of(Blog blog) {

        return BlogDto.builder()
                .id(blog.getId().toString())
                .title(blog.getTitle())
                .body(blog.getBody())
                .author(blog.getAuthor().toString())
                .status(blog.getStatus())
                .createdAt(blog.getCreatedAt())
                .publishedAt(blog.getPublishedAt())
                .lastModifiedAt(blog.getLastModifiedAt())
                .draft(BlogDraftDto.of(blog.getDraft()))
                .build();
    }

    @Override
    public Blog toDomainModel() {

        return new Blog(
                BlogId.of(id),
                title,
                body,
                UserId.of(author),
                status,
                createdAt,
                publishedAt,
                lastModifiedAt,
                new BlogDraftDto(draft.getDraftTitle(), draft.getDraftBody(), draft.getDraftSavedAt()).toDomainModel()
        );
    }
}
