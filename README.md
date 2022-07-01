# rent-a-car API

This is a Spring Boot web application responsible for control of Cars availables to rent.
Exposed API manages configuration and register Cars and Makes.

## Stack
* Java 14
* Spring WebFlux
* Spring Data R2DBC
* PostgreSql

## Getting Started

### Prerequisites

* Git
* JDK 11 or later
* Maven 3.x.x

### Clone

To get started you can simply clone this repository using git:
```
git clone https://github.com/o2junior/rentacar-api.git
```

### Environment Variables

The application is with defaults values to use the database postgresql presents on docker folder,  
but, if you wants up de application connected to another database, use the environment variables:
* DB_HOST
* DB_PORT
* DB_NAME
* DB_USERNAME
* DB_PASSWORD

### Quick start

#### Running Tests

To run the unit tests use the following command on terminal at the root project folder:
```
mvn clean test
```

#### Docker Database

To up the application on a local environment we have a containerized Postgresql database, to up then 
we can use the docker-compose or just run the following shell script.
```
./run-docker.sh
```

#### Running from the command line

You can run the application from the command line as follows:
```
mvn spring-boot:run
```

### Health check

You can check if the application is running by hitting: ```http://localhost:8080/health```

### Swagger-UI

You can check the specification of the API on: ```http://localhost:8080/swagger-ui.html```

---

### Next-Steps

- Add Health check endpoint.
- Add Listener to receive Cars and persist into database.
- Add more Unit Tests
  - Adapters
  - Repositories
  - Security classes
  - Utils
- Add Integration Tests

---
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.sql.jdbc)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)