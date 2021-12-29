# OAuth Resource Server - A resource server protected with OAuth 2.0

## Description
This is a Spring Boot based Web Application server which has REST based endpoints which are protected with Spring Web Security.

The resources are only accessed by Authorized users who has JWT token, and this token validated with Keycloak server with OAuth2.0 protocol.

## Use Case
This Resource Server is used to demonstrate the OpenID and OAuth 2.0 based user authentication and authorization.

## Technology Stack
- Java 8 or later
- Maven as build tool
- Keycloak Infrastructure & OAuth 2.0


## Build
Maven has been used as build tool for this project.

Before starting build, one must have below installed properly:
- JDK 8 or later
- Apache Maven 3.5x or later

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
This project has application.yml configuration file which contains configuration for specifying JWT issuer URI and JWT set URI for token signature verification.


## For Testing

Authorization Server (Keycloak): https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-authorization-server
Keyclock: http://localhost:8083/auth

This Project:
Resource Server: http://localhost:8081/resource-server

Angular UI Project: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-ui-authorization-code-angular
AngularUI: http://localhost:8089/

Admin:
sk-admin
pass

Users:
john@test.com/password
mike@other.com/password
