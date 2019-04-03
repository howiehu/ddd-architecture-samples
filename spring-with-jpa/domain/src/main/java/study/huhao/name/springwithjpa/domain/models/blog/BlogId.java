package study.huhao.name.springwithjpa.domain.models.blog;

import lombok.*;
import study.huhao.name.springwithjpa.domain.models.base.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
public class BlogId implements EntityId {

    private UUID id;

    protected BlogId() {
        id = UUID.randomUUID();
    }

    public static BlogId of(@NonNull String id) {
        return new BlogId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
