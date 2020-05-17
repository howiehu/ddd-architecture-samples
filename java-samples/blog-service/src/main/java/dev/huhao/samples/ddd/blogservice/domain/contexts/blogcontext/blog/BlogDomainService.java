package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.common.EntityNotFoundException;
import dev.huhao.samples.ddd.blogservice.domain.concepts.DomainService;

import java.util.UUID;

public class BlogDomainService implements DomainService {

    private final BlogRepository blogRepository;

    public BlogDomainService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createDraft(String title, String body, UUID authorId) {
        Blog blog = new Blog(title, body, authorId);
        blogRepository.save(blog);
        return blog;
    }

    public Blog getBlog(UUID id) {
        return findBlogById(id);
    }

    public Blog saveDraft(UUID id, String title, String body) {
        Blog blog = findBlogById(id);
        blog.saveDraft(title, body);
        blogRepository.save(blog);
        return blog;
    }

    private Blog findBlogById(UUID id) {
        return blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Blog.class, id));
    }
}
