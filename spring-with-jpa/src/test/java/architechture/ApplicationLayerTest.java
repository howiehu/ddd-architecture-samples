package architechture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ApplicationLayerTest {
    private JavaClasses classes;

    @BeforeEach
    void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("study.huhao.demo");
    }

    @Nested
    class all {
        @Test
        void application_depend_on_rule() {
            classes()
                    .that().resideInAPackage("..application..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..application..", "..domain..")
                    .as("The application layer should only depend on the classes in the package of " +
                            "java, application and domain.")
                    .check(classes);
        }
    }

    @Nested
    class service {

        @Test
        void services_should_reside_in_services_package() {
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().resideInAPackage("..application.services..")
                    .as("The services should reside in ..application.services.. package.")
                    .check(classes);
        }

        @Test
        void services_should_be_annotated_with_Transactional() {
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().beAnnotatedWith(Transactional.class)
                    .as("The services should be annotated with 'Transactional'")
                    .because("the transactional should control at the entrance of the application layer")
                    .check(classes);
        }

        @Test
        void services_should_be_annotated_with_Service() {
            classes()
                    .that().resideInAPackage("..application..")
                    .and().haveSimpleNameEndingWith("Service")
                    .should().beAnnotatedWith(Service.class)
                    .as("The services should be annotated with 'Service'.")
                    .check(classes);
        }

        @Test
        void services_should_be_named_ending_with_Service() {
            classes()
                    .that().resideInAPackage("..application..")
                    .and().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .as("The services should be named ending with 'Service'.")
                    .check(classes);
        }
    }
}
