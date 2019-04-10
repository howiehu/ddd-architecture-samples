package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.user.UserId;
import study.huhao.demo.infrastructure.persistence.Dto;

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
    private Instant savedAt;
    private PublishedBlogDto published;

    public static BlogDto of(Blog blog) {

        return BlogDto.builder()
                .id(blog.getId().toString())
                .title(blog.getTitle())
                .body(blog.getBody())
                .author(blog.getAuthor().toString())
                .status(blog.getStatus())
                .createdAt(blog.getCreatedAt())
                .savedAt(blog.getSavedAt())
                .published(blog.getPublished() == null ? null : PublishedBlogDto.of(blog.getPublished()))
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
                savedAt,
                new PublishedBlogDto(
                        published.getPublishedTitle(),
                        published.getPublishedBody(),
                        published.getPublishedAt()
                ).toDomainModel()
        );
    }
}
