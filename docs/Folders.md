# Project structure

## /docker
Contains all necessary files to build docker images.
Some of those files are used to facilitate container 
management.

### /client 
Scripts to manage containers.

### /hbase 
HBase Dockerfile and HBase configuration file.

### /image
CollectorA Dockerfile.

## /docs
Descriptions and other documents.

## /gradle
Gradle folder

## /scripts
Scripts to manage the project CollectorA.

## /src
CollectorA sources.

### /main/java/org/example/collectora/database
Classes to communicate with database.

### /main/java/org/example/collectora/network
Classes to communicate with the Internet.

### /main/java/org/example/collectora/pinterest
Classes to collect data from Pinterest.

### /resources/config
Configuration files.

## /
Gradle files, main README and docker-compose files.
