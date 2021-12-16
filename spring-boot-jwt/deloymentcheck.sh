#!/bin/bash
runuser -l ubuntu -c '/usr/local/bin/minikube kubectl -- -n bank-apps delete -f ./spring-boot-jwt/spring-boot-jwt-k8-deployment2.yaml'
