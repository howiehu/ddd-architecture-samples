package dev.huhao.samples.ddd.blogservice.domain.common;


import dev.huhao.samples.ddd.blogservice.domain.concepts.Entity;

import java.util.UUID;

public class EntityExistedException extends DomainException {
    public EntityExistedException(String message) {
        super(message);
    }

    public <T extends Entity> EntityExistedException(Class<T> entityClass, UUID id) {
        super("the " + entityClass.getSimpleName().toLowerCase() + " with id " + id + " was existed");
    }
}
