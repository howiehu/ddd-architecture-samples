package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.domain.core.*;
import study.huhao.demo.domain.core.excpetions.DomainException;
import study.huhao.demo.domain.core.excpetions.EntityExistedException;
import study.huhao.demo.domain.core.excpetions.EntityNotFoundException;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class DomainLayerTest {

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
        void domain_layer_depend_on_rule() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..domain..")
                    .as("The domain layer should only depend on the classes in the package of " +
                            "java and domain.")
                    .check(classes);
        }

        @Test
        void domain_layer_implement_interface_rule() {
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .should().implement(Entity.class)
                    .orShould().implement(AggregateRoot.class)
                    .orShould().implement(ValueObject.class)
                    .orShould().implement(EntityId.class)
                    .orShould().implement(DomainService.class)
                    .orShould().beAssignableTo(Repository.class)
                    .orShould().implement(DomainException.class)
                    .as("The models in the domain should implement one of the markedness interfaces in " +
                            "Entity, AggregateRoot, ValueObject, EntityId, DomainService, Repository, DomainException.")
                    .check(classes);
        }
    }

    @Nested
    class domain_service {

        @Test
        void domain_services_should_be_named_ending_with_DomainService() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(DomainService.class)
                    .should().haveSimpleNameEndingWith("DomainService")
                    .as("The domain services should be named ending with 'DomainService'.")
                    .check(classes);
        }

        @Test
        void domain_services_should_implement_DomainService() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("DomainService")
                    .and().areNotInterfaces()
                    .should().implement(DomainService.class)
                    .as("The domain services should implement 'DomainService' interface.")
                    .check(classes);
        }

        @Test
        void domain_services_can_only_be_access_by_application_services() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(DomainService.class)
                    .should().onlyBeAccessed().byAnyPackage("..application.services..")
                    .as("The domain services can only be access by application services")
                    .because("the application is the only entrance of domain.")
                    .check(classes);
        }
    }

    @Nested
    class repository {

        @Test
        void repositories_should_be_named_ending_with_Repository() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAssignableTo(Repository.class)
                    .should().haveSimpleNameEndingWith("Repository")
                    .as("The repositories should be named ending with 'Repository'.")
                    .check(classes);
        }

        @Test
        void repositories_should_extend_Repository() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Repository")
                    .and().doNotHaveSimpleName("Repository")
                    .should().beAssignableTo(Repository.class)
                    .as("The repositories should extend 'Repository' interface.")
                    .check(classes);
        }
    }

    @Nested
    class domain_exception {

        @Test
        void domain_exceptions_should_be_named_ending_with_Exception() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAssignableTo(DomainException.class)
                    .should().haveSimpleNameEndingWith("Exception")
                    .as("The domain exceptions should be named ending with 'Exception'.")
                    .check(classes);
        }

        @Test
        void domain_exceptions_should_extend_DomainException() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Exception")
                    .should().beAssignableTo(DomainException.class)
                    .as("The domain exceptions should extend DomainException.")
                    .check(classes);
        }


        @Test
        void entity_not_found_exceptions_should_be_named_ending_with_NotFoundException() {
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .and().areAssignableTo(EntityNotFoundException.class)
                    .should().haveSimpleNameEndingWith("NotFoundException")
                    .as("The entity not found exceptions should be named ending with 'NotFoundException'.")
                    .check(classes);
        }

        @Test
        void entity_not_found_exceptions_should_extend_EntityNotFoundException() {
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .and().haveSimpleNameEndingWith("NotFoundException")
                    .should().beAssignableTo(EntityNotFoundException.class)
                    .as("The entity not found exceptions should extend EntityNotFoundException.")
                    .check(classes);
        }

        @Test
        void entity_existed_exceptions_should_be_named_ending_with_ExistedException() {
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .and().areAssignableTo(EntityExistedException.class)
                    .should().haveSimpleNameEndingWith("ExistedException")
                    .as("The entity existed exceptions should be named ending with 'ExistedException'.")
                    .check(classes);
        }

        @Test
        void entity_existed_exceptions_should_extend_EntityExistedException() {
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .and().haveSimpleNameEndingWith("ExistedException")
                    .should().beAssignableTo(EntityExistedException.class)
                    .as("The entity existed exceptions should extend EntityExistedException.")
                    .check(classes);
        }
    }
}
