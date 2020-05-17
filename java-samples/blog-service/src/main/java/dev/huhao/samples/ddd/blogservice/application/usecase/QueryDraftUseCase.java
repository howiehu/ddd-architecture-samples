package dev.huhao.samples.ddd.blogservice.application.usecase;

import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.BlogDomainService;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.BlogRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueryDraftUseCase {
    private final BlogDomainService blogDomainService;

    public QueryDraftUseCase(BlogRepository blogRepository) {
        this.blogDomainService = new BlogDomainService(blogRepository);
    }

    public Blog getByBlogId(UUID id) {
        return blogDomainService.getBlog(id);
    }
}
