package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftServiceGrpc;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditBlogUseCase;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GrpcService
public class DraftGrpcService extends DraftServiceGrpc.DraftServiceImplBase {

    private final EditBlogUseCase editBlogUseCase;

    @Autowired
    public DraftGrpcService(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @Override
    public void createDraft(CreateDraftRequest request, StreamObserver<DraftDto> responseObserver) {
        Blog blog = editBlogUseCase
                .createDraft(request.getTitle(), request.getBody(), UUID.fromString(request.getAuthorId()));

        DraftDto draftDto = DraftDto.newBuilder()
                .setBlogId(blog.getBlogId().toString())
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
