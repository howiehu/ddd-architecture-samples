package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.GrpcServiceIntegrationTestBase;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlogGrpcServiceTest extends GrpcServiceIntegrationTestBase {
    @GrpcClient("inProcess")
    private BlogServiceGrpc.BlogServiceBlockingStub blogGrpcService;

    @Nested
    class createDraft {

        @Test
        @DirtiesContext
        void should_return_created_dto() {
            String authorId = UUID.randomUUID().toString();

            DraftDto result = createDraft("Hello", "A Nice Day...", authorId);

            assertThat(result.getBlogId()).isNotNull();
            assertThat(result.getTitle()).isEqualTo("Hello");
            assertThat(result.getBody()).isEqualTo("A Nice Day...");
            assertThat(result.getAuthorId()).isEqualTo(authorId);
            assertThat(result.getCreatedAt()).isNotBlank();
            assertThat(result.getSavedAt()).isEqualTo(result.getCreatedAt());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_not_have_authorId() {
            CreateDraftRequest request = buildCreateDraftRequest("Hello", "A Nice Day...", " ");

            assertThatThrownBy(() -> blogGrpcService.createDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the blog must have author")
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_title_is_blank() {
            CreateDraftRequest request =
                    buildCreateDraftRequest(" ", "A Nice Day...", UUID.randomUUID().toString());

            assertThatThrownBy(() -> blogGrpcService.createDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the title cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class getDraft {

        @Test
        void should_return_correct_draft() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            GetDraftRequest request = GetDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .build();

            DraftDto result = blogGrpcService.getDraft(request);

            assertThat(result.getBlogId()).isEqualTo(request.getBlogId());
        }

        @Test
        void should_thrown_NOT_FOUND_error_when_blog_not_found() {
            String blogId = UUID.randomUUID().toString();
            GetDraftRequest request = GetDraftRequest.newBuilder()
                    .setBlogId(blogId)
                    .build();

            assertThatThrownBy(() -> blogGrpcService.getDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class updateDraft {

        @Test
        void should_return_updated_draft() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody("Great!")
                    .build();

            DraftDto result = blogGrpcService.updateDraft(request);

            assertThat(result.getTitle()).isEqualTo("Hi");
            assertThat(result.getBody()).isEqualTo("Great!");
            assertThat(Instant.parse(result.getSavedAt())).isAfter(Instant.parse(createdDraft.getSavedAt()));
        }

        @Test
        void should_thrown_NOT_FOUND_error_when_blog_not_found() {
            String blogId = UUID.randomUUID().toString();
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(blogId)
                    .setTitle("Hi")
                    .setBody("Great!")
                    .build();

            assertThatThrownBy(() -> blogGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_title_is_blank() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle(" ")
                    .setBody("Great!")
                    .build();

            assertThatThrownBy(() -> blogGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the title cannot be blank")
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_body_is_blank() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody(" ")
                    .build();

            assertThatThrownBy(() -> blogGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the body cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class publishBlog {

        @Test
        void should_return_published_blog() {
            String authorId = UUID.randomUUID().toString();
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", authorId);
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();

            PublishedBlogDto result = blogGrpcService.publishBlog(request);

            assertThat(result.getBlogId()).isEqualTo(createdDraft.getBlogId());
            assertThat(result.getTitle()).isEqualTo("Hello");
            assertThat(result.getBody()).isEqualTo("A Nice Day...");
            assertThat(result.getAuthorId()).isEqualTo(authorId);
            assertThat(Instant.parse(result.getPublishedAt()))
                    .isAfter(Instant.parse(createdDraft.getSavedAt()));
            assertThat(result.getUpdatedAt()).isEqualTo(result.getPublishedAt());
        }

        @Test
        void should_update_updateAt_when_publish_again() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();
            PublishedBlogDto previousResult = blogGrpcService.publishBlog(request);
            SaveDraftRequest saveDraftRequest = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody("Great!")
                    .build();
            blogGrpcService.updateDraft(saveDraftRequest);

            PublishedBlogDto result = blogGrpcService.publishBlog(request);

            assertThat(result.getPublishedAt()).isEqualTo(previousResult.getPublishedAt());
            assertThat(Instant.parse(result.getUpdatedAt())).isAfter(Instant.parse(previousResult.getUpdatedAt()));
        }

        @Test
        void should_thrown_ALREADY_EXISTS_error_when_no_need_to_publish() {
            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();
            blogGrpcService.publishBlog(request);

            assertThatThrownBy(() -> blogGrpcService.publishBlog(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.ALREADY_EXISTS.withDescription("the blog has not changed, no need to publish.")
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_NOT_FOUND_error_when_blog_not_found() {
            String blogId = UUID.randomUUID().toString();
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(blogId).build();

            assertThatThrownBy(() -> blogGrpcService.publishBlog(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_body_is_blank() {
            DraftDto createdDraft = createDraft("Hello", " ", UUID.randomUUID().toString());
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();

            assertThatThrownBy(() -> blogGrpcService.publishBlog(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the body cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

    private CreateDraftRequest buildCreateDraftRequest(String title, String body, String authorId) {
        return CreateDraftRequest.newBuilder()
                .setTitle(title)
                .setBody(body)
                .setAuthorId(authorId)
                .build();
    }

    private DraftDto createDraft(String title, String body, String authorId) {
        CreateDraftRequest request = buildCreateDraftRequest(title, body, authorId);
        return blogGrpcService.createDraft(request);
    }
}
