package study.huhao.demo.domain.core.excpetions;

import study.huhao.demo.domain.core.Entity;
import study.huhao.demo.domain.core.EntityId;

public class EntityNotfoundException extends DomainServiceException {
    public <T extends Entity> EntityNotfoundException(Class<T> entityClass, EntityId id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
