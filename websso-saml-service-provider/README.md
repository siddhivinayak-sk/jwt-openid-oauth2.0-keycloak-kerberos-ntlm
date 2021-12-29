# WebSSO Service Provider - A SAML 2.0 based WebSSO service provider with Spring Boot and Security

## Description
This spring based java project has been written to create a WebSSO Service Provider to integrate with any IDPs (single or multiple) for authentication purpose. This is generic service provider which can be integrated with any IDP (one or more) and provides features like parsing SMAL 2.0 based token and set authorities using Spring Security and redirect to the application URL (configured in yaml file as application home page for the logged in user).


## Use Case
This project can be used as Service Provider for any IDP for SAML token based authentication.

## Technology Stack
- Java 8 or later
- Spring boot 2.x
- Maven as build tool

## Build
Maven has been used as build tool for this project with default Spring boot build plugin.
Hence, we can use below maven code to build the runnable spring boot jar:

```
mvn clean package
```

## Deployment 
This project is developed with Spring Boot and it return runnable binary and be executed in multiple ways. Mostly it is executed as:

```
java -jar <jar file name>
```

## Configuration

Create private key to expose SP on SSL:
```
src/main/resources/saml/keystore.jks
```

Also add the idP certificate in keystore so that SAML token will be validated.

application.yaml Configuartion
```
application:
  # define relay state to redirect the user to configured application once SAML token validated, multiple relay state can be configured with redirection target
  defaultRelayState: application_alpha
  
  # define if it makes direct call
  directcall: false

  # Relay state and target URL mapping
  url:
    application_alpha: ''
    relayState: url

  # IDP metadata, currntly it is for sso circle, can be changed as per need
  idps:
    - file:C:/sandeep/code/source/20210427/yaml/ssocircle-metadata.xml

  # Name of redirection target and details what type of call is required
  redirectauth:
  - name: application_alpha
    http-url: ''
    http-method: POST
    key-material: false
    key-material-type: JKS
    key-material-path: ''
    key-material-secret: abc
    trust-material: false
    trust-material-type: JKS
    trust-material-path: ''
    trust-material-secret: abc
    diable-cookie-management: true
    set-custom-connection-manager: true
    headers:
      Accept: '*/*'
      Accept-Encoding: 'gzip, deflate'
      Accept-Language: 'en-GB,en;q=0.9'
      Cache-Control: 'no-cache'
      Connection: 'keep-alive'
      Content-Length: '47'
      Content-Type: 'application/x-www-form-urlencoded'
      Host: '10.118.58.109:20090'
      Origin: ''
      Pragma: 'no-cache'
      Referer: ''
      User-Agent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36'
    #query-parameters:
    #  - abc: def
    requestTemplate: ''
    #responseTemplate: 

  # Domains to be displayed on page
  domains:
    - Application:application

  # Identity label and URL mapping to show in drop down
  idpLebels:
    SSOCircle: https://idp.ssocircle.com
  
sp:
  metadata:
    # Entity id
    entityId: https://www.sk.com/saml/SSO
    
  # Login redirect in case of auth failure
  login.url: https://idp.ssocircle.com/sso/hos/SelfCare.jsp
   
# Key details used for SSL or SAML token verification    
key:
  key-alias: own
  key-store: classpath:saml/keystore.jks
  key-store-password: 123456

server:
  servlet:
    session:
      timeout: 5m

  port: 443
  ssl:
    key-alias: own
    key-store: classpath:saml/keystore.jks
    key-store-password: 123456
    key-password: 123456
    key-store-type: JKS

# Logging configuration
logging.config: 'classpath:log4j2.xml'
logging:
  file.name: logs/file.log
  level:
    com:
      sk:
        webssosp: INFO
    org:
      opensaml: INFO
      springframework:
        security:
          saml: INFO

# Database configuration
spring:
  datasource:
    driverClassName: com.microsoft.sqlserver.jdbc.SQLServerDriver
    url: jdbc:sqlserver://localhost:1433;databaseName=webssosp
    username: sa
    password: p@ssw0rd
    
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.SQLServer2012Dialect
        format_sql: true
    show-sql: true
  session:
    store-type: jdbc
    jdbc:
      initialize-schema: always

# CORS configuration
cors:
  allowedOrigins: '*'
  allowedHeaders: '*'
  allowedMethods: '*'
  


```

## For Testing
```
WebSSO: https://<host of SP>:<port of SP>/landing
```
