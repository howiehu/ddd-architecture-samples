package study.huhao.demo.domain.contexts.blogcontext.blog;

import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;
import study.huhao.demo.domain.core.concepts.Service;

import java.util.UUID;

public class BlogService implements Service {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog create(String title, String body, UUID authorId) {
        Blog blog = new Blog(title, body, authorId);
        blogRepository.save(blog);
        return blog;
    }

    public Blog get(UUID id) {
        return blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Blog.class, id));
    }

    public Blog getPublished(UUID id) {
        return blogRepository.findById(id)
                .filter(Blog::isPublished)
                .orElseGet(() -> {
                    throw new EntityNotFoundException("cannot find the published blog with id " + id);
                });
    }

    public void saveDraft(UUID id, String title, String body) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Blog.class, id));
        blog.saveDraft(title, body);
        blogRepository.save(blog);
    }

    public void delete(UUID id) {
        boolean existed = blogRepository.existsById(id);
        if (!existed) throw new EntityNotFoundException(Blog.class, id);
        blogRepository.deleteById(id);
    }

    public Blog publish(UUID id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Blog.class, id));
        blog.publish();
        blogRepository.save(blog);
        return blog;
    }

}
