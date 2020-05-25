package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import com.google.protobuf.Empty;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.GrpcServiceIntegrationTestBase;
import dev.huhao.samples.ddd.protobuf.blog.Blog.DeleteBlogRequest;
import dev.huhao.samples.ddd.protobuf.blog.Blog.PublishBlogRequest;
import dev.huhao.samples.ddd.protobuf.blog.Blog.PublishedBlogDto;
import dev.huhao.samples.ddd.protobuf.blog.BlogServiceGrpc;
import dev.huhao.samples.ddd.protobuf.blog.Draft.DraftDto;
import dev.huhao.samples.ddd.protobuf.blog.Draft.GetDraftRequest;
import dev.huhao.samples.ddd.protobuf.blog.Draft.SaveDraftRequest;
import dev.huhao.samples.ddd.protobuf.blog.DraftServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlogGrpcServiceTest extends GrpcServiceIntegrationTestBase {
    @GrpcClient("inProcess")
    private BlogServiceGrpc.BlogServiceBlockingStub blogGrpcService;

    @GrpcClient("inProcess")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

    @Autowired
    private DraftSpecification draftSpecification;

    @Nested
    class publishBlog {

        @Test
        void should_return_published_blog() {
            String authorId = UUID.randomUUID().toString();
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", authorId);
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
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();
            PublishedBlogDto previousResult = blogGrpcService.publishBlog(request);
            SaveDraftRequest saveDraftRequest = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody("Great!")
                    .build();
            draftGrpcService.updateDraft(saveDraftRequest);

            PublishedBlogDto result = blogGrpcService.publishBlog(request);

            assertThat(result.getPublishedAt()).isEqualTo(previousResult.getPublishedAt());
            assertThat(Instant.parse(result.getUpdatedAt())).isAfter(Instant.parse(previousResult.getUpdatedAt()));
        }

        @Test
        void should_thrown_ALREADY_EXISTS_error_when_no_need_to_publish() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
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
            DraftDto createdDraft = draftSpecification.createDraft("Hello", " ", UUID.randomUUID().toString());
            PublishBlogRequest request = PublishBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();

            assertThatThrownBy(() -> blogGrpcService.publishBlog(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the body cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class deleteBlog {

        @Test
        void should_delete_blog() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            DeleteBlogRequest deleteBlogRequest = DeleteBlogRequest.newBuilder().setBlogId(createdDraft.getBlogId()).build();

            Empty result = blogGrpcService.deleteBlog(deleteBlogRequest);
            assertThat(result).isNotNull();

            GetDraftRequest getDraftRequest = GetDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .build();

            assertThatThrownBy(() -> draftGrpcService.getDraft(getDraftRequest))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + createdDraft.getBlogId())
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_NOT_FOUND_error_when_blog_not_found() {
            String blogId = UUID.randomUUID().toString();
            DeleteBlogRequest request = DeleteBlogRequest.newBuilder().setBlogId(blogId).build();

            assertThatThrownBy(() -> blogGrpcService.deleteBlog(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }
    }
}
