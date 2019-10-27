package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.application.concepts.UseCase;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ApplicationLayerTest {
    private JavaClasses classes;

    @BeforeEach
    void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("study.huhao.demo");
    }

    @Nested
    class use_case {

        @Test
        void use_cases_should_be_named_ending_with_UseCase() {
            classes().that().resideInAPackage("..application..")
                    .and().implement(UseCase.class)
                    .should().haveSimpleNameEndingWith("UseCase")
                    .as("The use cases should be named ending with 'UseCase'.")
                    .check(classes);
        }

        @Test
        void use_cases_should_implement_UseCase() {
            classes()
                    .that().resideInAPackage("..application..")
                    .and().haveSimpleNameEndingWith("UseCase")
                    .and().areNotInterfaces()
                    .should().implement(UseCase.class)
                    .as("The use cases should implement 'UseCase' interface.")
                    .check(classes);
        }
    }
}
