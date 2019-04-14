package study.huhao.demo.adapters.api.resources.blog;

import study.huhao.demo.adapters.api.resources.ResponseDto;
import study.huhao.demo.domain.models.blog.Blog;

import java.time.Instant;

class BlogDto implements ResponseDto {
    public String id;
    public String title;
    public String body;
    public String authorId;
    public Blog.PublishStatus status;
    public Instant createdAt;
    public Instant savedAt;
    public PublishedBlogDto published;
}
