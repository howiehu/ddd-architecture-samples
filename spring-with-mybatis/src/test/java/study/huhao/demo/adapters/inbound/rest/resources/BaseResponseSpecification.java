package study.huhao.demo.adapters.inbound.rest.resources;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.*;

public class BaseResponseSpecification {

    public static ResponseSpecification OK_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(OK.value())
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification CREATED_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(CREATED.value())
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification NO_CONTENT_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(NO_CONTENT.value())
            .build();

    public static ResponseSpecification NOT_FOUND_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(NOT_FOUND.value())
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification CONFLICT_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(CONFLICT.value())
            .expectContentType(JSON)
            .build();
}
