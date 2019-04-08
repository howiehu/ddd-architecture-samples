package study.huhao.demo.infrastructure.persistence.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogJpaRepository extends JpaRepository<BlogDto, String> {

}
