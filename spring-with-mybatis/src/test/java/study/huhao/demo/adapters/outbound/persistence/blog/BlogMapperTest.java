package study.huhao.demo.adapters.outbound.persistence.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.huhao.demo.adapters.outbound.persistence.MapperTest;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogCriteria;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BlogMapperTest extends MapperTest {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void insert() {
        BlogPO newBlog = insertBlog();

        Optional<BlogPO> result = blogMapper.findById(newBlog.getId());

        assertThat(result).hasValueSatisfying(b -> {
            assertThat(b).isEqualToIgnoringGivenFields(newBlog, "published");
            assertThat(b.getPublished()).isEqualToComparingFieldByField(newBlog.getPublished());
        });
    }

    @Test
    void update() {
        BlogPO newBlog = insertBlog();

        BlogPO updatedBlog = new BlogPO(
                newBlog.getId(),
                "Updated Blog",
                "Updated Something...",
                UUID.randomUUID().toString(),
                Blog.Status.Draft.toString(),
                Instant.now(),
                Instant.now(),
                new PublishedBlogPO(
                        "Updated Published Blog",
                        "Updated Published Something...",
                        Instant.now()
                )
        );

        blogMapper.update(updatedBlog);

        Optional<BlogPO> result = blogMapper.findById(newBlog.getId());

        assertThat(result).hasValueSatisfying(b -> {
            assertThat(b).isEqualToIgnoringGivenFields(updatedBlog, "published");
            assertThat(b.getPublished()).isEqualToComparingFieldByField(updatedBlog.getPublished());
        });
    }

    @Test
    void existsById() {
        BlogPO newBlog = insertBlog();

        boolean result = blogMapper.existsById(newBlog.getId());

        assertThat(result).isTrue();
    }

    @Test
    void deleteById() {
        BlogPO newBlog = insertBlog();

        blogMapper.deleteById(newBlog.getId());

        Optional<BlogPO> result = blogMapper.findById(newBlog.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void selectAllByCriteria() {
        for (int i = 0; i < 5; i++) {
            insertBlog();
        }

        BlogCriteria criteria = new BlogCriteria(3, 3);

        List<BlogPO> result = blogMapper.selectAllByCriteria(criteria);

        assertThat(result).hasSize(2);
    }

    @Test
    void countTotalByCriteria() {
        for (int i = 0; i < 5; i++) {
            insertBlog();
        }

        BlogCriteria criteria = new BlogCriteria(3, 3);

        long result = blogMapper.countTotalByCriteria(criteria);

        assertThat(result).isEqualTo(5);
    }

    private BlogPO insertBlog() {
        BlogPO newBlog = new BlogPO(
                UUID.randomUUID().toString(),
                "Blog",
                "Something...",
                UUID.randomUUID().toString(),
                Blog.Status.Published.toString(),
                Instant.now(),
                Instant.now(),
                new PublishedBlogPO(
                        "Published Blog",
                        "Published Something...",
                        Instant.now()
                )
        );

        blogMapper.insert(newBlog);
        return newBlog;
    }
}
