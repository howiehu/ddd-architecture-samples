package study.huhao.name.springwithjpa.adapters.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.huhao.name.springwithjpa.domain.models.blog.Blog;
import study.huhao.name.springwithjpa.domain.models.user.UserId;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UserId> {
}
