package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayeredArchitectureTest {

    private JavaClasses classes;

    @BeforeEach
    void setUp() {
        classes = new ClassFileImporter()
                .importPackages("study.huhao.demo");
    }

    @Test
    void layer_dependencies_must_be_respected_include_the_tests() {
        layeredArchitecture()

                .layer("Interfaces").definedBy("study.huhao.demo.interfaces..")
                .layer("Infrastructure").definedBy("study.huhao.demo.infrastructure..")
                .layer("Application").definedBy("study.huhao.demo.application..")
                .layer("Domain").definedBy("study.huhao.demo.domain..")

                .whereLayer("Interfaces").mayNotBeAccessedByAnyLayer()
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Interfaces")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Interfaces", "Infrastructure", "Application")

                .as("The layer dependencies must be respected (include the tests)")
                .because("we must follow the DIP principle.")
                .check(classes);
    }
}
