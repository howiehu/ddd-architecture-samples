package study.huhao.demo.architechture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "study.huhao.demo.domain")
class DomainArchitectureTest {
    @Test
    void should_not_depend_on_other_layer() {
        noClasses().should().dependOnClassesThat().resideInAnyPackage(
                "..adapters..",
                "..application..",
                "..infrastructure.."
        );
    }

    @Test
    void should_only_depend_on_lombok() {
        classes().should().onlyDependOnClassesThat().resideInAnyPackage("lombok");
    }
}
