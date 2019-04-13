package study.huhao.demo.adapters.api.controllers.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import study.huhao.demo.adapters.api.controllers.ResponseEntity;
import study.huhao.demo.domain.models.blog.Blog;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogRE implements ResponseEntity {
    public String id;
    public String title;
    public String body;
    public String authorId;
    public Blog.PublishStatus status;
    public Instant createdAt;
    public Instant savedAt;
    public PublishedBlogRE published;

    public static BlogRE of(Blog blog) {
        if (blog == null) {
            return null;
        }

        return BlogRE.builder()
                .id(blog.getId().toString())
                .title(blog.getTitle())
                .body(blog.getBody())
                .authorId(blog.getAuthorId().toString())
                .status(blog.getStatus())
                .createdAt(blog.getCreatedAt())
                .savedAt(blog.getSavedAt())
                .published(PublishedBlogRE.of(blog.getPublished()))
                .build();
    }
}
