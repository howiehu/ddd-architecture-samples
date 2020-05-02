package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import java.util.UUID;

public class BlogDomainService {

    private final BlogRepository blogRepository;

    public BlogDomainService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createDraft(String title, String body, UUID authorId) {
        Blog blog = new Blog(title, body, authorId);
        blogRepository.save(blog);
        return blog;
    }
}
