package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.proto.DraftServiceGrpc;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditBlogUseCase;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class DraftGrpcService extends DraftServiceGrpc.DraftServiceImplBase {

    private final EditBlogUseCase editBlogUseCase;

    public DraftGrpcService(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @Override
    public void createDraft(CreateDraftRequest request, StreamObserver<DraftDto> responseObserver) {

        if (request.getAuthorId() == null || request.getAuthorId().isEmpty()) {
            responseObserver
                    .onError(Status.INVALID_ARGUMENT.withDescription("the blog must have author").asRuntimeException());
            return;
        }

        Blog blog = editBlogUseCase
                .createDraft(request.getTitle(), request.getBody(), UUID.fromString(request.getAuthorId()));

        DraftDto draftDto = DraftDto.newBuilder()
                .setBlogId(blog.getId().toString())
                .setTitle(blog.getTitle())
                .setBody(blog.getBody())
                .setAuthorId(blog.getAuthorId().toString())
                .setCreatedAt(blog.getCreatedAt().toString())
                .setSavedAt(blog.getSavedAt().toString())
                .build();

        responseObserver.onNext(draftDto);
        responseObserver.onCompleted();
    }
}
