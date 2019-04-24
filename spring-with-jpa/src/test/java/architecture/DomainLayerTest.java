package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.domain.core.*;
import study.huhao.demo.domain.core.excpetions.DomainException;

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
        void domain_layer_implement_and_extend_rule() {
            classes()
                    .that().resideInAPackage("..domain.models..")

                    // these lines use to ignore lombok's @SuperBuilder generated code
                    .and().haveSimpleNameNotEndingWith("CriteriaBuilderImpl")
                    .and().haveSimpleNameNotEndingWith("CriteriaBuilder")

                    .should().implement(Entity.class)
                    .orShould().implement(AggregateRoot.class)
                    .orShould().implement(ValueObject.class)
                    .orShould().implement(EntityId.class)
                    .orShould().implement(ReadModel.class)
                    .orShould().implement(WriteModel.class) // need to be verified
                    .orShould().implement(DomainService.class)
                    .orShould().implement(Policy.class)
                    .orShould().beAssignableTo(Repository.class)
                    .orShould().beAssignableTo(DomainException.class)
                    .orShould().beAssignableTo(Criteria.class)
                    .as("The models in the domain should implement or extend one of the interfaces / classes in " +
                            "Entity, AggregateRoot, ValueObject, EntityId, ReadModel, WriteModel, " +
                            "DomainService, Policy, Repository, DomainException, Criteria.")
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
        void domain_services_can_only_be_access_by_policies_and_application_services() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(DomainService.class)
                    .should().onlyBeAccessed()
                    .byAnyPackage("..domain.polices..", "..application.services..")
                    .as("The domain services can only be access by policies and application services")
                    .because("the polices and application is the only entrance of domain.")
                    .check(classes);
        }
    }

    @Nested
    class policy {

        @Test
        void policies_should_be_named_ending_with_Policy() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(Policy.class)
                    .should().haveSimpleNameEndingWith("Policy")
                    .as("The policies should be named ending with 'Policy'.")
                    .check(classes);
        }

        @Test
        void policies_should_implement_Policy() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Policy")
                    .and().areNotInterfaces()
                    .should().implement(Policy.class)
                    .as("The policies should implement 'Policy' interface.")
                    .check(classes);
        }

        @Test
        void policies_should_in_the_domain_policies_packages() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(Policy.class)
                    .should().resideInAPackage("..domain.policies..")
                    .as("The policies should in the ..domain.policies.. package.")
                    .check(classes);
        }

        @Test
        void policies_can_only_be_access_by_application_services() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(Policy.class)
                    .should().onlyBeAccessed().byAnyPackage("..application.services..")
                    .as("The policies can only be access by application services")
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
    }

    @Nested
    class criteria {

        @Test
        void criteria_should_be_named_ending_with_Criteria() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAssignableTo(Criteria.class)
                    .should().haveSimpleNameEndingWith("Criteria")
                    .as("The criteria should be named ending with 'Criteria'.")
                    .check(classes);
        }

        @Test
        void criteria_should_extend_Criteria() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Criteria")
                    .should().beAssignableTo(Criteria.class)
                    .as("The criteria should extend Criteria.")
                    .check(classes);
        }
    }
}
