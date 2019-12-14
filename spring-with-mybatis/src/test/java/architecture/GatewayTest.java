package architecture;

import org.junit.jupiter.api.Test;
import study.huhao.demo.application.concepts.Gateway;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class GatewayTest extends ArchitectureTest {

    @Test
    void gateway_interfaces_should_be_named_ending_with_Gateway() {
        classes().that().implement(Gateway.class)
                .and().areInterfaces()
                .should().haveSimpleNameEndingWith("Gateway")
                .as("The gateway interfaces should be named ending with 'Gateway'.")
                .check(classes);
    }

    @Test
    void gateway_implements_should_be_named_ending_with_GatewayImpl() {
        classes().that().implement(Gateway.class)
                .and().areNotInterfaces()
                .should().haveSimpleNameEndingWith("GatewayImpl")
                .as("The gateway implements should be named ending with 'GatewayImpl.")
                .check(classes);
    }

    @Test
    void gateway_should_implement_Gateway() {
        classes()
                .that().haveSimpleNameEndingWith("Gateway")
                .and().areNotInterfaces()
                .should().implement(Gateway.class)
                .as("The gateway implements should implement 'Gateway' interface.")
                .check(classes);
    }

    @Test
    void gateway_interfaces_should_place_under_application_gateway_package() {
        classes()
                .that().implement(Gateway.class)
                .and().areInterfaces()
                .should().resideInAPackage("..application.gateway..")
                .as("The gateway should place under application.gateway package.")
                .check(classes);
    }

    @Test
    void gateway_implements_should_place_under_adapters_outbound_gateway_package() {
        classes()
                .that().implement(Gateway.class)
                .and().areNotInterfaces()
                .should().resideInAPackage("..adapters.outbound.gateway..")
                .as("The gateway should place under adapters.outbound.gateway package.")
                .check(classes);
    }
}
