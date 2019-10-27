package study.huhao.demo.adapters.inbound.rest.resources.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.adapters.inbound.rest.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
class BlogDto implements ResponseDto {
    private UUID id;
    private String title;
    private String body;
    private UUID authorId;
    private Blog.Status status;
    private Instant createdAt;
    private Instant savedAt;

    static BlogDto of(Blog blog) {
        return new BlogDto(
                blog.getId(),
                blog.getTitle(),
                blog.getBody(),
                blog.getAuthorId(),
                blog.getStatus(),
                blog.getCreatedAt(),
                blog.getSavedAt());
    }
}
