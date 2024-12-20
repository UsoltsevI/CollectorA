#!/bin/bash

pull_images() {
  echo "Docker pulling images..."
  docker compose pull
  echo "Pulling is finished"
}

build_containers() {
  BASEDIR=$(dirname "$0")
  cd $BASEDIR
  echo "Building CollectorA image..."
  docker compose -f ../../docker-compose.yml build collectora
}

pull_images
build_containers
