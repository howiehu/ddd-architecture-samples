package study.huhao.demo.infrastructure.persistence;

import study.huhao.demo.domain.core.HumbleObject;

public interface PersistenceObject<T> extends HumbleObject {
    T toDomainModel();
}
