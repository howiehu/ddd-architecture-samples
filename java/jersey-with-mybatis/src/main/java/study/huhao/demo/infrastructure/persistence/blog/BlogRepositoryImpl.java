package study.huhao.demo.infrastructure.persistence.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;

import java.util.Optional;


@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogMapper blogMapper;

    @Autowired
    public BlogRepositoryImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public void save(Blog blog) {

    }

    @Override
    public Optional<Blog> findById(BlogId id) {
        return Optional.empty();
    }

    @Override
    public boolean existById(BlogId id) {
        return false;
    }

    @Override
    public void deleteById(BlogId id) {

    }

    @Override
    public Page<Blog> findAllWithPagination(BlogCriteria criteria) {
        return null;
    }
}

