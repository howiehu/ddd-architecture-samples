package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.blog;

import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BlogPO {
    private String id;
    private String title;
    private String body;
    private String authorId;
    private Instant createdAt;
    private Instant savedAt;

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    public Blog toDomainModel() {
        return new Blog(
                UUID.fromString(id),
                title,
                body,
                UUID.fromString(authorId),
                createdAt,
                savedAt
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
                blog.getCreatedAt(),
                blog.getSavedAt());
    }
}
