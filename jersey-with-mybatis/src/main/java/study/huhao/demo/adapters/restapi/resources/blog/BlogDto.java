package study.huhao.demo.adapters.restapi.resources.blog;

import study.huhao.demo.adapters.restapi.resources.ResponseDto;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

import java.time.Instant;
import java.util.UUID;

class BlogDto implements ResponseDto {
    public UUID id;
    public String title;
    public String body;
    public UUID authorId;
    public Blog.Status status;
    public Instant createdAt;
    public Instant savedAt;
    public PublishedBlogDto published;
}
