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
                    .orShould().implement(ReadModel.class)
                    .orShould().implement(Service.class)
                    .orShould().implement(Factory.class)
                    .orShould().beAssignableTo(Repository.class)
                    .orShould().beAssignableTo(DomainException.class)
                    .orShould().beAssignableTo(Criteria.class)
                    .as("The models in the domain should implement or extend one of the interfaces / classes in " +
                            "Entity, AggregateRoot, ValueObject, ReadModel, WriteModel, " +
                            "Service, Policy, Factory, Repository, DomainException, Criteria.")
                    .check(classes);
        }
    }

    @Nested
    class service {

        @Test
        void services_should_be_named_ending_with_Service() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .as("The domain services should be named ending with 'Service'.")
                    .check(classes);
        }

        @Test
        void services_should_implement_Service() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Service")
                    .and().areNotInterfaces()
                    .should().implement(Service.class)
                    .as("The domain services should implement 'Service' interface.")
                    .check(classes);
        }
    }

    @Nested
    class factory {

        @Test
        void factories_should_be_named_ending_with_Factory() {
            classes().that().resideInAPackage("..domain..")
                    .and().implement(Factory.class)
                    .should().haveSimpleNameEndingWith("Factory")
                    .as("The domain factories should be named ending with 'Factory'.")
                    .check(classes);
        }

        @Test
        void factories_should_implement_Factory() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Factory")
                    .and().areNotInterfaces()
                    .should().implement(Factory.class)
                    .as("The domain factories should implement 'Factory' interface.")
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
