package study.huhao.name.springwithjpa.domain.models.page;

import lombok.*;
import study.huhao.name.springwithjpa.domain.models.base.EntityId;
import study.huhao.name.springwithjpa.domain.models.base.ValueObject;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
public class PageId implements EntityId {

    private UUID id;

    protected PageId() {
        id = UUID.randomUUID();
    }

    public static PageId of(@NonNull String id) {
        return new PageId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
