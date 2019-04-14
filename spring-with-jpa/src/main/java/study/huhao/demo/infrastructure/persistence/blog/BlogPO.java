package study.huhao.demo.infrastructure.persistence.blog;

import lombok.Data;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "blog")
@Data
class BlogPO implements PersistenceObject {

    @Id
    private String id;
    private String title;
    private String body;
    private String authorId;
    @Enumerated(EnumType.STRING)
    private Blog.PublishStatus status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlogPO published;
}
