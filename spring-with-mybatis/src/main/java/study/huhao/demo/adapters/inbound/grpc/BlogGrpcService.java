package study.huhao.demo.adapters.inbound.grpc;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import study.huhao.demo.adapters.grpc.BlogGrpc;
import study.huhao.demo.application.usecases.EditBlogUseCase;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;

import java.util.UUID;

import static study.huhao.demo.adapters.grpc.BlogOuterClass.BlogDto;
import static study.huhao.demo.adapters.grpc.BlogOuterClass.CreateBlogRequest;

@GRpcService
public class BlogGrpcService extends BlogGrpc.BlogImplBase {
    private final EditBlogUseCase editBlogUseCase;

    public BlogGrpcService(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @Override
    public void create(CreateBlogRequest request, StreamObserver<BlogDto> responseObserver) {
        Blog blog = editBlogUseCase.create(request.getTitle(), request.getBody(), UUID.fromString(request.getAuthorId()));
        final BlogDto blogDto = BlogDto.newBuilder()
                .setId(blog.getId().toString())
                .setTitle(blog.getTitle())
                .setBody(blog.getBody())
                .setAuthorId(blog.getAuthorId().toString())
                .setStatus(BlogDto.BlogStatus.valueOf(blog.getStatus().toString()))
                .setCreatedAt(blog.getCreatedAt().toString())
                .setSavedAt(blog.getSavedAt().toString())
                .build();

        responseObserver.onNext(blogDto);
        responseObserver.onCompleted();
    }
}
