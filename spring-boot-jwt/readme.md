https://explore.skillbuilder.aws/learn/course/external/view/elearning/134/aws-cloud-practitioner-essentials?sc_ichannel=ha&sc_icampaign=evergreen_ribbon_testing_cloud_practitioner_training_lm&trk=ha_awssm-9045_tnc
https://medium.com/hackernoon/delivery-pipelines-as-enabler-for-a-devops-culture-ebc45963f703

Azure
Azure DevOps Organization - Source Code, Project Management
Azure DevOps Stater - CI/CD workflow & pipeline

https://aws.amazon.com/codepipeline/product-integrations/?nc=sn&loc=6

AWS
AWS CodeCommit - Git based Source Code Management
AWS CodePipeline - CI/CD workflow & pipeline
    - AWS CodeBuild - Preconfigured build for different language
    - AWS CodeDeploy - Automates software deployments for compute services like EC2, Fargate, Lambda
AWS Elastic Beanstalk - A deployment platform for web application or services
Amazon Elastic Container Service
AWS Fargate
AWS CloudFormation
AWS Lambda
Amazon API Gateway
Amazon DynamoDB

Jekins Groovy Sandbox Pipeline:
Scripted Pipeline
Declarative Pipeline

icacls .\<pemfile>.pem /inheritance:r
icacls .\<pemfile>.pem /grant:r "%username%":"(R)"

https://codeburst.io/setup-sonarqube-for-angular-application-locally-in-three-easy-steps-8f31e339ac19
npm install -g sonar-scanner --save-dev
sonar-project.properties:
sonar.host.url=http://localhost:9000 
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

npm install -g karma-sonarqube-unit-reporter --save-dev
karma.conf.js:
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

package.json:
"scripts": {
"sonar":"sonar-scanner"
}
ng test --code-coverage --watch=false
npm run sonar

https://help.sonatype.com/repomanager2/node-packaged-modules-and-npm-registries
package.json:
"private": false,
npm publish --registry http://localhost:8081/repository/mynpmrepo/

npm adduser --registry http://localhost:8081/repository/mynpmrepo/ --always-auth

Installation on Obuntu 20.4 LTS machine:
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
which chromium-browser
export CHROME_BIN=/usr/bin/google-chrome
export CHROME_BIN=/usr/bin/google-chrome-stable
sudo apt install openjdk-11-jdk
sudo apt install maven
curl -sL https://deb.nodesource.com/setup_16.x -o nodesource_setup.sh
sudo bash nodesource_setup.sh
sudo apt install nodejs
sudo npm install -g @angular/cli
npm adduser --registry http://www.mynexus.com:8081/repository/mynpmrepo/ --always-auth
https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04
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
https://minikube.sigs.k8s.io/docs/start/
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
kubectl get po -A
minikube kubectl -- get po -A
minikube addons list
minikube addons enable ingress
https://stackoverflow.com/questions/51390161/127-0-0-15000-getsockopt-connection-refused-in-minikube
kubectl get endpoints
sudo vim /etc/hosts
192.168.49.2 hub.docker.local
sudo vim /etc/docker/daemon.json
{
  "insecure-registries" : ["hub.docker.local:5000"]
}
sudo systemctl restart docker
kubectl -n kube-system create -f kube-registry.yaml
curl http://hub.docker.local:5000/v2/_catalog

sudo vim /etc/systemd/system/own-startup.service

[Unit]
After=network.service

[Service]
ExecStart=/usr/local/bin/own-startup-script.sh

[Install]
WantedBy=default.target

sudo vim /usr/local/bin/own-startup-script.sh

#!/bin/bash
runuser -l ubuntu -c '/usr/local/bin/minikube start'

sudo chmod 744 /usr/local/bin/own-startup-script.sh
sudo chmod 664 /etc/systemd/system/own-startup.service
sudo systemctl daemon-reload
sudo systemctl enable own-startup.service
sudo systemctl status own-startup


Solve Port Opening: https://aws.amazon.com/premiumsupport/knowledge-center/ec2-connect-internet-gateway/


docker build --build-arg VER=0.0.1 -f spring-boot-jwt/dockerfile -t spring/spring-boot-jwt:latest .
docker tag spring/spring-boot-jwt:latest hub.docker.local:5000/spring/spring-boot-jwt:latest
docker push hub.docker.local:5000/spring/spring-boot-jwt:latest
docker tag openjdk:8-jre-alpine hub.docker.local:5000/alpine-jre-8:latest
docker push hub.docker.local:5000/alpine-jre-8:latest

docker run -d -p 5000:5000 --env REGISTRY_STORAGE_DELETE_ENABLED="true" -v /myapp/apps/registrydata:/var/lib/registry --restart=always --name hub.local registry

kubectl -n ingress-nginx port-forward service/ingress-nginx-controller 30001:80
kubectl port-forward --namespace kube-system $(kubectl get po -n kube-system | grep kube-registry-v0 | awk '{print $1;}') 5000:5000

netsh advfirewall firewall add rule name="Open Port 8888" dir=in action=allow protocol=TCP localport=8888

sudo systemctl restart nginx

