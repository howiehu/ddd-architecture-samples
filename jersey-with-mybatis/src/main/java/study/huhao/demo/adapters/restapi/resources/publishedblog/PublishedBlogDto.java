package study.huhao.demo.adapters.restapi.resources.publishedblog;

import lombok.Builder;
import lombok.Getter;
import study.huhao.demo.adapters.restapi.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
class PublishedBlogDto implements ResponseDto {
    private UUID id;
    private String title;
    private String body;
    private UUID authorId;
    private Instant publishedAt;

    static PublishedBlogDto of(Blog blog) {
        return PublishedBlogDto.builder()
                .id(blog.getId())
                .title(blog.getPublished().getTitle())
                .body(blog.getPublished().getBody())
                .authorId(blog.getAuthorId())
                .publishedAt(blog.getPublished().getPublishedAt())
                .build();
    }
}
