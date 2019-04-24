package study.huhao.demo.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.domain.core.AggregateRoot;

@AllArgsConstructor
@Getter
public class User implements AggregateRoot {
    private UserId id;
    private String name;

    User(String name) {
        this.id = new UserId();
        this.name = name;
    }
}
