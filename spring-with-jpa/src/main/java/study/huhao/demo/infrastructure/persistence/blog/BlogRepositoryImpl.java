package study.huhao.demo.infrastructure.persistence.blog;

import ma.glasnost.orika.MapperFacade;
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

    private final BlogJpaRepository blogJpaRepository;
    private final MapperFacade mapper;

    @Autowired
    public BlogRepositoryImpl(BlogJpaRepository blogJpaRepository, MapperFacade mapper) {
        this.blogJpaRepository = blogJpaRepository;
        this.mapper = mapper;
    }


    @Override
    public void save(Blog blog) {
        blogJpaRepository.save(mapper.map(blog, BlogPO.class));
    }

    @Override
    public Optional<Blog> findById(BlogId id) {
        return blogJpaRepository.findById(id.toString()).map(blogPO -> mapper.map(blogPO, Blog.class));
    }

    @Override
    public boolean existById(BlogId id) {
        return blogJpaRepository.existsById(id.toString());
    }

    @Override
    public void deleteById(BlogId id) {
        blogJpaRepository.deleteById(id.toString());
    }

    @Override
    public Page<Blog> findAllWithPagination(BlogCriteria criteria) {
        throw  new UnsupportedOperationException("not implement");
    }
}

