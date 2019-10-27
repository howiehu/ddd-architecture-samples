package study.huhao.demo.domain.contexts.blogcontext.blog;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import study.huhao.demo.domain.core.concepts.ValueObject;

import java.time.Instant;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PublishedBlog implements ValueObject {
    private String title;
    private String body;
    private Instant publishedAt;
}
