package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogTest {

    @Nested
    class constructor {

        @Test
        void should_initialize_correctly() {
            UUID authorId = UUID.randomUUID();

            Blog blog = new Blog("Hello", "A nice day...", authorId);

            assertThat(blog.getId()).isNotNull();
            assertThat(blog.getDraft().getTitle()).isEqualTo("Hello");
            assertThat(blog.getDraft().getBody()).isEqualTo("A nice day...");
            assertThat(blog.getAuthorId()).isEqualTo(authorId);
            assertThat(blog.getCreatedAt()).isNotNull();
            assertThat(blog.getDraft().getSavedAt()).isEqualTo(blog.getCreatedAt());
        }

        @Test
        void should_throw_IllegalArgumentException_when_not_have_authorId() {
            assertThatThrownBy(() -> new Blog("Hello", "A nice day...", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the blog must have author");
        }
    }
}
