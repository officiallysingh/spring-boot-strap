# Spring Boot Sample Application
A comprehensive Spring Boot base project that serves as a template for building robust Spring boot applications following Domain Driven Design([DDD](https://martinfowler.com/bliki/DomainDrivenDesign.html)).  

## Getting Started
This project comes pre-configured with common enterprise concerns and includes demo implementations to help you get started quickly.
It is designed to be a starting point for your Spring Boot applications, providing a solid foundation with best practices and common patterns.

### Prerequisites
- [Java 21](https://sdkman.io/)
- [Maven](https://maven.apache.org)
- [Docker](https://www.docker.com)
- IDE (IntelliJ, Eclipse or VS Code) - Recommended [IntelliJ IDEA](https://www.jetbrains.com/idea)
- Optional: [Configure Formatter in IntelliJ](https://github.com/google/google-java-format/blob/master/README.md#intellij-android-studio-and-other-jetbrains-ides), similarly for other IDEs

## Features
- **Database Support**: Both `PostgreSQL` and `MongoDB` sample repositories with `Spring Data JPA` and `Spring Data Mongodb` implementations.
- **API Development**: Sample RESTful CRUD APIs following best practices and API documentation using `springdoc-openapi`.
- **Database Versioning**: Implemented using `Flyway` for `Postgres` and using `Mongock` for `MongoDB`.
- **Audit Logging**: Implemented using `Hibernate Envers` for `Postgres` and Custom implementation for `MongoDB`.
- **Docker Support**: Docker compose for seamless deployment.
- **Unit Testing**: Sample Unit test cases for `Controller` and `Service` classes.

## Installation
1. Clone the repository `https://github.com/officiallysingh/spring-boot-strap.git` 
2. Build the application using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application in IDE by setting `spring.active.profiles` to `docker` or from cmd as follows.
   ```bash
   mvn spring-boot:run -Pdocker
   ```
4. Or create `application-local.yml` as follows.
```yaml
spring:
  data:
    mongodb:
      uri: <Your MongoDB URL>
      database: <Your MongoDB Database name>
  datasource:
    url: <Your postgres URL>
    username: <Your postgres Username>
    password: <Your postgres Password>
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    open-in-view: true
    properties:
      '[hibernate.show_sql]': false
      '[hibernate.format_sql]': true
      '[hibernate.use_sql_comments]': true
      '[hibernate.jdbc.time_zone]': UTC
      '[integration.envers.enabled]': true
      
  flyway:
    enabled: true
    
mongock:
  enabled: true
  default-author: system
  index-creation: true
#  transaction-strategy: change_unit

problem:
  debug-enabled: false
  stacktrace-enabled: true
  cause-chains-enabled: false

server:
  port: 8090
    #servlet:
  #context-path:

logging:
  file:
    path: logs
    name: ${logging.file.path}/application.log
  level:
    ROOT: info
  logback:
    rollingpolicy:
      clean-history-on-start: true
debug: false

# ===================================================================
# Application specific properties
# Add your own application properties here
# ===================================================================

application:
  mongodb:
#   entity-base-packages:
#     - com.ksoot.hammer
    auditing:
      enabled: true
#       prefix:
#       suffix: _aud
```

and run the application in `local` profile by setting `spring.profiles.active` to `local` in IDE or from cmd as follows.
   ```bash
   mvn spring-boot:run -Plocal
   ```
5. Access the **Swagger** at: [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html)

### Database Support

- **Dual Database Support**
  - PostgreSQL implementation with JPA
  - MongoDB implementation
  - Database versioning for both PostgreSQL and MongoDB
  - Audit logging for entities in both databases
  - Audit log history APIs for tracking changes

### API Development

- RESTful CRUD APIs following best practices.
- API documentation using Springdoc Swagger.
- Declarative exception handling with no boilerplate code.
- Object mapping using MapStruct for clean DTO transformations.

### Development Tools

- Multiple profiles for different environments
- Docker support for easy deployment
- Unit test cases with examples
- Code formatting using Google Java Format

### Quick Start

1. Clone the repository
2. Run the application using Docker:
   ```bash
   mvn spring-boot:run -Pdocker
   ```
3. Access the API documentation at: `http://localhost:8080/swagger-ui.html`

## Project Structure

The project follows a clean architecture pattern with the following key components:

- `controllers`: REST API endpoints
- `services`: Business logic implementation
- `repositories`: Database access layer
- `models`: Entity and DTO classes
- `config`: Application configuration
- `utils`: Utility classes and helpers

## Key Implementations

### Database Versioning

- Liquibase for PostgreSQL
- MongoDB migrations

### Audit Logging

- Automatic audit trail for entity changes
- History tracking APIs
- Support for both PostgreSQL and MongoDB

### API Documentation

- Swagger UI integration
- Detailed API descriptions
- Request/Response examples

### Exception Handling

- Global exception handler
- Custom exception classes
- Proper HTTP status codes
- Meaningful error messages

## Testing

The project includes:

- Unit tests
- Integration tests
- Test containers for database testing

## Best Practices

This project implements several best practices:

- Clean code principles
- SOLID principles
- Proper separation of concerns
- Comprehensive documentation
- Consistent code formatting

## Contributing

Feel free to fork this repository and use it as a base for your projects. If you find any issues or have suggestions for improvements, please create a pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
