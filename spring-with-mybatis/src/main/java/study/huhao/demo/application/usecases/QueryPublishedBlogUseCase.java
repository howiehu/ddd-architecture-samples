package study.huhao.demo.application.usecases;

import org.springframework.stereotype.Component;
import study.huhao.demo.application.concepts.UseCase;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;

import java.util.UUID;

@Component
public class QueryPublishedBlogUseCase implements UseCase {
    private final BlogService blogService;

    public QueryPublishedBlogUseCase(BlogRepository blogRepository) {
        // 依赖注入是一种应用需要和技术实现细节，所以在 UseCase 里使用依赖注入框架，通过实例化 DomainService 并注入相关依赖的方式实现了 Domain 与技术框架的解耦。
        this.blogService = new BlogService(blogRepository);
    }

    public Blog get(UUID id) {
        return blogService.getPublished(id);
    }
}
