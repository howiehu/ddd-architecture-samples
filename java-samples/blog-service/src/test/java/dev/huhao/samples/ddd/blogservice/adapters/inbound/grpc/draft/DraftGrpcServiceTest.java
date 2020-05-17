package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.GrpcServiceIntegrationTestBase;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftServiceGrpc;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.GetDraftRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DraftGrpcServiceTest extends GrpcServiceIntegrationTestBase {
    @GrpcClient("inProcess")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

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
        void should_return_error_when_not_have_authorId() {

            CreateDraftRequest request = buildCreateDraftRequest("Hello", "A Nice Day...", "");

            assertThatThrownBy(() -> draftGrpcService.createDraft(request))
                    .isInstanceOf(StatusRuntimeException.class)
                    .hasMessage(Status.INVALID_ARGUMENT.withDescription("the blog must have author")
                            .asRuntimeException().getMessage());
        }
    }

    @Nested
    class getDraft {

        @Test
        void should_return_correct_draft() {
            String authorId = UUID.randomUUID().toString();

            DraftDto createdDraft = createDraft("Hello", "A Nice Day...", authorId);

            GetDraftRequest request = GetDraftRequest.newBuilder()
                    .setBlogId(createdDraft.getBlogId())
                    .build();

            DraftDto result = draftGrpcService.getDraft(request);

            assertThat(result.getBlogId()).isEqualTo(request.getBlogId());
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
        return draftGrpcService.createDraft(request);
    }
}
