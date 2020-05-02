package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BlogTest {

    @Nested
    class constructor {

        @Test
        void should_initialize_correctly() {
            UUID authorId = UUID.randomUUID();

            Blog blog = new Blog("Hello", "A nice day...", authorId);

            assertThat(blog.getId()).isNotNull();
            assertThat(blog.getTitle()).isEqualTo("Hello");
            assertThat(blog.getBody()).isEqualTo("A nice day...");
            assertThat(blog.getAuthorId()).isEqualTo(authorId);
            assertThat(blog.getCreatedAt()).isNotNull();
            assertThat(blog.getSavedAt()).isEqualTo(blog.getCreatedAt());
        }
    }
}
