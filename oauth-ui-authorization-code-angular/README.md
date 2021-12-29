# OpenID & OAuth2.0 Front-End Server - A front-end server with OpenID Connect Authentication

## Description
This is a Spring Boot based server application which contains the Angular code written in Typescript. When it compiles, the Typescript code compiled and translated in HTML and Javascript files and placed in resources directory.

When this application runs, the angular artifacts served as static resource to form web application / page.

This project demonstrate the OpenID Connect based authentication and OAuth 2.0 based authorization.

It has page to login, while login OpenID Connect based authentication performed with help of Authorization server (here used Keycloak as Authorization server). Once Authentication completed, JWT token generated and this token further used with backend protected with OAuth 2.0.

## Use Case
This project is used to demonstrate the OpenID Connect based Authentication with help of Keycloak as Authorization server.

## Technology Stack
- Java 8 or later
- Maven as build tool
- Nodejs
- Angular
- Keycloak Infrastructure & OAuth 2.0

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
In this project there is one configuration file application.yml file which contains only server port configuration.

Additionally, in this project Keycloak sever URL for Authentication and Application resource server are provided in Typescript code which need to be changes as per need to target correct Keycloak and Resource server.

## For Testing

Authorization Server (Keycloak): https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-authorization-server
Keyclock: http://localhost:8083/auth

Resource Server Project: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-resource-server
Resource Server: http://localhost:8081/resource-server

This Project:
AngularUI: http://localhost:8089/

Admin:
sk-admin
pass

Users:
john@test.com/password
mike@other.com/password
