package study.huhao.name.springwithjpa.domain.models.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import study.huhao.name.springwithjpa.domain.models.base.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
public class PageId implements EntityId {
    private UUID id;

    protected PageId() {
        id = UUID.randomUUID();
    }

    public static PageId of(String id) {
        return new PageId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
