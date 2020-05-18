package dev.huhao.samples.ddd.bff.adapters.outbound.gateway;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.BlogServiceGrpc;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.GetDraftRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class BlogGateway {

    @GrpcClient("blog-service")
    private BlogServiceGrpc.BlogServiceBlockingStub blogServiceStub;

    public DraftDto getDraft(String blogId) {
        GetDraftRequest request = GetDraftRequest.newBuilder().setBlogId(blogId).build();

        return blogServiceStub.getDraft(request);
    }
}
