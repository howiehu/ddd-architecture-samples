package study.huhao.demo.infrastructure.persistence.blog;

import lombok.Data;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "blog")
@Data
public class BlogPO implements PersistenceObject {

    @Id
    private String id;
    private String title;
    private String body;
    private String authorId;
    private String status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlogPO published;
}
