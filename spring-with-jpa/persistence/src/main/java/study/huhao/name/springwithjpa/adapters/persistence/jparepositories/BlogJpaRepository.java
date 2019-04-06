package study.huhao.name.springwithjpa.adapters.persistence.jparepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.huhao.name.springwithjpa.adapters.persistence.dtos.BlogDto;

@Repository
public interface BlogJpaRepository extends JpaRepository<BlogDto, String> {

}
