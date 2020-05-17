package dev.huhao.samples.ddd.blogservice.domain.common;

import dev.huhao.samples.ddd.blogservice.domain.concepts.Entity;

import java.util.UUID;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public <T extends Entity> EntityNotFoundException(Class<T> entityClass, UUID id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
