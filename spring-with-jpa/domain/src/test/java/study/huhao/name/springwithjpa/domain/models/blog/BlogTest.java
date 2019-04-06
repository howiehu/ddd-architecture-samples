package study.huhao.name.springwithjpa.domain.models.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.AuthorIsNullException;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.NoNeedToPublishException;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.TitleHasNoContentException;
import study.huhao.name.springwithjpa.domain.models.user.UserId;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogTest {

    @Nested
    class constructor {

        @Test
        void should_initialize_correctly() {
            var author = UserId.of(UUID.randomUUID().toString());

            var blog = new Blog("Test Blog", "Something...", author);

            assertThat(blog.getId()).isNotNull();
            assertThat(blog.getTitle()).isNull();
            assertThat(blog.getBody()).isNull();
            assertThat(blog.getAuthor()).isEqualTo(author);
            assertThat(blog.getStatus()).isEqualTo(Blog.PublishStatus.Draft);
            assertThat(blog.getCreatedAt()).isNotNull();
            assertThat(blog.getPublishedAt()).isNull();
            assertThat(blog.getLastModifiedAt()).isNull();

            var draft = blog.getDraft();
            assertThat(draft).isNotNull();
            assertThat(draft.getTitle()).isEqualTo("Test Blog");
            assertThat(draft.getBody()).isEqualTo("Something...");
            assertThat(draft.getSavedAt()).isEqualTo(blog.getCreatedAt());
        }

        @Test
        void should_throw_TitleHasNoContentException_when_title_is_null_or_no_content() {
            assertThatThrownBy(() -> new Blog(null, "Something...", UserId.of(UUID.randomUUID().toString())))
                    .isInstanceOf(TitleHasNoContentException.class)
                    .hasMessage("the draftTitle cannot be null or no content");
            assertThatThrownBy(() -> new Blog("   ", "Something...", UserId.of(UUID.randomUUID().toString())))
                    .isInstanceOf(TitleHasNoContentException.class)
                    .hasMessage("the draftTitle cannot be null or no content");
        }

        @Test
        void should_throw_AuthorIsNullException_when_author_is_null() {
            assertThatThrownBy(() -> new Blog("Test Blog", "Something...", null))
                    .isInstanceOf(AuthorIsNullException.class)
                    .hasMessage("the author cannot be null");
        }
    }

    @Nested
    class save {

        private Blog blog;
        private Instant pastSavedAt;

        @BeforeEach
        void setUp() {
            blog = new Blog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));
            pastSavedAt = blog.getDraft().getSavedAt();
        }

        @Test
        void should_save_correctly() {
            blog.save("Updated Title", "Updated...");

            assertSaveSuccess();
        }

        @Test
        void can_save_after_published() {
            blog.publish();

            blog.save("Updated Title", "Updated...");

            assertSaveSuccess();
            assertThat(blog.getStatus()).isEqualTo(Blog.PublishStatus.Published);
        }

        @Test
        void should_throw_TitleHasNoContentException_when_title_is_null_or_no_content() {
            assertThatThrownBy(() -> blog.save(null, "Updated..."))
                    .isInstanceOf(TitleHasNoContentException.class)
                    .hasMessage("the draftTitle cannot be null or no content");
            assertThatThrownBy(() -> blog.save("   ", "Updated..."))
                    .isInstanceOf(TitleHasNoContentException.class)
                    .hasMessage("the draftTitle cannot be null or no content");
        }

        private void assertSaveSuccess() {
            var draft = blog.getDraft();
            assertThat(draft).isNotNull();
            assertThat(draft.getTitle()).isEqualTo("Updated Title");
            assertThat(draft.getBody()).isEqualTo("Updated...");
            assertThat(draft.getSavedAt()).isAfter(pastSavedAt);
        }
    }

    @Nested
    class publish {

        private Blog blog;
        private BlogId pastId;
        private UserId pastAuthor;
        private Instant pastCreatedAt;

        @BeforeEach
        void setUp() {
            blog = new Blog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));
            pastId = blog.getId();
            pastAuthor = blog.getAuthor();
            pastCreatedAt = blog.getCreatedAt();
        }

        @Test
        void should_publish_correctly_when_first_publish() {
            blog.publish();

            assertUnchangedContent();
            assertThat(blog.getTitle()).isEqualTo("Test Blog");
            assertThat(blog.getBody()).isEqualTo("Something...");
            assertThat(blog.getPublishedAt())
                    .isNotNull()
                    .isAfter(blog.getCreatedAt());
            assertThat(blog.getLastModifiedAt()).isNull();
            assertThat(blog.getDraft()).isNull();
        }

        @Test
        void should_publish_correctly_when_follow_up_publish() {
            blog.publish();
            var pastPublishAt = blog.getPublishedAt();
            blog.save("Updated Blog", "Updated...");

            blog.publish();

            assertUnchangedContent();
            assertThat(blog.getTitle()).isEqualTo("Updated Blog");
            assertThat(blog.getBody()).isEqualTo("Updated...");
            assertThat(blog.getPublishedAt()).isEqualTo(pastPublishAt);
            assertThat(blog.getLastModifiedAt())
                    .isNotNull()
                    .isAfter(blog.getPublishedAt());
            assertThat(blog.getDraft()).isNull();
        }

        @Test
        void should_throw_NoNeedToPublishException_when_no_draft() {
            blog.publish();

            assertThatThrownBy(blog::publish)
                    .isInstanceOf(NoNeedToPublishException.class)
                    .hasMessage("no need to publish");
        }

        @Test
        void should_throw_NoNeedToPublishException_when_no_change() {
            blog.publish();
            blog.save(blog.getTitle(), blog.getBody());

            assertThatThrownBy(blog::publish)
                    .isInstanceOf(NoNeedToPublishException.class)
                    .hasMessage("no need to publish");
        }

        private void assertUnchangedContent() {
            assertThat(blog.getId()).isEqualTo(pastId);
            assertThat(blog.getAuthor()).isEqualTo(pastAuthor);
            assertThat(blog.getStatus()).isEqualTo(Blog.PublishStatus.Published);
            assertThat(blog.getCreatedAt()).isEqualTo(pastCreatedAt);
        }
    }
}
