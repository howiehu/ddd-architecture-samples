package study.huhao.demo.infrastructure.persistence.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import java.time.Instant;
import java.util.UUID;

// Lombok annotations
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BlogPO implements PersistenceObject<Blog> {

    private String id;
    private String title;
    private String body;
    private String authorId;
    private String status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlogPO published;

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    @Override
    public Blog toDomainModel() {
        return new Blog(
                UUID.fromString(id),
                title,
                body,
                UUID.fromString(authorId),
                Blog.Status.valueOf(status),
                createdAt,
                savedAt,
                published == null ? null : published.toDomainModel()
        );
    }

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    static BlogPO of(Blog blog) {
        return blog == null ? null : new BlogPO(
                blog.getId().toString(),
                blog.getTitle(),
                blog.getBody(),
                blog.getAuthorId().toString(),
                blog.getStatus().toString(),
                blog.getCreatedAt(),
                blog.getSavedAt(),
                PublishedBlogPO.of(blog.getPublished()));
    }
}
