package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import study.huhao.demo.domain.core.concepts.HumbleObject;
import study.huhao.demo.domain.core.concepts.Repository;
import study.huhao.demo.infrastructure.persistence.PersistenceObject;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.theClass;

class PersistenceLayerTest {
    private JavaClasses classes;

    @BeforeEach
    void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("study.huhao.demo");
    }

    @Nested
    class repository_impl {

        @Test
        void repository_impls_be_public() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().implement(Repository.class)
                    .should().bePublic()
                    .as("The repository impls should be package private.")
                    .check(classes);
        }

        @Test
        void repository_impls_be_annotated_with_Component() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().implement(Repository.class)
                    .should().beAnnotatedWith(Component.class)
                    .as("The repository impls should be annotated with 'Component'.")
                    .check(classes);
        }

        @Test
        void repository_impls_should_be_named_ending_with_RepositoryImpl() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().implement(Repository.class)
                    .should().haveSimpleNameEndingWith("RepositoryImpl")
                    .as("The repository impls should be named ending with 'RepositoryImpl'.")
                    .check(classes);
        }

        @Test
        void repository_impls_should_implement_Repository() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().haveSimpleNameEndingWith("RepositoryImpl")
                    .should().implement(Repository.class)
                    .as("The repository impls should implement 'Repository' interface.")
                    .check(classes);
        }
    }

    @Nested
    class mapper {

        @Test
        void mappers_be_interface() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().areAnnotatedWith(Mapper.class)
                    .should().beInterfaces()
                    .as("The mappers should be interface.")
                    .check(classes);
        }

        @Test
        void mappers_be_annotated_with_Mapper() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().haveSimpleNameEndingWith("Mapper")
                    .should().beAnnotatedWith(Mapper.class)
                    .as("The mappers should be annotated with MyBatis 'Repository'.")
                    .check(classes);
        }

        @Test
        void mappers_should_be_named_ending_with_Mapper() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().areAnnotatedWith(Mapper.class)
                    .should().haveSimpleNameEndingWith("Mapper")
                    .as("The mappers should be named ending with 'Mapper'.")
                    .check(classes);
        }
    }

    @Nested
    class persistence_dto {

        @Test
        void PersistenceObject_interface_should_extend_HumbleObject() {
            theClass(PersistenceObject.class)
                    .should().beAssignableTo(HumbleObject.class)
                    .as("The PersistenceObject interface should extend HumbleObject.")
                    .check(classes);
        }

        @Test
        void persistence_objects_be_public() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().implement(PersistenceObject.class)
                    .should().bePublic()
                    .as("The persistence objects should be public.")
                    .check(classes);
        }

        @Test
        void persistence_objects_should_be_named_ending_with_PO() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().implement(PersistenceObject.class)
                    .should().haveSimpleNameEndingWith("PO")
                    .as("The persistence objects should be named ending with 'PO'.")
                    .check(classes);
        }

        @Test
        void persistence_objects_should_implement_PersistenceObject() {
            classes()
                    .that().resideInAPackage("..persistence..")
                    .and().haveSimpleNameEndingWith("Dto")
                    .and().areNotInterfaces()
                    .should().implement(PersistenceObject.class)
                    .as("The persistence objects should implement 'PersistenceObject' interface.")
                    .check(classes);
        }
    }
}
