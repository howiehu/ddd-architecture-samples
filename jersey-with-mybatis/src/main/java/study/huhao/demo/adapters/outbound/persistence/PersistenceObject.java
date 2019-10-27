package study.huhao.demo.adapters.outbound.persistence;

import study.huhao.demo.domain.core.concepts.HumbleObject;

import java.io.Serializable;

public interface PersistenceObject<T> extends HumbleObject, Serializable {
    T toDomainModel();
}
