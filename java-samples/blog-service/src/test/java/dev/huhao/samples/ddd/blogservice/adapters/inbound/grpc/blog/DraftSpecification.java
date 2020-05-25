package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import dev.huhao.samples.ddd.protobuf.blog.Draft;
import dev.huhao.samples.ddd.protobuf.blog.DraftServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class DraftSpecification {

    @GrpcClient("inProcess")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

    public Draft.CreateDraftRequest buildCreateDraftRequest(String title, String body, String authorId) {
        return Draft.CreateDraftRequest.newBuilder()
                .setTitle(title)
                .setBody(body)
                .setAuthorId(authorId)
                .build();
    }

    public Draft.DraftDto createDraft(String title, String body, String authorId) {
        Draft.CreateDraftRequest request = buildCreateDraftRequest(title, body, authorId);
        return draftGrpcService.createDraft(request);
    }
}
