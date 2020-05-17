package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.blog;

import dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence.MapperIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BlogMapperTest extends MapperIntegrationTestBase {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void insert() {
        BlogPO newBlog = insertBlog();

        Optional<BlogPO> result = blogMapper.findById(newBlog.getId());

        assertThat(result).hasValueSatisfying(b -> assertThat(b).isEqualToComparingFieldByField(newBlog));
    }

    @Test
    void update() {
        BlogPO newBlog = insertBlog();

        BlogPO updatedBlog = new BlogPO(
                newBlog.getId(),
                newBlog.getAuthorId(),
                newBlog.getCreatedAt(),
                "Hi",
                "Great!",
                Instant.now(),
                "Wow",
                "Sad",
                Instant.now(),
                Instant.now()
        );

        blogMapper.update(updatedBlog);

        Optional<BlogPO> result = blogMapper.findById(newBlog.getId());

        assertThat(result).hasValueSatisfying(b -> assertThat(b).isEqualToComparingFieldByField(updatedBlog));
    }

    private BlogPO insertBlog() {
        BlogPO newBlog = new BlogPO(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Instant.now(),
                "Blog",
                "Something...",
                Instant.now(),
                null,
                null,
                null,
                null
        );

        blogMapper.insert(newBlog);
        return newBlog;
    }
}
