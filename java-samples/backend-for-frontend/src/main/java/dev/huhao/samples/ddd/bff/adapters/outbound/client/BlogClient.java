package dev.huhao.samples.ddd.bff.adapters.outbound.client;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.BlogServiceGrpc;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.GetDraftRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class BlogClient {

    @GrpcClient("blog-service")
    private BlogServiceGrpc.BlogServiceBlockingStub blogServiceStub;

    public DraftDto receiveDraft(String blogId) {
        GetDraftRequest request = GetDraftRequest.newBuilder().setBlogId(blogId).build();

        return blogServiceStub.getDraft(request);
    }
}
