package study.huhao.demo.adapters.inbound.rest.resources;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class BaseResponseSpecification {

    public static ResponseSpecification OK_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification CREATED_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification NO_CONTENT_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification NOT_FOUND_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification CONFLICT_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(409)
            .expectContentType(JSON)
            .build();
}
