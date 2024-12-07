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
    echo "Building HBase image..."
    docker compose -f ../../docker-compose.yml build hbase
}

pull_images
build_collectora
build_hbase
