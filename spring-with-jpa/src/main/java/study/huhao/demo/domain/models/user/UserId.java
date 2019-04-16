package study.huhao.demo.domain.models.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import study.huhao.demo.domain.core.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.NONE)
@EqualsAndHashCode
public class UserId implements EntityId {
    private UUID id;

    protected UserId() {
        id = UUID.randomUUID();
    }

    public static UserId valueOf(String id) {
        return new UserId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
