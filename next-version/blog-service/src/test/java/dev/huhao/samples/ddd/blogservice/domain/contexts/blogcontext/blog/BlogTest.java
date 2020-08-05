package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.exceptions.NoNeedToPublishException;
import org.junit.jupiter.api.BeforeEach;
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
            assertThat(blog.getPublished()).isNull();
        }

        @Test
        void should_throw_IllegalArgumentException_when_not_have_authorId() {
            assertThatThrownBy(() -> new Blog("Hello", "A nice day...", null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the blog must have author");
        }

        @Test
        void should_throw_IllegalArgumentException_when_title_is_blank() {
            assertThatThrownBy(() -> new Blog(" ", " ", UUID.randomUUID()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be blank");
        }
    }

    @Nested
    class updateDraft {

        private Blog blog;

        @BeforeEach
        void setUp() {
            blog = new Blog("Hello", "A nice day...", UUID.randomUUID());
        }

        @Test
        void should_update_correctly() throws InterruptedException {
            Draft oldDraft = blog.getDraft();
            Thread.sleep(1);// 由于程序运行速度太快，savedAt更改前后时间没有区别，所以需要刻意等1毫秒。

            blog.updateDraft("Hi", "Great!");

            assertThat(blog.getDraft()).isNotSameAs(oldDraft); // 值对象不可变，所以只有被替换，不能被修改。
            assertThat(blog.getDraft().getTitle()).isEqualTo("Hi");
            assertThat(blog.getDraft().getBody()).isEqualTo("Great!");
            assertThat(blog.getDraft().getSavedAt()).isAfter(oldDraft.getSavedAt());
        }

        @Test
        void should_throw_IllegalArgumentException_when_title_is_blank() {
            assertThatThrownBy(() -> blog.updateDraft(" ", "Great!"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the title cannot be blank");
        }

        @Test
        void should_throw_IllegalArgumentException_when_body_is_blank() {
            assertThatThrownBy(() -> blog.updateDraft("Hi", " "))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the body cannot be blank");
        }
    }

    @Nested
    class publish {

        @Test
        void should_publish_correctly() {
            Blog blog = new Blog("Hello", "A nice day...", UUID.randomUUID());

            blog.publish();

            assertThat(blog.getPublished()).isNotNull();
            assertThat(blog.getPublished().getTitle()).isEqualTo("Hello");
            assertThat(blog.getPublished().getBody()).isEqualTo("A nice day...");
            assertThat(blog.getPublished().getPublishedAt()).isNotNull();
            assertThat(blog.getPublished().getUpdatedAt()).isEqualTo(blog.getPublished().getPublishedAt());
        }

        @Test
        void should_update_updatedAt_when_publish_again() throws InterruptedException {
            Blog blog = new Blog("Hello", "A nice day...", UUID.randomUUID());

            blog.publish();
            PublishedBlog oldPublished = blog.getPublished();
            blog.updateDraft("Hi", "Great!");
            Thread.sleep(1);

            blog.publish();

            assertThat(blog.getPublished()).isNotSameAs(oldPublished);
            assertThat(blog.getPublished().getPublishedAt()).isEqualTo(oldPublished.getPublishedAt());
            assertThat(blog.getPublished().getUpdatedAt()).isAfter(oldPublished.getUpdatedAt());
        }

        @Test
        void should_throw_IllegalArgumentException_when_body_is_blank() {
            Blog blog = new Blog("Hello", " ", UUID.randomUUID());

            assertThatThrownBy(() -> blog.publish())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("the body cannot be blank");
        }

        @Test
        void should_throw_NoNeedToPublishException_when_draft_not_change() {
            Blog blog = new Blog("Hello", "A nice day...", UUID.randomUUID());
            blog.publish();

            assertThatThrownBy(() -> blog.publish())
                    .isInstanceOf(NoNeedToPublishException.class)
                    .hasMessage("the blog has not changed, no need to publish.");
        }
    }
}
