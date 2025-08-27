# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

This is a multi-module Java project using Gradle 8.5 and Java 21. Each module has its own gradlew wrapper.

### Java Version Management
- Uses asdf-vm with Java 21 (temurin-21.0.6+7.0.LTS)
- Each module has .sdkmanrc configured for java=21.0.6-tem

### Build Commands
- Build all services: Run `./gradlew build` in each module directory
- Build specific module: `cd <module-name> && ./gradlew build`
- Clean builds: `./gradlew clean` in each module

### Test Commands
- Run all tests: `./gradlew test` in each module
- Run specific test: `./gradlew test --tests "ClassName.methodName"`

### Development Commands
- Start blog-service: `cd blog-service && ./gradlew bootRun`
- Start backend-for-frontend: `cd backend-for-frontend && ./gradlew bootRun`
- Generate protobuf code: `cd blog-service-proto && ./gradlew generateProto`

## Architecture Overview

This is a DDD (Domain-Driven Design) sample project implementing a blog system with microservices architecture:

### Module Structure
- **blog-service**: Core domain service using gRPC, implements DDD patterns
- **backend-for-frontend**: GraphQL BFF layer that communicates with blog-service
- **blog-service-proto**: Shared protobuf definitions for gRPC communication

### Domain-Driven Design Patterns
The blog-service follows DDD architecture:
- **Domain Layer** (`domain/contexts/blogcontext/blog/`): Contains aggregates (Blog), value objects (Draft, PublishedBlog), domain services, and repository interfaces
- **Application Layer** (`application/usecase/`): Use cases orchestrating domain logic
- **Adapters Layer** (`adapters/`): Infrastructure implementations
  - Inbound: gRPC services
  - Outbound: Database persistence with MyBatis

### Key Domain Concepts
- **Blog Aggregate**: Root entity managing Draft and PublishedBlog states
- **Draft**: Value object representing unpublished blog content
- **PublishedBlog**: Value object for published blog state
- **BlogRepository**: Domain repository interface for Blog persistence

### Communication Architecture
- **gRPC**: blog-service exposes gRPC endpoints (BlogService, DraftService)
- **GraphQL**: backend-for-frontend provides GraphQL API for clients
- **Database**: H2 in-memory database with Flyway migrations

### Technology Stack
- **Java 21**: LTS Java version with temurin distribution
- **Spring Boot 3.2.1**: Application framework
- **Gradle 8.5**: Build tool
- **gRPC**: Inter-service communication (grpc-spring-boot-starter 3.1.0)
- **Spring GraphQL**: GraphQL implementation (replaced GraphQL Java Kickstart)
- **MyBatis 3.0.3**: Database persistence
- **Flyway**: Database migrations
- **JUnit 5**: Testing framework

## Development Guidelines

### Module Dependencies
- blog-service depends on blog-service-proto for gRPC definitions
- backend-for-frontend depends on blog-service-proto for gRPC client
- Always run `./gradlew generateProto` in blog-service-proto after proto changes

### Testing Strategy
- Database tests use standard Spring Boot test annotations (FlywayTest removed for Spring Boot 3 compatibility)
- gRPC services use `@SpringBootTest` with test configurations
- Repository tests extend `MapperIntegrationTestBase`
- GraphQL tests use Spring Boot 3's native GraphQL testing with `WebGraphQlTester`
- All tests are compatible with Java 21 and Spring Boot 3.2.1

### Database Schema
- Managed by Flyway migrations in `src/main/resources/db/migration/`
- Uses H2 database for development and testing
- MyBatis mappers in `src/main/resources/mybatis/mapper/`