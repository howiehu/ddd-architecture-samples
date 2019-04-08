package study.huhao.demo.adapters.persistence;

import study.huhao.demo.domain.core.HumbleObject;

public interface Dto<T> extends HumbleObject {
    T toDomainModel();
}
