package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.adapters.inbound.rest.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.PublishedBlog;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
class PublishedBlogDto implements ResponseDto {
    private UUID id;
    private String title;
    private String body;
    private UUID authorId;
    private Instant publishedAt;

    static PublishedBlogDto of(Blog blog) {
        PublishedBlog published = blog.getPublished();
        return new PublishedBlogDto(
                blog.getId(),
                published.getTitle(),
                published.getBody(),
                blog.getAuthorId(),
                published.getPublishedAt());
    }
}
