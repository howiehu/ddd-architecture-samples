package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.concepts.ValueObject;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Draft implements ValueObject {
    private String title;
    private String body;
    private Instant savedAt;
}
