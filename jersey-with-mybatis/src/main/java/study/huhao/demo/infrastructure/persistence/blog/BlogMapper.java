package study.huhao.demo.infrastructure.persistence.blog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogCriteria;

import java.util.List;
import java.util.Optional;

@Component
@Mapper
public interface BlogMapper {
    Optional<BlogPO> findById(@Param("id") String id);

    void update(@Param("blog") BlogPO blog);

    void insert(@Param("blog") BlogPO blog);

    boolean existsById(@Param("id") String id);

    void deleteById(@Param("id") String id);

    List<BlogPO> selectAllByCriteria(@Param("criteria") BlogCriteria criteria);

    long countTotalByCriteria(@Param("criteria")BlogCriteria criteria);
}
