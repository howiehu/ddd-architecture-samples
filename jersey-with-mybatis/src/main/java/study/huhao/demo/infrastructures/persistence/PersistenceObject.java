package study.huhao.demo.infrastructures.persistence;

import study.huhao.demo.domain.core.concepts.HumbleObject;

import java.io.Serializable;

public interface PersistenceObject<T> extends HumbleObject, Serializable {
    T toDomainModel();
}
