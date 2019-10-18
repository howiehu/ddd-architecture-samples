package study.huhao.demo.adapters.restapi.resources.publishedblog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.adapters.restapi.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

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
        return new PublishedBlogDto(
                blog.getId(),
                blog.getPublished().getTitle(),
                blog.getPublished().getBody(),
                blog.getAuthorId(),
                blog.getPublished().getPublishedAt());
    }
}
