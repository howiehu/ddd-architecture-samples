package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft;

import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.CreateDraftRequest;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftDto;
import dev.huhao.samples.ddd.blogservice.adatpers.inbound.grpc.draft.proto.DraftServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.UUID;

@GrpcService
public class DraftGrpcService extends DraftServiceGrpc.DraftServiceImplBase {

    @Override
    public void createDraft(CreateDraftRequest request, StreamObserver<DraftDto> responseObserver) {
        DraftDto draftDto = DraftDto.newBuilder()
                .setBlogId(UUID.randomUUID().toString())
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .setAuthorId(request.getAuthorId())
                .setCreatedAt(Instant.now().toString())
                .build();

        responseObserver.onNext(draftDto);
        responseObserver.onCompleted();
    }
}
