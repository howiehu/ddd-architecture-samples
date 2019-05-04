# DDD Architecture Samples

> *At first, you must remember, a simple blog system often does not need to use a DDD workshop (like Event Storming) to design and apply a complex architecture.*
> *However, when you are skilled, you will find DDD thinking is very usefully for your daily thought. Also, your modeling speed will be more and more fast. So you can quickly complete all the design process in your brain.*

These examples aim to demonstrate a DDD architecture with different tech stacks, which can work in the real project and let you understand and reference quickly.

When designing these samples, I refer to the following architecture patterns:

- [Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
- [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Microservice](https://www.martinfowler.com/microservices/)

And I designed them with these principles (you can call it ***CUPET***):

- Easy to **C**ode
- Easy to **U**nderstand
- Easy to **P**rotect
- Easy to **E**xtend
- Easy to **T**est

## Domain Model & Humble Object

*Work in progress...*

## Specification Pattern

## Test Strategy

### Architecture Test

*Work in progress...*

### API Test

*Work in progress...*

### Repository Test

I don't write the test for the specific implementation of repositories, and the instead is inject the repository implementation to services, and write the ***integration tests*** for the services.

Because when we used the domain models and services to replace [anemic models](https://martinfowler.com/bliki/AnemicDomainModel.html) like POJO or POCO, it makes creating input data for repositories in the tests are very difficult. The more important is only focus on the implementation of repositories is make little sense; they must work correctly in the business context (services).

### Service Test

The ***test doubles*** are handy when writing ***unit tests*** for services. This way can make the service's tests very easy because we want to focus on the business logic in service, not the implementation of domain models and repositories.

### Domain Test

*Work in progress...*
