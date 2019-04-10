package study.huhao.demo.infrastructure.persistence.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogRepository;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final MapperFacade mapperFacade;

    private final BlogJpaRepository blogJpaRepository;

    @Autowired
    public BlogRepositoryImpl(MapperFacade mapperFacade, BlogJpaRepository blogJpaRepository) {
        this.mapperFacade = mapperFacade;
        this.blogJpaRepository = blogJpaRepository;
    }


    @Override
    public void save(Blog blog) {
        var blogPO = mapperFacade.map(blog, BlogPO.class);
        BlogPO save = blogJpaRepository.save(blogPO);

        Blog map = mapperFacade.map(save, Blog.class);
    }
}

