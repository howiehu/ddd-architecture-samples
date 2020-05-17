package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftServiceGrpc;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.GetDraftRequest;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditDraftUseCase;
import dev.huhao.samples.ddd.blogservice.application.usecase.QueryDraftUseCase;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class DraftGrpcService extends DraftServiceGrpc.DraftServiceImplBase {

    private final EditDraftUseCase editDraftUseCase;
    private final QueryDraftUseCase queryDraftUseCase;

    public DraftGrpcService(EditDraftUseCase editDraftUseCase, QueryDraftUseCase queryDraftUseCase) {
        this.editDraftUseCase = editDraftUseCase;
        this.queryDraftUseCase = queryDraftUseCase;
    }

    @Override
    public void createDraft(CreateDraftRequest request, StreamObserver<DraftDto> responseObserver) {

        if (request.getAuthorId() == null || request.getAuthorId().isEmpty()) {
            throw new IllegalArgumentException("the blog must have author");
        }

        Blog blog = editDraftUseCase
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
