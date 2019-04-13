package study.huhao.demo.architechture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import study.huhao.demo.domain.core.*;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "study.huhao.demo", importOptions = ImportOption.DoNotIncludeTests.class)
class DomainArchitectureTest {

    @ArchTest
    static final ArchRule domain_layer_depend_on_rule =
            classes()
                    .that().resideInAPackage("..domain")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..domain.core..");

    @ArchTest
    static final ArchRule domain_layer_implement_interface_rule =
            classes()
                    .that().resideInAPackage("..domain.models..")
                    .should().implement(Entity.class)
                    .orShould().implement(AggregateRoot.class)
                    .orShould().implement(ValueObject.class)
                    .orShould().implement(DomainService.class)
                    .orShould().beAssignableTo(Repository.class)
                    .orShould().implement(DomainException.class)
                    .as("The models in the domain should implement one of the markedness interfaces in " +
                            "Entity, AggregateRoot, ValueObject, DomainService, Repository, DomainException.");

    @ArchTest
    static final ArchRule domain_services_should_be_named_ending_with_DomainService =
            classes()
                    .that().implement(DomainService.class)
                    .should().haveSimpleNameEndingWith("DomainService")
                    .as("The domain services should be named ending with 'DomainService.'");

    @ArchTest
    static final ArchRule domain_services_can_only_be_access_by_application_services =
            classes()
                    .that().implement(DomainService.class)
                    .should().onlyBeAccessed().byAnyPackage("..application.services..")
                    .as("The domain services can only be access by application services")
                    .because("the application is the only entrance of domain.");
}
