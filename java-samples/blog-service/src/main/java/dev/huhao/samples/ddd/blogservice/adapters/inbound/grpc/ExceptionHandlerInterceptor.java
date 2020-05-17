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
                    handleCustomException(e, Status.NOT_FOUND);
                } catch (EntityExistedException e) {
                    handleCustomException(e, Status.ALREADY_EXISTS);
                } catch (IllegalArgumentException e) {
                    handleCustomException(e, Status.INVALID_ARGUMENT);
                }
            }

            private void handleCustomException(RuntimeException e, Status status) {
                call.close(status.withCause(e).withDescription(e.getMessage()), new Metadata());
            }
        };
    }
}
