# Spring Boot Link Shortening Service

An example of Link Shortening Service developed using SpringBoot.
The service is just a simple Link Shortening REST service. It uses an in-memory database (H2) to store the data.


## Dependencies

There are a number of third-party dependencies used in the project:
	H2 database, flyway, swagger.
Please browse the Maven pom.xml file for all dependencies and libraries.


## How to Run 

### 1-Building the project

You will need:
Java JDK 21
Maven 3.9.9
Git

Clone the project and use Maven to build the service
```
$ mvn clean install
```
### 2 -Run service

You can run using
```
$ java -jar Shortening-0.0.1-SNAPSHOT.jar
```

## Endpoints

You can find all endpoints on Swagger UI (giving REST service details), just open your browser and put link:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

POST `/shorten`, GET `/{shortenedCode}`, POST `/app/registration` are public, aditionally, /shorten can also be used with login, then our links are saved.
To call other endpoints we need to be authenticated by sending a header with basic authentication. Before you need register user by /app/registration endpoint.


