package study.huhao.demo.domain.models.blog;

import lombok.AllArgsConstructor;
import lombok.Value;
import study.huhao.demo.domain.core.ValueObject;

import java.time.Instant;

@AllArgsConstructor
@Value
public class BlogDraft implements ValueObject {
    private String title;
    private String body;
    private Instant savedAt;
}
