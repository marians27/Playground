## Overview
Exemplary deployments of dockerized app utilizing ELK (or EFK) stack. 

## Structure
### simple-logging-app
simple-logging-app contains a simple maven application spiting some logs in stdout.
To build application use command:
```
mvn clean install
```
To build docker image with the application
```
mvn clean instal docker:build
```
Docker image can be also build in to steps:
```
mvn clean install
docker build -t myImage .
```

### deployment
This directory contains various fully-fledged deployments of the app integrated with ELK/EFK. Deployment is done by docker-compose. Each deployment example can be run via command:
```
docker-compose up
```
## fluentd/example0
This example just run fluentd which reads logs created by simple-logging-app and spit this logs out in the stdout.

#### fluentd/example1
EFK (ElasticSearch-Fluentd-Kibana) deployment.

