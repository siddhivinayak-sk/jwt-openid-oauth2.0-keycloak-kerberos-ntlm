# WebSSO Service Provider - A SAML 2.0 based WebSSO service provider with Spring Boot and Security

## Description
This spring based java project has been written to create a WebSSO Service Provider to integrate with any IDPs (single or multiple) for authentication purpose. This is generic service provider which can be integrated with any IDP (one or more) and provides features like parsing SMAL 2.0 based token and set authorities using Spring Security and redirect to the application URL (configured in yaml file as application home page for the logged in user).

Vaults are used to store sensitive information securely with minimum possible access permission e.g.
- Database credential
- External system credential e.g. SFTP, Blob Storage, MQ connection credential etc.
- Other sensitive information which used into application for business processing
- Certificate used for authentication, trust or SSL purpose
- Private Keys which are used to decrypt the encrypted data

Vault support to store below three objects:
- Secret: A secret string, e.g. passwords, hostname/IPs etc.
- Certificate: To store Certificate in PEM/PFX format
- Key: To store Private/Public Key in PEM/PFX format

Using this technical utility, one can easy implement Azure KeyVault in application for storing yaml configuration, database credential, Azure Blob Credential and so on. 


## Use Case
In higher environment like production, application resource must have appropriate security. The 'security' is not only required for externals but also from internal stake holders like development team, deployment team, infrastructure team based upon the data/business process being used into application.

There are numerous concern and solution for resource security from externals and similar for internals. And one of the internal security concern here to protect/keep sensitive access details example, database credential, integration credential, private keys, certificates and so on.

This security concern requires to implement the access based mechanism so that the users who are not authorized to use can not access the resources.

Most popular way is to protect such details in ENCRYPT and store into  VAULT with proper access restrictions.
Consider a example, suppose there is a web application which usages a database and database contains the sensitive/personal information about the users of application.
Now in this scenario, the personal information must be secure. To make it secure, need to store encrypted data into database and keep the database credential very secure with limited access.

Since, database credential ultimately required into the application to establish connection, it must be stored somewhere into database like in code/property file/yaml/xml configuration. Which is vulnerable from internal stake holders like infra team, developers etc.

To overcome from this problem, there are two solutions,
1. Encrypt the contents available into yaml/configuration file
2. Keep the encryption key into Vault
3. Store certificate or other details into Vault

When application boot up/whenever request is made to access, request will be made to Vault and details are obtained.
The Vault access is so restricted that it will be access only by the production server/network/super users.
  

## Technology Stack
- Java 8 or later
- Spring boot 2.x
- Maven as build tool
- Azure Blob Storage (Example purpose only)
- Azure Key Vault
- Jasypt



## Build
Maven has been used as build tool for this project with default Spring boot build plugin.
Hence, we can use below maven code to build the runnable spring boot jar:

```
mvn clean package
```

## Deployment 
These utilities can be used as library in the domain projects or referece can be taken. 

## Encrypt values of YAML
Jasypt has been used here which provides property encryption feature with application properties/yaml configuration. Below are the steps:
1. Configure Jasypt in application by defining needed parameters like secretCode, algorithm etc.
2. Encrypt values by using

```
com.azure.keyvault.utils.EncryptionDecryptionUtils
E.g.
		String key = "myapp123";
		setKeyForJasyptEncryptionDecryption(key, "PBEWithMD5AndDES", 1000, "SunJCE", "base64");
		
		String secretValutToEncrypt = "p@ssw0rd";
		System.out.println(encryptForJasypt(secretValutToEncrypt));

```
3. Once obtained encrypted values mention into configuraiton yaml/properties file with Wrapper method ENC e.g.

```
spring.datasource.password=ENC(KZ6be0jCfWIVMBcXfGTjyy1B3ma1odlP)
```
 

## Configuration
The configuration contains mainly two type of configuration in Properties file:
###1. Jasypt Configruation - Custom Jasypt configuration to eanble encrypted value into the properties/yaml configuration. Below are the properties need to define:

A. Define PooledPBEStringEncryptor with custom encryption values

```
jasypt.encryptor.bean=encryptorBean
```
B. Create two ways to get Jasypt encryption key: online from Azure Vault and offline from application boot argument.
In case online is false, then define Jasypt key as, java -jar azure-key-vault.jar secretCode=myapp123

```
encryption.get-key-online=true
````
C. Define the algorith for encryption

```
encryption.algorithm=PBEWithMD5AndDES
```
D. Define key obtention iteration

``` 
encryption.key-obtention-iterations=1000
```
E. Pooled PBEStringEncryptor pool size

```
encryption.pool-size=1
```
F. Encryption provider, SunJCE is default provider by JRE

```
encryption.provider-name=SunJCE
```
G. Define salt generator class

```
encryption.salt-generator-class-name=org.jasypt.salt.RandomSaltGenerator
```
H. Define output type of encrypted string

```
encryption.string-output-type=base64
```

A utility has been created to create encrypted values by using the key provided (either from vault/starting argument).

 

###2. Azure Vault Configuration - With this section Azure KeyVault configuration is defined so that keys/other configuration can be obtained from KeyVault

A. Define login URL for Azure - Note: This is fixed for Azure Vault

```
azure-keyvault.azure-login-uri=https://login.microsoftonline.com/
```
B. Define scope for getting authentication token - Note: This value is fixed for Azure Vault

```
azure-keyvault.scope=https://vault.azure.net
```
C. Define Azure KeyVault URL - Obtain from Azure KeyVault page

```
azure-keyvault.resource-uri=
```
D. Define TenantID/DirectoryID from Azure KeyVault for obtaining Authentication token - Obtain it from Azure KeyVault page

```
azure-keyvault.tenant-id=
```
E. Define client ID which obtain secret from Azure KeyVault - Obtain from Access policy section from KeyVault page

```
azure-keyvault.client-id=
```
F. Define key for specified client Id in E. - Obtain from client defining page for the Azure KeyVault

```
azure-keyvault.client-key=
```
G. Name of the secret defined into Azure KeyValut and it's value will be used as secretCode for Jasypt

```
azure-keyvault.secret-name=secretCode
```
H. Custom Implementation for Fallback: Define default value of requested secret from Azure KeyVault, the default value will be returned in case of any exception while getting value from Azure KeyVault

```
azure-keyvault.secret-default-value=myapp123
```
