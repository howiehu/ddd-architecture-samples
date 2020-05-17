package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.blog;

import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.BlogRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogMapper blogMapper;

    public BlogRepositoryImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public void save(Blog blog) {
        BlogPO blogPO = BlogPO.of(blog);

        blogMapper.insert(blogPO);
    }

    @Override
    public Optional<Blog> findById(UUID id) {
        return blogMapper.findById(id.toString()).map(BlogPO::toDomainModel);
    }
}
