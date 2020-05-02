package dev.huhao.samples.ddd.blogservice.adapters.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adapters.grpc.GrpcServiceIntegrationTestBase;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DraftGrpcServiceTest extends GrpcServiceIntegrationTestBase {
    @GrpcClient("inProcess")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

    @Test
    @DirtiesContext
    void create_draft_should_return_created_dto() {
        // Given
        CreateDraftRequest request = CreateDraftRequest.newBuilder()
                .setTitle("Hello")
                .setBody("A Nice Day...")
                .setAuthorId(UUID.randomUUID().toString())
                .build();

        // When
        DraftDto draftDto = draftGrpcService.createDraft(request);

        // Then
        assertThat(draftDto.getBlogId()).isNotNull();
        assertThat(draftDto.getTitle()).isEqualTo(request.getTitle());
        assertThat(draftDto.getBody()).isEqualTo(request.getBody());
        assertThat(draftDto.getAuthorId()).isEqualTo(request.getAuthorId());
        assertThat(draftDto.getCreatedAt()).isNotBlank();
        assertThat(draftDto.getSavedAt()).isBlank();
    }
}
