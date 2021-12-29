# Kerberos Client - A Kerberos based Authentication Client for Simulation

## Description
This Java based project has been written to create a Web Application which authenticate users from the Kerberos based authentication mechanism.

It has an endpoints which is used to make call to another endpoint which is protected with Kerberos and it also contains the configuration for Kerberos Authentication. Whenever, user tries to access the endpoint, the Spring Web Security tries to Authenticate the user request.

To configure Kerberos, the configuration available in application.properties file where Kerberos Keytab file location and resource URL provided which futher used in Kerberos based RestTemplate to make a call to Kerberos protected endpont running in other service.

## Use Case
Tis project is just a client to make a call to demonstrate the Kerberos based authentication mechanism protecting the Web applciation endpoint.

## Technology Stack
- Java 8 or later
- Maven as build tool
- Kerberos platform


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
This project is just a client for endpoint protected with Kerberos based authentication mechanism.

Below need to be configured:
```
# Define the client principal
app.user-principal=client/localhost

# Define the path of keytab file
app.keytab-location=example.keytab

# Provide path of protected endpoint which need to be called
app.access-url=http://localhost:81/endpoint
```
