# NTLM with Spring and Waffle - A web application with NTLM authentication with Waffle

## Description
This is Spring Boot based project created to demonstrate the NTLM based authentication using Spring Web Security and Waffle which runs on Windows.

Run this application, when user launch the browser and open any resource, it automatically gets the underlying user detail from where Browser has been launched and can be obtained in Java code.

The format of user name will be received as:

DOMAIN/user

## Use Case
Using this project, a NTLM based authentication can be demonstrated with Spring Boot based web application.

## Technology Stack
- Java 8 or later
- Maven as build tool
- Waffle

## Build
Maven has been used as build tool for this project.

Before starting build, one must have below installed properly:
- JDK 8 or later
- Apache Maven 3.5x or later
- NodeJs
- Angular

Steps:
1. Install JDK
2. Install maven
3. Build
```
mvn clean package
```
It will create JAR file which can be executed.

## Deployment 
This project is Spring Boot based project so it can be started in many ways.

Normally it is executed as:
```
java -jar <jar file name>
```

## Configuration
This project contains a configuration named application.yaml which contains basic configuraiton like server port, context etc.
