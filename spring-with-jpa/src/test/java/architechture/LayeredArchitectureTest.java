package architechture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayeredArchitectureTest {

    private JavaClasses classes;

    @BeforeEach
    void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("study.huhao.demo");
    }

    @Test
    void layer_dependencies_are_respected() {
        layeredArchitecture()

                .layer("Adapters").definedBy("study.huhao.demo.adapters..")
                .layer("Application").definedBy("study.huhao.demo.application..")
                .layer("Infrastructure").definedBy("study.huhao.demo.infrastructure..")
                .layer("Domain").definedBy("study.huhao.demo.domain..")

                .whereLayer("Adapters").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Adapters")
                .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Application")

                .as("The layer dependencies must be respected.")
                .check(classes);
    }
}
