package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.GrpcServiceIntegrationTestBase;
import dev.huhao.samples.ddd.protobuf.blog.Draft.CreateDraftRequest;
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
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DraftGrpcServiceTest extends GrpcServiceIntegrationTestBase {
    @GrpcClient("inProcess")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

    @Autowired
    private DraftSpecification draftSpecification;

    @Nested
    class createDraft {

        @Test
        @DirtiesContext
        void should_return_created_dto() {
            String authorId = UUID.randomUUID().toString();

            DraftDto result = draftSpecification.createDraft("Hello", "A Nice Day...", authorId);

            assertThat(result.getBlogId()).isNotNull();
            assertThat(result.getTitle()).isEqualTo("Hello");
            assertThat(result.getBody()).isEqualTo("A Nice Day...");
            assertThat(result.getAuthorId()).isEqualTo(authorId);
            assertThat(result.getCreatedAt()).isNotBlank();
            assertThat(result.getSavedAt()).isEqualTo(result.getCreatedAt());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_not_have_authorId() {
            CreateDraftRequest request = draftSpecification.buildCreateDraftRequest("Hello", "A Nice Day...", " ");

            assertThatThrownBy(() -> draftGrpcService.createDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the blog must have author")
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_title_is_blank() {
            CreateDraftRequest request =
                    draftSpecification.buildCreateDraftRequest(" ", "A Nice Day...", UUID.randomUUID().toString());

            assertThatThrownBy(() -> draftGrpcService.createDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the title cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class getDraft {

        @Test
        void should_return_correct_draft() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            GetDraftRequest request = GetDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .build();

            DraftDto result = draftGrpcService.getDraft(request);

            assertThat(result.getBlogId()).isEqualTo(request.getBlogId());
        }

        @Test
        void should_thrown_NOT_FOUND_error_when_blog_not_found() {
            String blogId = UUID.randomUUID().toString();
            GetDraftRequest request = GetDraftRequest.newBuilder()
                    .setBlogId(blogId)
                    .build();

            assertThatThrownBy(() -> draftGrpcService.getDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class updateDraft {

        @Test
        void should_return_updated_draft() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody("Great!")
                    .build();

            DraftDto result = draftGrpcService.updateDraft(request);

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

            assertThatThrownBy(() -> draftGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.NOT_FOUND.withDescription("cannot find the blog with id " + blogId)
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_title_is_blank() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle(" ")
                    .setBody("Great!")
                    .build();

            assertThatThrownBy(() -> draftGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the title cannot be blank")
                            .asRuntimeException().getMessage());
        }

        @Test
        void should_thrown_INVALID_ARGUMENT_error_when_body_is_blank() {
            DraftDto createdDraft = draftSpecification.createDraft("Hello", "A Nice Day...", UUID.randomUUID().toString());
            SaveDraftRequest request = SaveDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .setTitle("Hi")
                    .setBody(" ")
                    .build();

            assertThatThrownBy(() -> draftGrpcService.updateDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the body cannot be blank")
                            .asRuntimeException().getMessage());
        }
    }

}
