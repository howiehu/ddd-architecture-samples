package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Draft {
    private String title;
    private String body;
    private Instant savedAt;
}
