# JWT Server - JWT Server for Token Generation and Resource for JWT token based access

## Description
This is a Spring Boot based server application which contains:

A. JWT token generation endpoint. It takes two input for user identity verification (username and password) and generate a JWT token.

B. A resource endpoint protected with Spring Web Security, and can only be accessed with valid JWT access token.

Therefore, flow is:

User generate JWT token with username and password -> Access the protected resource with JWT token as Bearer token in Authorization header.


## Use Case
This project created to demonstrate the JWT token based authorization to access the protected resource endpoint with Java and Spring boot based project.


## Technology Stack
- Java 8 or later
- Maven as build tool
- JWT

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
This project contains a configuration file which contains JWT token configuration e.g.

```
# Secret to be used in JWT signature
jwt.secret=javainuse

# Resource to generate JWT token
jwt.get.token.uri=/authenticate
```

## For testing

JWT Web
```
Swagger UI: http://127.0.0.1:8080/app/swagger-ui/
Default username/pwd: admin/admin
Token to be passed in Authorization Header as: Bearer <generated token>
```


# Setup DevOps

## Provision Machine

To setup the DevOps for this project, let's provision two machines:
1. Windows 2019 Server 

This machine will be used to install below softwares:
- Jenkins
- Nexus
- SonarQube

2. Ubuntu 20.04 LTS

This machine will be used to install the minikube and other deployment related softwares

## Install softwares on Windows server machine
- Install Web Browser e.g. Chrome
- Install JDK e.g. JDK 11
- Install Maven e.g. Maven 3.x
- Install NodeJS
- Install Groovy SDK
- Install SSH client


### Setup Jenkins

Jekins supports two types of pipeline running on Groovy Sandbox:
A. Scripted Pipeline
B. Declarative Pipeline

The Declarative pipeline is comparatively new way and good for modular pipeline steps. So we will be using declarative pipeline.

### Setup Jenkins Agent
Jenkins is most popular and widely used open-source automation tool which helps in setup CI and CD pipeline. It has wide range of support of build tools, platforms, deployment platform etc. It provides hundreds of plugins which enriches its capabilities. It also supports Cloud based deployments.
To install, please refer the article: https://siddhivinayak-sk.medium.com/build-your-ci-cd-pipeline-with-jenkins-2dc082162f86


### Setup SonarQube
SonarQube is a tool which is used for static and dynamic code analysis to analyze the project source to detect security vulnerabilities, performance suggestion, code smell, code bugs etc.
To install, please refer the article: https://siddhivinayak-sk.medium.com/static-dynamic-code-analysis-with-sonarqube-af689124dab0


### Setup Nexus
Nexus Repository Manager OSS is most popular open-source deliverable repository to publish artifacts. It has been used into the pipeline.
To install, please refer the article: https://siddhivinayak-sk.medium.com/manage-libraries-artifacts-and-deliverables-with-nexus-repository-manager-oss-2252ec3a35ff


### In case using SSH to connect Ubuntu from Windows machine with PEM file
Run Below command to change permission of pem file:

```
icacls .\<pemfile>.pem /inheritance:r
icacls .\<pemfile>.pem /grant:r "%username%":"(R)"
```

Run below command to connect with Ubuntu machine:
```
ssh -i "<pemfile>" <user>@<host or ip of ubuntu machine>
```


## Install softwares on Ubuntu machine

### Install Chrome Browser:
```
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
```

Obtain path of Chrome Binary
```
which chromium-browser
```

Setup Path of Chrome Binary
```
export CHROME_BIN=/usr/bin/google-chrome
export CHROME_BIN=/usr/bin/google-chrome-stable
```

### Install OpenJDK
```
sudo apt install openjdk-11-jdk
```

### Install Apache Maven
```
sudo apt install maven
```

### Install NodeJS
```
curl -sL https://deb.nodesource.com/setup_16.x -o nodesource_setup.sh
sudo bash nodesource_setup.sh
sudo apt install nodejs
```

### Install Angular CLI
```
sudo npm install -g @angular/cli
```

### Add Nexus User in NPM

```
npm adduser --registry http://www.mynexus.com:8081/repository/mynpmrepo/ --always-auth
```

### Install Docker
Ref: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04
```
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
apt-cache policy docker-ce
sudo apt install docker-ce
sudo systemctl status docker
sudo usermod -aG docker ${USER}
sudo su - ${USER}
groups
sudo usermod -aG docker ubuntu
```

### Install Minikube
Ref: https://minikube.sigs.k8s.io/docs/start/
```
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
kubectl get po -A
minikube kubectl -- get po -A
minikube addons list
minikube addons enable ingress
```

### Setup Docker Image registry - There are two ways, 
- run docker registry directly on docker as daemon service

Change local path in -v switch as per local directory
```
docker run -d -p 5000:5000 --env REGISTRY_STORAGE_DELETE_ENABLED="true" -v /myapp/apps/registrydata:/var/lib/registry --restart=always --name hub.local registry
```

- Install docker registry in Kubernetes

Download kube-registry from: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/blob/main/spring-boot-jwt/kube-registry.yaml
```
kubectl -n kube-system create -f kube-registry.yaml
```

In case Minikube is already running and need reset minikube to install registry,
```
minikube stop && minikube delete && rm -fr $HOME/.minikube &&  minikube start
```

Download kube-registry from: https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/blob/main/spring-boot-jwt/kube-registry.yaml

