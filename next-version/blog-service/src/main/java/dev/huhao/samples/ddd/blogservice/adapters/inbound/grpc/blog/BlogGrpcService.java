package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import com.google.protobuf.Empty;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditBlogUseCase;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.protobuf.blog.Blog.DeleteBlogRequest;
import dev.huhao.samples.ddd.protobuf.blog.Blog.PublishBlogRequest;
import dev.huhao.samples.ddd.protobuf.blog.Blog.PublishedBlogDto;
import dev.huhao.samples.ddd.protobuf.blog.BlogServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class BlogGrpcService extends BlogServiceGrpc.BlogServiceImplBase {

    private final EditBlogUseCase editBlogUseCase;

    public BlogGrpcService(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @Override
    public void publishBlog(PublishBlogRequest request, StreamObserver<PublishedBlogDto> responseObserver) {
        Blog blog = editBlogUseCase.publishBlog(UUID.fromString(request.getBlogId()));
        PublishedBlogDto publishedBlogDto = PublishedBlogDto.newBuilder()
                .setBlogId(blog.getId().toString())
                .setAuthorId(blog.getAuthorId().toString())
                .setTitle(blog.getPublished().getTitle())
                .setBody(blog.getPublished().getBody())
                .setPublishedAt(blog.getPublished().getPublishedAt().toString())
                .setUpdatedAt(blog.getPublished().getUpdatedAt().toString())
                .build();
        responseObserver.onNext(publishedBlogDto);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBlog(DeleteBlogRequest request, StreamObserver<Empty> responseObserver) {
        editBlogUseCase.deleteBlog(UUID.fromString(request.getBlogId()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
