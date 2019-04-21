package study.huhao.demo.infrastructure.persistence.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.huhao.demo.domain.core.excpetions.EntityNotFoundException;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogDomainService;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;
import study.huhao.demo.infrastructure.persistence.RepositoryTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogRepositoryTest extends RepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    private BlogDomainService blogDomainService;

    @BeforeEach
    void setUp() {
        blogDomainService = new BlogDomainService(blogRepository);
    }

    @Test
    void findById() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        var foundBlog = blogDomainService.getBlog(blog.getId());

        assertThat(foundBlog.getId()).isEqualTo(blog.getId());
        assertThat(foundBlog.getTitle()).isEqualTo("Test Blog");
        assertThat(foundBlog.getBody()).isEqualTo("Something...");
    }

    @Test
    void save_updated_blog() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        blogDomainService.saveBlog(blog.getId(), "Updated Title", "Updated...");

        var foundBlog = blogDomainService.getBlog(blog.getId());
        assertThat(foundBlog.getId()).isEqualTo(blog.getId());
        assertThat(foundBlog.getTitle()).isEqualTo("Updated Title");
        assertThat(foundBlog.getBody()).isEqualTo("Updated...");
    }

    @Test
    void delete_blog() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        blogDomainService.deleteBlog(blog.getId());

        assertThatThrownBy(() -> blogDomainService.getBlog(blog.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void publish_blog() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        blogDomainService.publishBlog(blog.getId());

        var foundBlog = blogDomainService.getBlog(blog.getId());
        assertThat(foundBlog.getId()).isEqualTo(blog.getId());
        assertThat(foundBlog.getStatus()).isEqualTo(Blog.Status.Published);
        assertThat(foundBlog.getPublished()).isNotNull();
        assertThat(foundBlog.getPublished().getTitle()).isEqualTo("Test Blog");
        assertThat(foundBlog.getPublished().getBody()).isEqualTo("Something...");
        assertThat(foundBlog.getPublished().getPublishedAt()).isNotNull();
    }

    @Test
    void get_all_blog() {
        UserId authorId = UserId.valueOf(UUID.randomUUID().toString());
        for (int i = 0; i < 5; i++) {
            blogDomainService.createBlog("Test Blog " + i + 1, "Something...", authorId);
        }
        var criteria = BlogCriteria.builder().limit(3).offset(4).build();

        var pagedBlog = blogDomainService.getAllBlog(criteria);

        assertThat(pagedBlog.getResults()).hasSize(2);
        assertThat(pagedBlog.getLimit()).isEqualTo(3);
        assertThat(pagedBlog.getOffset()).isEqualTo(4);
        assertThat(pagedBlog.getTotal()).isEqualTo(5);
    }
}
