package architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayeredArchitectureTest extends ArchitectureTest {

    @Test
    void layer_dependencies_must_be_respected_include_the_tests() {
        layeredArchitecture()

                .layer("Rest").definedBy("study.huhao.demo.adapters.inbound.rest..")
//                .layer("Rpc").definedBy("study.huhao.demo.adapters.inbound.rpc..")
                .layer("Gateway").definedBy("study.huhao.demo.adapters.outbound.gateway..")
                .layer("Persistence").definedBy("study.huhao.demo.adapters.outbound.persistence..")
                .layer("Application").definedBy("study.huhao.demo.application..")
                // 由于Domain层位于最内层，可以被所有其它层访问，所以在此不用显式声明和进行测试
                // .layer("Domain").definedBy("study.huhao.demo.domain..")

                .whereLayer("Rest").mayNotBeAccessedByAnyLayer()
//                .whereLayer("Rpc").mayNotBeAccessedByAnyLayer()
                .whereLayer("Gateway").mayNotBeAccessedByAnyLayer()
                .whereLayer("Persistence").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Rest", "Gateway")
                // 由于Domain层位于最内层，可以被所有其它层访问，所以在此不用显式声明和进行测试
                // .whereLayer("Domain").mayOnlyBeAccessedByLayers("Adapters", "Application")

                .as("The layer dependencies must be respected (include the tests)")
                .because("we must follow the DIP principle.")
                .check(classes);
    }
}
