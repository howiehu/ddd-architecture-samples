package architecture;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.inbound.rest.resources.RequestDto;
import study.huhao.demo.adapters.inbound.rest.resources.ResponseDto;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class RestLayerTest extends ArchitectureTest {

    @Nested
    class request_dto {

        @Test
        void request_dtos_should_be_named_ending_with_Request() {
            classes()
                    .that().resideInAPackage("..restapi..")
                    .and().implement(RequestDto.class)
                    .should().haveSimpleNameEndingWith("Request")
                    .as("The request DTOs should be named ending with 'Request'.")
                    .check(classes);
        }

        @Test
        void request_dtos_should_implement_RequestDto() {
            classes()
                    .that().resideInAPackage("..restapi..")
                    .and().haveSimpleNameEndingWith("Request")
                    .and().areNotInterfaces()
                    .should().implement(RequestDto.class)
                    .as("The request DTOs should implement 'RequestDto' interface.")
                    .check(classes);
        }

    }

    @Nested
    class response_dto {

        @Test
        void response_dtos_should_be_named_ending_with_Dto() {
            classes()
                    .that().resideInAPackage("..restapi..")
                    .and().implement(ResponseDto.class)
                    .should().haveSimpleNameEndingWith("Dto")
                    .as("The response DTOs should be named ending with 'Dto'.")
                    .check(classes);
        }

        @Test
        void response_dtos_should_implement_ResponseDto() {
            classes()
                    .that().resideInAPackage("..restapi..")
                    .and().haveSimpleNameEndingWith("Dto")
                    .and().areNotInterfaces()
                    .should().implement(ResponseDto.class)
                    .as("The response DTOs should implement 'ResponseDto' interface.")
                    .check(classes);
        }

    }
}
