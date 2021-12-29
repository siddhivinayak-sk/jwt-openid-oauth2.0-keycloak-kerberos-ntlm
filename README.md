
# User Authentication in Web Application - JWT, OpenID Connect, OAuth 2.0, SAML, Kerberos, NTLM, KeyCloak, Minikdc

## Description
This repository contains the different projects to create and demonstrate the User Authentication and Authorization for Web Applications using different available techniques and technologies.

This helps developers and architects to understand the different possibilities for User Authentication and Authorization mechanism and choose the best for their projects.

The source code and configuration are provided here in ready to use mode so that it provides ease in setup and test on local.

## Use Case
The projects under this repository can be used to understand the different User Authentication and Authorization mechanism available for Web Applications.

Source and configuration can be checkout out and setup on local for testing purpose and can be demonstrated the technologies like JWT, OpenID Connect, OAuth, SAML etc.

## Sub-Projects
In this repository, there are sub-projects to setup different tools and technologies for user authentication and authorization:

### JWT
A. JWT Generator and Resource Server - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/spring-boot-jwt

This is a Spring Boot based web application which contains JWT token generator and resource endpoint protected with Spring Web Security.

### OpenID Connect and OAuth 2.0

To demostrate OpenID Connect and OAuth 2.0, we need to setup Authorization server, resource server and front-end which provide screens for user to login, redirect to Authorization server and show the resources:

A. Authorization Server - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-authorization-server

To perform Authentication on OpenID Connect, we need an Authorization server. There are multiple third-party Authorization server available from different software vendors like Google, Facebook, Twitter etc. Here for demonstration purose, embedded Keycloak has been used.

B. Resource Server - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-resource-server

To demonstrate the OAuth 2.0 based authorization, a resource is required which must be protected. This project contains a Spring Boot based web application which contains endpoint resource protected with Spring Web Security and can only be access using correct token.

C. Front-End Angular Application - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-ui-authorization-code-angular

This is a Spring Boot based web application which contains user screens developed into Angular and after build, it will be wrapped in Boot based project as static resources. It runs and provide user interface from where user can tries to access resource, then it redirects to Authorization server, once user provides userid and password, it again redirects to application and access the protected resource.


D. Front-End Angular Application with Zuul - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/oauth-ui-authorization-code-angular-zuul

This is again a Spring Boot based web application which contains user screens developed into Angular and after build, it will be wrapped in Boot based project as static resources. It runs and provide user interface from where user can tries to access resource, then it redirects to Authorization server, once user provides userid and password, it again redirects to application and access the protected resource.

Additionally, this contains Zuul which is a Gateway component and provides routing based upon the configuration in application.yaml. It enhance the front-end and allow to be on single URL in from front end side to prevent CORS based restrictions.

E. Custom User Provider for Keycloak - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/custom-user-provider

This project can be used to create a custom user provider for Keycloak. It can be used to show users stored in database.

### SAML

A. SAML Service Provider (SP) - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/websso-saml-service-provider

If one wants to implement SAML based authentication, this project can be used as Service Provider (SP) which connects with Identity Provider (idP). This enables users to do SSO with SAML token.

### Kerberos

A. Kerberos Server (KDC) - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/kerbores-server

This project contains two portions, first portion is used to setup KDC on local for Kerberos based authentication. The second portion is exposing one resource endpoint protected with Spring Security and can be access with Kerberos.

B. Kerberos Client - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/kerbores-client

This project contains one endpoint which internally connect to endpoint available in A with Kerberos authentication.

### NTLM

Application Server with Waffle - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/spring-waffle

If one wants to implement NTLMv2 based authentication mechanism, this can help. This project is a Web Project developed using Spring Boot and Waffle. Whenever, anyone opens browser and open any page, it goes to backend, and Waffle parse and identifies the user.
