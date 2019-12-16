package study.huhao.demo.application.usecases;

import org.springframework.stereotype.Component;
import study.huhao.demo.application.concepts.UseCase;
import study.huhao.demo.application.dto.Page;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogCriteria;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;

import java.util.UUID;

@Component
public class QueryBlogUseCase implements UseCase {
    private final BlogService blogService;
    private final BlogRepository blogRepository;

    public QueryBlogUseCase(BlogRepository blogRepository) {
        // 依赖注入是一种应用需要和技术实现细节，所以在 UseCase 里使用依赖注入框架，通过实例化 DomainService 并注入相关依赖的方式实现了 Domain 与技术框架的解耦。
        this.blogService = new BlogService(blogRepository);
        this.blogRepository = blogRepository;
    }

    public Blog get(UUID id) {
        return blogService.get(id);
    }

    // 分页查询是一种数据读取方面的应用需求，所以应该在 UseCase 中实现，而非在 DomainService 中实现
    public Page<Blog> query(int limit, int offset) {
        var criteria = new BlogCriteria(limit, offset);

        var total = blogRepository.count(criteria);

        var pagedBlog = blogRepository.findAll(criteria);

        return new Page<>(
                pagedBlog,
                criteria.getLimit(),
                criteria.getOffset(),
                total
        );
    }
}
