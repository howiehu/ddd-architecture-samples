package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import java.util.Optional;
import java.util.UUID;

public interface BlogRepository {
    void save(Blog blog);

    Optional<Blog> findById(UUID id);
}
