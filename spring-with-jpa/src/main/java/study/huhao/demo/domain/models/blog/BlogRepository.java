package study.huhao.demo.domain.models.blog;

import study.huhao.demo.domain.core.Repository;

public interface BlogRepository extends Repository {
    void save(Blog blog);
}
