package study.huhao.demo.domain.core.common.excpetions;

import study.huhao.demo.domain.core.concepts.Entity;

import java.util.UUID;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public <T extends Entity> EntityNotFoundException(Class<T> entityClass, UUID id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
