package study.huhao.demo.architechture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "study.huhao.demo")
class LayeredArchitectureTest {
    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()

            .layer("Adapters").definedBy("study.huhao.demo.adapters...")
            .layer("Application").definedBy("study.huhao.demo.application...")
            .layer("Infrastructure").definedBy("study.huhao.demo.infrastructure...")
            .layer("Domain").definedBy("study.huhao.demo.domain...")

            .whereLayer("Adapters").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Adapters")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Application")

            .as("The layer dependencies must be respected.");

}
