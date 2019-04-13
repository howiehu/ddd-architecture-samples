package study.huhao.demo.adapters.api.controllers.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import study.huhao.demo.domain.models.blog.PublishedBlog;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishedBlogRE {
    public String title;
    public String body;
    public Instant publishedAt;

    public static PublishedBlogRE of(PublishedBlog publishedBlog) {
        if (publishedBlog == null) {
            return null;
        }

        return PublishedBlogRE.builder()
                .title(publishedBlog.getTitle())
                .body(publishedBlog.getBody())
                .publishedAt(publishedBlog.getPublishedAt())
                .build();
    }
}
