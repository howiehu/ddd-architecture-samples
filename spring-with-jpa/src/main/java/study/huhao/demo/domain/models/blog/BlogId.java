package study.huhao.demo.domain.models.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import study.huhao.demo.domain.core.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.NONE)
@EqualsAndHashCode
public class BlogId implements EntityId {
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
