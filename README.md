# Spring Bootstrap Application

[![Java](https://img.shields.io/badge/java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Maven](https://img.shields.io/badge/maven-3.9.5-blue.svg)](https://maven.apache.org/)
[![Postgres](https://img.shields.io/badge/postgres-17.4-blue.svg)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/mongodb-6.0.6-blue.svg)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/docker-20.10.24-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](http://www.opensource.org/licenses/mit-license.php)

A comprehensive Spring Boot base project that serves as an opinionated template for building robust Spring boot applications.

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
- **Docker Support**:` Docker compose` for seamless deployment.
- **Unit Testing**: Example Unit test cases for `Controller` and `Service` classes. Whole Spring boot context should not be loaded for unit tests, instead Slice testing should be implemented.
- Consistent code formatting

## Quick start

1. Clone the repository `https://github.com/officiallysingh/spring-boot-strap.git`
2. Build the application using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application in IDE by setting `spring.active.profiles` to `docker` or from cmd as follows.
   ```bash
   mvn spring-boot:run -Pdocker
   ```
   > [!IMPORTANT]
   > On the first run, it may take some time to download Docker images for Postgres and MongoDB,  
   > but the application may try to connect to databases too early before their docker containers are started,  
   > hence may throw failing due to db connection exception. Just make sure docker imaged are downloaded and then run the application again.
4. In case you want to use self-hosted Postgres and MongoDB, create `application-local.yml` as follows, update Postgres and MongoDB connection parameters.

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
         "[hibernate.show_sql]": false
         "[hibernate.format_sql]": true
         "[hibernate.use_sql_comments]": true
         "[hibernate.jdbc.time_zone]": UTC
         "[integration.envers.enabled]": true

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

## Project Structure
Following Domain Driven Design([DDD](https://martinfowler.com/bliki/DomainDrivenDesign.html)) following is the project structure which reflects DDD concepts.


## Rest APIs
### Specification and Documentation

### Implementation

### Response Format


## Validations
### Request Validation

### Entity Validations

## Database versioning

### Postgres

### MongoDB

## Audit Logging

### Postgres

### MongoDB

### Exception Handling

## Testing
The project includes Unit tests following Slice testing technique, so that the whole Spring boot context is not loaded for unit tests and they run faster.

- Unit tests

## Contributing
Feel free to fork this repository and use it as a base for your projects. If you find any issues or have suggestions for improvements, please create a pull request.

## Licence
Open source [**The MIT License**](http://www.opensource.org/licenses/mit-license.php)

## Author
[**Rajveer Singh**](https://www.linkedin.com/in/rajveer-singh-589b3950/), In case you find any issues or need any support, please email me at raj14.1984@gmail.com.
Give it a :star: on [Github](https://github.com/officiallysingh/spring-boot-spark-kubernetes) and a :clap: on [**medium.com**](https://officiallysingh.medium.com/spark-spring-boot-starter-e206def765b9) if you find it helpful.

## References
* [Spring boot](https://docs.spring.io/spring-boot/index.html)
* [Spring boot Exception Handling](https://github.com/officiallysingh/spring-boot-problem-handler)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.3/specification/configuration-metadata/annotation-processor.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.4.3/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Audit Logging - Postgres](https://hibernate.org/orm/envers)
* [Flyway Migration - Postgres](https://docs.spring.io/spring-boot/3.4.3/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.4.3/reference/data/nosql.html#data.nosql.mongodb)
* [Audit Logging - MongoDB](https://github.com/officiallysingh/spring-boot-mongodb-auditing)
* [Mongock Migration - MongoDB](https://docs.spring.io/spring-boot/3.4.3/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Validation](https://docs.spring.io/spring-boot/3.4.3/reference/io/validation.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.4.3/reference/actuator/index.html)
* [OpenFeign](https://docs.spring.io/spring-cloud-openfeign/reference/)
* [Spring Security](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-security.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.4.3/reference/messaging/kafka.html)
* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/3.4.3/reference/testing/testcontainers.html#testing.testcontainers)
* [Testcontainers](https://java.testcontainers.org/)
* [Testcontainers MongoDB Module Reference Guide](https://java.testcontainers.org/modules/databases/mongodb/)
* [Testcontainers Kafka Modules Reference Guide](https://java.testcontainers.org/modules/kafka/)
* [Testcontainers Postgres Module Reference Guide](https://java.testcontainers.org/modules/databases/postgres/)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Distributed Tracing Reference Guide](https://docs.micrometer.io/tracing/reference/index.html)
* [OTLP for metrics](https://docs.spring.io/spring-boot/3.4.3/reference/actuator/metrics.html#actuator.metrics.export.otlp)
* [Getting Started with Distributed Tracing](https://docs.spring.io/spring-boot/3.4.3/reference/actuator/tracing.html)

