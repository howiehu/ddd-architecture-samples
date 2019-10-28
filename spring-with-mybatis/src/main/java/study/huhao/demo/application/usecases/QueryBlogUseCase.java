package study.huhao.demo.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.concepts.UseCase;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogCriteria;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;
import study.huhao.demo.domain.core.common.Page;

import java.util.UUID;

@Component
public class QueryBlogUseCase implements UseCase {
    private final BlogService blogService;

    @Autowired
    public QueryBlogUseCase(BlogRepository blogRepository) {
        // 依赖注入是一种应用需要和技术实现细节，所以在 UseCase 里使用依赖注入框架，通过实例化 DomainService 并注入相关依赖的方式实现了 Domain 与技术框架的解耦。
        this.blogService = new BlogService(blogRepository);
    }

    public Blog get(UUID id) {
        return blogService.get(id);
    }

    public Page<Blog> query(int limit, int offset) {
        return blogService.query(new BlogCriteria(limit, offset));
    }
}
