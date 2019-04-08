# Hexagonal Architecture - Spring MVC With JPA

## Need to resolve

- [ ] The `api` module cannot read other module's `*.properties` file in folder `src/test/resources`. So I save the duplicate flyway configurations in `api` module now.
- [ ] Need to split the flyway related configurations and dependencies in the `migration` module.
- [ ] Try to extract `domain` and `application` modules to the "external module", so maybe it can share to another project like `jersey-with-mybatis`.
