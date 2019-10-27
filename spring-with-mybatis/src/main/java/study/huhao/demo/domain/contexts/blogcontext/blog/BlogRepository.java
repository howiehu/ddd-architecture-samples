package study.huhao.demo.domain.contexts.blogcontext.blog;

import study.huhao.demo.domain.core.common.Page;
import study.huhao.demo.domain.core.concepts.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BlogRepository extends Repository {
    void save(Blog blog);

    Optional<Blog> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);

    Page<Blog> findAllWithPagination(BlogCriteria criteria);
}
