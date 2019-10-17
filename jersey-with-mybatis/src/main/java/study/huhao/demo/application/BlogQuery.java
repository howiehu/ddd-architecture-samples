package study.huhao.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogCriteria;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;
import study.huhao.demo.domain.core.common.Page;

import java.util.UUID;

@Service
public class BlogQuery {
    private final BlogService blogService;

    @Autowired
    public BlogQuery(BlogRepository blogRepository) {
        this.blogService = new BlogService(blogRepository);
    }

    public Blog get(UUID id) {
        return blogService.get(id);
    }

    public Page<Blog> query(int limit, int offset) {
        BlogCriteria criteria = BlogCriteria.builder().limit(limit).offset(offset).build();
        return blogService.query(criteria);
    }
}
