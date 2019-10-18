package study.huhao.demo.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.concepts.UseCase;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;

import java.util.UUID;

@Component
public class QueryPublishedBlogUseCase implements UseCase {
    private final BlogService blogService;

    @Autowired
    public QueryPublishedBlogUseCase(BlogRepository blogRepository) {
        this.blogService = new BlogService(blogRepository);
    }

    public Blog get(UUID id) {
        return blogService.getPublished(id);
    }
}
