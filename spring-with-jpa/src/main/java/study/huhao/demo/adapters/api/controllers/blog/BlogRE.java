package study.huhao.demo.adapters.api.controllers.blog;

import study.huhao.demo.adapters.api.controllers.ResponseEntity;
import study.huhao.demo.domain.models.blog.Blog;

import java.time.Instant;

class BlogRE implements ResponseEntity {
    public String id;
    public String title;
    public String body;
    public String authorId;
    public Blog.PublishStatus status;
    public Instant createdAt;
    public Instant savedAt;
    public PublishedBlogRE published;
}
