# Custom User Provider for Keycloak - Retrive User list stored in database

## Description
This Java based project has been written to create a custom User Provider for Keycloak appliation. This user provider adds feature to Keycloak to obtain the user list from the database table.

This provider component connects to SQL Server database, fetch the list of users and provides to Keycloak system so that Keycloak can list users and use whenever authentication or authorization required as per configuration in Keycloak for the realm.

## Use Case
This is a custom user provider component for Keycloak system. It has been written to get list of users from SQL Server database and provide to Keycloak system which further provisioned into Keycloak realm and associated with role or permission.

Whenever, authentication or authorization performed, these users will take participation.

It mainly helps in providing alternate user repository with Keycloak.

In case if same feature required with other databases then need to change the driver and pass the required parameter accordingly.

## Technology Stack
- Java 8 or later
- Maven as build tool
- Keycloak platform


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
It will create JAR file which need to be copied in Keycloak server.

## Deployment 
This is customer user provider and Keycloak has provision to add provider along with Keycloak distribution.

Locate Keycloak distribution on your server/machine/docker image and add JAR file in below directory:

```
keycloak-<version>/providers
```

e.g.

```
keycloak-15.0.2/providers
                        keycloak-custom-providers-0.1.0-SNAPSHOT.jar
```

If there is any additional jar dependency used, those also need to be placed along with provider JAR. In this case, MS SQL Server JDBC driver has been used to connect SQL Server database so driver jar also requried to be placed.
```
keycloak-15.0.2/providers
                        keycloak-custom-providers-0.1.0-SNAPSHOT.jar
                        mssql-jdbc-7.4.1.jre8.jar
```



## Configuraiton
In this custom user provider for Keycloak, the users are obtained from database and to connect database, JDBC had been used so mainly four properties need to be provided to connect the database which are:
- Database Driver
- Database URL
- Username
- Password

Once, the custom provider Jar placed into the deployment directory of Keycloak server then Keycloak need to be started.
After starting Keycloak, it will start listing he providers page which we login into the Keycloak with Admin credentials.

Now, open Provider management page, search for the custom user provider, enable it and provide the requried parameters value listed above.
E.g.
```
jdbcDriver=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbcUrl=jdbc:sqlserver://localhost:1433;DatabaseName=myuserschema
username=xxx
password=xxx
validationQuery=select @@version
```
