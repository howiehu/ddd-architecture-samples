package study.huhao.demo.infrastructure.persistence.blog;

import lombok.Data;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Data
class PublishedBlogPO implements PersistenceObject {
    @Column(name = "published_title")
    private String title;
    @Column(name = "published_body")
    private String body;
    private Instant publishedAt;
}
