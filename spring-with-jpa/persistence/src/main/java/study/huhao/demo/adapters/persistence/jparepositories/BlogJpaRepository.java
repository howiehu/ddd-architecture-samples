package study.huhao.demo.adapters.persistence.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.huhao.demo.adapters.persistence.dtos.BlogDto;

@Repository
public interface BlogJpaRepository extends JpaRepository<BlogDto, String> {

}
