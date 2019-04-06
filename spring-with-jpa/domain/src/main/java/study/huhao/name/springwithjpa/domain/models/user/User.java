package study.huhao.name.springwithjpa.domain.models.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.name.springwithjpa.domain.models.base.AggregateRoot;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User implements AggregateRoot {
    private UserId id;
    private String name;

    public User(String name) {
        this.id = new UserId();
        this.name = name;
    }
}
