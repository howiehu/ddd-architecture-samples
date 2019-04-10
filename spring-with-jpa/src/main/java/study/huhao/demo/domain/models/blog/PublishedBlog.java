package study.huhao.demo.domain.models.blog;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.Instant;

@AllArgsConstructor
@Value
public class PublishedBlog {
    private String title;
    private String body;
    private Instant publishedAt;
}
