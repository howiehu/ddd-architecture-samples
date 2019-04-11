package study.huhao.demo.infrastructure.persistence.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogRepository;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogJpaRepository blogJpaRepository;

    @Autowired
    public BlogRepositoryImpl(BlogJpaRepository blogJpaRepository) {
        this.blogJpaRepository = blogJpaRepository;
    }


    @Override
    public void save(Blog blog) {
        blogJpaRepository.save(BlogPO.of(blog));
    }
}

