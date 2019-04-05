package study.huhao.name.springwithjpa.domain.models.user;

import lombok.*;
import study.huhao.name.springwithjpa.domain.models.base.EntityId;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Getter(AccessLevel.NONE)
public class UserId implements EntityId {
    @NonNull
    private UUID id;

    public UserId() {
        id = UUID.randomUUID();
    }

    public static UserId of(@NonNull String id) {
        return new UserId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
