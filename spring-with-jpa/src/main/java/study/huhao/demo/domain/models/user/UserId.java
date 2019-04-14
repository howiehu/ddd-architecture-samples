package study.huhao.demo.domain.models.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import study.huhao.demo.domain.core.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
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
