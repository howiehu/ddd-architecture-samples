package study.huhao.name.springwithjpa.adapters.persistence.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.name.springwithjpa.adapters.persistence.dtos.BlogDto;
import study.huhao.name.springwithjpa.adapters.persistence.jparepositories.BlogJpaRepository;
import study.huhao.name.springwithjpa.domain.models.blog.Blog;
import study.huhao.name.springwithjpa.domain.models.blog.BlogRepository;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogJpaRepository blogJpaRepository;

    @Autowired
    public BlogRepositoryImpl(BlogJpaRepository blogJpaRepository) {
        this.blogJpaRepository = blogJpaRepository;
    }


    @Override
    public void save(Blog blog) {
        blogJpaRepository.save(BlogDto.of(blog));
    }
}

