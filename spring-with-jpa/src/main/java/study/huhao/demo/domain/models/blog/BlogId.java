package study.huhao.demo.domain.models.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import study.huhao.demo.domain.core.ValueObject;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
public class BlogId implements ValueObject {
    private UUID id;

    protected BlogId() {
        id = UUID.randomUUID();
    }

    public static BlogId valueOf(String id) {
        return new BlogId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
