#!/bin/bash

pull_images() {
  echo "Docker pulling images..."
  docker compose pull
  echo "Pulling is finished"
}

build_collectora() {
  BASEDIR=$(dirname "$0")
  cd $BASEDIR
  echo "Building CollectorA image..."
  docker compose -f ../../docker-compose.yml build collectora
}

build_hbase() {
    BASEDIR=$(dirname "$0")
    cd $BASEDIR
    echo "Downloading the necessary archives for the HBase..."
    wget -P ../hbase/ https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
    wget -P ../hbase/ https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
    echo "Building HBase image..."
    docker compose -f ../../docker-compose.yml build hbase
}

pull_images
build_collectora
build_hbase
