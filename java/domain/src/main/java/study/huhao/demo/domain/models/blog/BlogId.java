package study.huhao.demo.domain.models.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import study.huhao.demo.domain.core.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BlogId implements EntityId {
    private UUID id;

    BlogId() {
        id = UUID.randomUUID();
    }

    public static BlogId valueOf(String id) {
        return id == null ? null : new BlogId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
