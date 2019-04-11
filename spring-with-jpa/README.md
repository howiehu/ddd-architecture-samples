# Hexagonal Architecture - Spring MVC With JPA

# Memo

- Because the Orika cannot find correct constructor under Spring Boot's Tomcat (and some other issues), so I use Jetty for the temporary solution.
    - https://github.com/spring-projects/spring-boot/issues/2308
    - https://stackoverflow.com/questions/52321672/orika-wrong-classloader-used-in-case-of-using-embedded-tomcat
