package study.huhao.name.springwithjpa.domain.models.blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.AuthorIsNullException;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.TitleHasNoContentException;
import study.huhao.name.springwithjpa.domain.models.user.UserId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlogTest {

    @Nested
    class constructor {

        @Test
        void should_initialize_correctly() {
            var author = new UserId();

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
            assertThat(draft.getBlogId()).isEqualTo(blog.getId());
            assertThat(draft.getTitle()).isEqualTo("Test Blog");
            assertThat(draft.getBody()).isEqualTo("Something...");
            assertThat(draft.getSavedAt()).isEqualTo(blog.getCreatedAt());
        }

        @Test
        void should_throw_TitleHasNoContentException_when_title_is_null_or_no_content() {
            assertThatThrownBy(() -> new Blog(null, "Something...", new UserId()))
                    .isInstanceOf(TitleHasNoContentException.class)
                    .hasMessage("the title cannot be null or no content");
        }

        @Test
        void should_throw_AuthorIsNullException_when_author_is_null() {
            assertThatThrownBy(() -> new Blog("Test Blog", "Something...", null))
                    .isInstanceOf(AuthorIsNullException.class)
                    .hasMessage("the author cannot be null");
        }
    }
}
