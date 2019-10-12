package study.huhao.demo.domain.usercontext.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.domain.core.AggregateRoot;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User implements AggregateRoot {
    private UUID id;
    private String name;

    User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
