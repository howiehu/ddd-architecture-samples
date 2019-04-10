package study.huhao.demo.infrastructure.persistence.blog;

import lombok.*;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.user.UserId;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "blog")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BlogPO implements PersistenceObject<Blog> {

    @Id
    private String id;
    private String title;
    private String body;
    private String author;
    @Enumerated(EnumType.STRING)
    private Blog.PublishStatus status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlogPO published;

    public static BlogPO of(Blog blog) {

        return BlogPO.builder()
                .id(blog.getId().toString())
                .title(blog.getTitle())
                .body(blog.getBody())
                .author(blog.getAuthor().toString())
                .status(blog.getStatus())
                .createdAt(blog.getCreatedAt())
                .savedAt(blog.getSavedAt())
                .published(blog.getPublished() == null ? null : PublishedBlogPO.of(blog.getPublished()))
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
                new PublishedBlogPO(
                        published.getPublishedTitle(),
                        published.getPublishedBody(),
                        published.getPublishedAt()
                ).toDomainModel()
        );
    }
}
