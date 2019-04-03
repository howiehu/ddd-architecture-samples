package study.huhao.name.springwithjpa.domain.models.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.name.springwithjpa.domain.models.base.AggregateRoot;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Message implements AggregateRoot {
}
