package study.huhao.demo.domain.models.blog.exceptions;

import study.huhao.demo.domain.core.excpetions.EntityNotfoundException;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogId;

public class BlogNotFoundException extends EntityNotfoundException {
    public BlogNotFoundException(BlogId id) {
        super(Blog.class, id);
    }
}
