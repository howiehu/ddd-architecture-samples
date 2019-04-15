package study.huhao.demo.infrastructure.persistence.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;

import java.util.Optional;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogJpaRepository blogJpaRepository;
    private final MapperFacade mapperFacade;

    @Autowired
    public BlogRepositoryImpl(BlogJpaRepository blogJpaRepository, MapperFacade mapperFacade) {
        this.blogJpaRepository = blogJpaRepository;
        this.mapperFacade = mapperFacade;
    }


    @Override
    public void save(Blog blog) {
        BlogPO blogDto = mapperFacade.map(blog, BlogPO.class);
        blogJpaRepository.save(blogDto);
    }

    @Override
    public Optional<Blog> findById(BlogId id) {
        return blogJpaRepository.findById(id.toString()).map(blogDto -> mapperFacade.map(blogDto, Blog.class));
    }
}

