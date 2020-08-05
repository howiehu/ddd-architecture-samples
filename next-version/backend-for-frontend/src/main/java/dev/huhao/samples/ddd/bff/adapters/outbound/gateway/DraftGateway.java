package dev.huhao.samples.ddd.bff.adapters.outbound.gateway;

import dev.huhao.samples.ddd.protobuf.blog.Draft.CreateDraftRequest;
import dev.huhao.samples.ddd.protobuf.blog.Draft.DraftDto;
import dev.huhao.samples.ddd.protobuf.blog.Draft.GetDraftRequest;
import dev.huhao.samples.ddd.protobuf.blog.DraftServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class DraftGateway {

    @GrpcClient("blog-service")
    private DraftServiceGrpc.DraftServiceBlockingStub draftGrpcService;

    public DraftDto getDraft(String blogId) {
        GetDraftRequest request = GetDraftRequest.newBuilder().setBlogId(blogId).build();
        return draftGrpcService.getDraft(request);
    }

    public DraftDto createDraft(String title, String body, String authorId) {
        CreateDraftRequest request = CreateDraftRequest.newBuilder()
                .setTitle(title)
                .setBody(body)
                .setAuthorId(authorId)
                .build();
        return draftGrpcService.createDraft(request);
    }
}
