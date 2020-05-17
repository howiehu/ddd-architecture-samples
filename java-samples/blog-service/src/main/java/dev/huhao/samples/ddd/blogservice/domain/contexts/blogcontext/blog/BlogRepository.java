package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.concepts.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BlogRepository extends Repository {
    void save(Blog blog);

    Optional<Blog> findById(UUID id);
}
