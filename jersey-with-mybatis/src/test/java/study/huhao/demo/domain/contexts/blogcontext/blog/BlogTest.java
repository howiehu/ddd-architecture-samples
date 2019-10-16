package study.huhao.demo.domain.contexts.blogcontext.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.domain.contexts.blogcontext.blog.exceptions.NoNeedToPublishException;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogTest {

    @Nested
    class constructor {

        @Test
        void should_initialize_correctly() {
            var authorId = UUID.randomUUID();

            var blog = new Blog("Test Blog", "Something...", authorId);

            assertThat(blog.getId()).isNotNull();
            assertThat(blog.getTitle()).isEqualTo("Test Blog");
            assertThat(blog.getBody()).isEqualTo("Something...");
            assertThat(blog.getAuthorId()).isEqualTo(authorId);
            assertThat(blog.getStatus()).isEqualTo(Blog.Status.Draft);
            assertThat(blog.getCreatedAt()).isNotNull();
            assertThat(blog.getSavedAt()).isEqualTo(blog.getCreatedAt());
            assertThat(blog.getPublished()).isNull();
        }

        @Test
        void should_throw_IllegalArgumentException_when_title_is_null_or_no_content() {
            assertThatThrownBy(() -> new Blog(null, "Something...", UUID.randomUUID()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be null or no content");
            assertThatThrownBy(() -> new Blog("   ", "Something...", UUID.randomUUID()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be null or no content");
        }

        @Test
        void should_throw_IllegalArgumentException_when_author_is_null() {
            assertThatThrownBy(() -> new Blog("Test Blog", "Something...", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the author cannot be null");
        }
    }

    @Nested
    class save {

        private Blog blog;
        private Instant pastSavedAt;

        @BeforeEach
        void setUp() {
            blog = new Blog("Test Blog", "Something...", UUID.randomUUID());
            pastSavedAt = blog.getSavedAt();
        }

        @Test
        void should_save_correctly() throws InterruptedException {
            Thread.sleep(1);

            blog.save("Updated Title", "Updated...");

            assertThat(blog.getTitle()).isEqualTo("Updated Title");
            assertThat(blog.getBody()).isEqualTo("Updated...");
            assertThat(blog.getSavedAt()).isAfter(pastSavedAt);
        }

        @Test
        void should_throw_IllegalArgumentException_when_title_is_null_or_no_content() {
            assertThatThrownBy(() -> blog.save(null, "Updated..."))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be null or no content");
            assertThatThrownBy(() -> blog.save("   ", "Updated..."))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be null or no content");
        }

    }

    @Nested
    class publish {

        private Blog blog;

        @BeforeEach
        void setUp() {
            blog = new Blog("Test Blog", "Something...", UUID.randomUUID());
        }

        @Test
        void should_publish_correctly() {
            blog.publish();

            assertThat(blog.getStatus()).isEqualTo(Blog.Status.Published);
            var published = blog.getPublished();
            assertThat(published.getTitle()).isEqualTo("Test Blog");
            assertThat(published.getBody()).isEqualTo("Something...");
            assertThat(published.getPublishedAt()).isNotNull();
        }

        @Test
        void should_throw_NoNeedToPublishException_when_no_change() {
            blog.publish();
            blog.save(blog.getTitle(), blog.getBody());

            assertThatThrownBy(blog::publish)
                    .isInstanceOf(NoNeedToPublishException.class)
                    .hasMessage("no need to publish");
        }
    }
}
