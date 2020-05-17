package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc;

import dev.huhao.samples.ddd.blogservice.domain.common.EntityExistedException;
import dev.huhao.samples.ddd.blogservice.domain.common.EntityNotFoundException;
import io.grpc.*;

public class ExceptionHandlerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (EntityNotFoundException e) {
                    call.close(Status.NOT_FOUND.withCause(e).withDescription(e.getMessage()), new Metadata());
                } catch (EntityExistedException e) {
                    call.close(Status.ALREADY_EXISTS.withCause(e).withDescription(e.getMessage()), new Metadata());
                } catch (IllegalArgumentException e) {
                    call.close(Status.INVALID_ARGUMENT.withCause(e).withDescription(e.getMessage()), new Metadata());
                }
            }
        };
    }
}