```
kubectl create -f kube-registry.yaml
```

Test if registry is working:
```
minikube ssh && curl localhost:5000
```

Port forwarding of 5000 to localhost:
```
kubectl port-forward --namespace kube-system $(kubectl get po -n kube-system | grep kube-registry-v0 | awk '{print $1;}') 5000:5000
```

Port forwarding if ingress to be exposed on localhost:
```
kubectl -n ingress-nginx port-forward service/ingress-nginx-controller 30001:80
```

Note: This is only required when registry is exposed internally. Else, it can be exposed on node port and used directly with host ip.


### Setup docker registry domain name in /etc/hosts
```
kubectl get endpoints
sudo vim /etc/hosts
192.168.49.2 hub.docker.local
```

### Make docker registry insecure to run on HTTP instead of HTTPS:
```
sudo vim /etc/docker/daemon.json
{
  "insecure-registries" : ["hub.docker.local:5000"]
}
sudo systemctl restart docker
```

Test registry:
```
curl http://hub.docker.local:5000/v2/_catalog
```

### If want to create service to start minikube

Create a service file:
```
sudo vim /etc/systemd/system/own-startup.service

[Unit]
After=network.service

[Service]
ExecStart=/usr/local/bin/own-startup-script.sh

[Install]
WantedBy=default.target
```

Create a script file:
```
sudo vim /usr/local/bin/own-startup-script.sh

#!/bin/bash
runuser -l ubuntu -c '/usr/local/bin/minikube start'
```

Change script permission and start service:
```
sudo chmod 744 /usr/local/bin/own-startup-script.sh
sudo chmod 664 /etc/systemd/system/own-startup.service
sudo systemctl daemon-reload
sudo systemctl enable own-startup.service
sudo systemctl status own-startup
```

### Install nginx
```
sudo apt install nginx
```

Change nginx configuration:
```
```

Restart nginx service:
```
sudo systemctl restart nginx
```


### Setup SonarQube analysis in Node project
Ref:https://codeburst.io/setup-sonarqube-for-angular-application-locally-in-three-easy-steps-8f31e339ac19

Install Sonar Scanner:
```
npm install -g sonar-scanner --save-dev
```

Create properties file: sonar-project.properties
```
sonar.host.url=http://www.mysonar.com:9000 
sonar.login=admin
sonar.password=admin
sonar.projectKey=my-angular-app
sonar.sourceEncoding=UTF-8
sonar.sources=src
sonar.tests=src
sonar.exclusions=**/node_modules/**
sonar.test.inclusions=**/*.spec.ts
sonar.typescript.lcov.reportPaths=coverage/lcov.info
sonar.testExecutionReportPaths=reports/ut_report.xml
```

Install SonarQube reporter:
```
npm install -g karma-sonarqube-unit-reporter --save-dev
```

Modify Karma conf for SonarQube analysis:
Change in karma.conf.js:
```
plugins: [
      require('karma-sonarqube-unit-reporter')
    ],
    sonarQubeUnitReporter: {
      sonarQubeVersion: 'LATEST',
      outputFile: 'reports/ut_report.xml',
      overrideTestDescription: true,
      testPaths: ['./src'],
      testFilePattern: '.spec.ts',
      useBrowserName: false
    },
    reporters: ['sonarqubeUnit'],
```


Change in package.json:
```
"scripts": {
"sonar":"sonar-scanner"
}
```

Run Below command to run test and sonar analysis:
```
ng test --code-coverage --watch=false
npm run sonar
```

### In case publishing npm artificat on private Nexus:
Ref: https://help.sonatype.com/repomanager2/node-packaged-modules-and-npm-registries

Change in package.json:
```
"private": false,
```

Run below command to publish npm artifact:
```
npm publish --registry http://localhost:8081/repository/mynpmrepo/
```

Add user in npm of Nexus to publish artifact in Nexus:
```
npm adduser --registry http://localhost:8081/repository/mynpmrepo/ --always-auth
```

### In case of port related issues
```
Solve Port Opening: https://aws.amazon.com/premiumsupport/knowledge-center/ec2-connect-internet-gateway/
netsh advfirewall firewall add rule name="Open Port 8888" dir=in action=allow protocol=TCP localport=8888
```

### Below are three micro-services configured to run polyglot DevOps pipeline and form an application CI&CD pipeline 
1. Java based Micro-service - https://github.com/siddhivinayak-sk/jwt-openid-oauth2.0-keycloak-kerberos-ntlm/tree/main/spring-boot-jwt
2. Node based Angular micro-service - https://github.com/siddhivinayak-sk/node-angular-front-end
3. .Net based micro-service written C# - https://github.com/siddhivinayak-sk/dotnetmicroservice

### Pre-Requisite:
1. Start Windows VM, get public IP
2. Start Ubuntu VM, change /etc/hosts add Windows IP
sudo vim /etc/hosts
3. Start Jenkins agent on Ubuntu
cd /apps/jenkinsagent1
./ubuntu-jenkins-agent1.sh &
4. Change environement.ts in https://github.com/siddhivinayak-sk/node-angular-front-end/blob/main/src/environments/environment.ts add Ubuntu VM ip
5. Change sonarqube url IP in: https://github.com/siddhivinayak-sk/dotnetmicroservice/blob/main/Jenkinsfile
6. Clean up artifacts




