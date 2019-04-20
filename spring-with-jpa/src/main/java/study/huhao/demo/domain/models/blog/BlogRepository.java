package study.huhao.demo.domain.models.blog;

import study.huhao.demo.domain.core.Repository;

import java.util.Optional;

public interface BlogRepository extends Repository {
    void save(Blog blog);

    Optional<Blog> findById(BlogId id);

    boolean existById(BlogId id);

    void deleteById(BlogId id);
}
