package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.blog;

import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Draft;
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
    private String authorId;
    private Instant createdAt;
    private String draftTitle;
    private String draftBody;
    private Instant draftSavedAt;

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    public Blog toDomainModel() {
        return new Blog(
                UUID.fromString(id),
                UUID.fromString(authorId),
                createdAt,
                new Draft(draftTitle, draftBody, draftSavedAt)
        );
    }

    // The persistence object needs to reflect the table structure.
    // The domain model and persistence object may have much different.
    // So, manual to convert between them is better than use object mapper like Orika.
    static BlogPO of(Blog blog) {
        return blog == null ? null : new BlogPO(
                blog.getId().toString(),
                blog.getAuthorId().toString(),
                blog.getCreatedAt(),
                blog.getDraft().getTitle(),
                blog.getDraft().getBody(),
                blog.getDraft().getSavedAt());
    }
}
