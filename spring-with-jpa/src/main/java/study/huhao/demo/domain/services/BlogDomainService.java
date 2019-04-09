package study.huhao.demo.domain.services;

import study.huhao.demo.domain.core.DomainService;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;

public class BlogDomainService implements DomainService {

    private BlogRepository blogRepository;

    public BlogDomainService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(String title, String body, UserId author) {
        var blog = new Blog(title, body, author);
        blogRepository.save(blog);
        return blog;
    }
}
