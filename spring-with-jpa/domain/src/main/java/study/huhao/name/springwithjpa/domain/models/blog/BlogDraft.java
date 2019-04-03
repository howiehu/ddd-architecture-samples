package study.huhao.name.springwithjpa.domain.models.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import study.huhao.name.springwithjpa.domain.models.base.ValueObject;

import java.time.Instant;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Value
public class BlogDraft implements ValueObject {
    @NonNull
    private BlogId blogId;
    @NonNull
    private String title;
    private String body;
    @NonNull
    private Instant savedAt;
}
