# Authorization Server - An embedded Keycloak Server

## Description
This is Spring Boot based project which include the embedded Keycloak server.

It facilitate the complete Keycloak infrastructure for setting up local authorization server. It contains a JSON file which has basic configuration for Keycloak infrastructure with default Realm, users, roles and permission for demonstration purpose.

## Use Case
It is embedded Keycloak server which is used to create a demonstration of Authorization server for OpenID, OAuth 2.0, SAML technologies.


## Technology Stack
- Java 8 or later
- Maven as build tool
- Keycloak Infrastructure


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
This project has two configurations:

A. Realm JSON file - it will be loaded by embedded Keycloak server to created required Realm for demonstration

B. application.yml - It contains the properties admin username & password and realm file path


## For Testing
This Project:
Keyclock: http://localhost:8083/auth

Resource Server Project: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-resource-server
Resource Server: http://localhost:8081/resource-server

Angular UI Project: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-ui-authorization-code-angular
AngularUI: http://localhost:8089/

Admin:
sk-admin
pass

Users:
john@test.com/password
mike@other.com/password

#

Note:

This is just for testing on local. However, actual Keycloak can also be setup instead of using embedded Keycloak.
