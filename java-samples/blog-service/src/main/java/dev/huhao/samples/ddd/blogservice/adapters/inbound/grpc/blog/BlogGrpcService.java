package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog;

import com.google.protobuf.Empty;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.*;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditBlogUseCase;
import dev.huhao.samples.ddd.blogservice.application.usecase.QueryDraftUseCase;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class BlogGrpcService extends BlogServiceGrpc.BlogServiceImplBase {

    private final EditBlogUseCase editBlogUseCase;
    private final QueryDraftUseCase queryDraftUseCase;

    public BlogGrpcService(EditBlogUseCase editBlogUseCase, QueryDraftUseCase queryDraftUseCase) {
        this.editBlogUseCase = editBlogUseCase;
        this.queryDraftUseCase = queryDraftUseCase;
    }

    @Override
    public void createDraft(CreateDraftRequest request, StreamObserver<DraftDto> responseObserver) {
        if (request.getAuthorId() == null || request.getAuthorId().trim().isEmpty()) {
            throw new IllegalArgumentException("the blog must have author");
        }

        Blog blog = editBlogUseCase
                .createDraft(request.getTitle(), request.getBody(), UUID.fromString(request.getAuthorId()));

        responseObserver.onNext(buildDraftDto(blog));
        responseObserver.onCompleted();
    }

    @Override
    public void getDraft(GetDraftRequest request, StreamObserver<DraftDto> responseObserver) {
        Blog blog = queryDraftUseCase.getByBlogId(UUID.fromString(request.getBlogId()));
        responseObserver.onNext(buildDraftDto(blog));
        responseObserver.onCompleted();
    }

    @Override
    public void updateDraft(SaveDraftRequest request, StreamObserver<DraftDto> responseObserver) {
        Blog blog =
                editBlogUseCase.updateDraft(UUID.fromString(request.getBlogId()), request.getTitle(), request.getBody());
        responseObserver.onNext(buildDraftDto(blog));
        responseObserver.onCompleted();
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

    private DraftDto buildDraftDto(Blog blog) {
        return DraftDto.newBuilder()
                .setBlogId(blog.getId().toString())
                .setTitle(blog.getDraft().getTitle())
                .setBody(blog.getDraft().getBody())
                .setAuthorId(blog.getAuthorId().toString())
                .setCreatedAt(blog.getCreatedAt().toString())
                .setSavedAt(blog.getDraft().getSavedAt().toString())
                .build();
    }
}