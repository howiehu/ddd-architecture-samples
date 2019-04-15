package study.huhao.demo.domain.core.excpetions;

import study.huhao.demo.domain.core.Entity;
import study.huhao.demo.domain.core.EntityId;

public abstract class EntityNotFoundException extends DomainException {
    public <T extends Entity> EntityNotFoundException(Class<T> entityClass, EntityId id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
