package study.huhao.demo.domain.core.excpetions;

import study.huhao.demo.domain.core.Entity;
import study.huhao.demo.domain.core.EntityId;

public class EntityExistedException extends DomainException {
    public EntityExistedException(String message) {
        super(message);
    }

    public <T extends Entity> EntityExistedException(Class<T> entityClass, EntityId id) {
        super("the " + entityClass.getSimpleName().toLowerCase() + " with id " + id + " was existed");
    }
}
