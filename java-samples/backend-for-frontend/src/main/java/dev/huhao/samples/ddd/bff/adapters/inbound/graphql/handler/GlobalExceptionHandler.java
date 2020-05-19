package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.handler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(StatusRuntimeException.class)
    public GraphQLError grpcStatusRuntimeExceptionHandler(StatusRuntimeException e) {
        return GraphqlErrorBuilder.newError().message(e.getMessage()).build();
    }
}
