#!/bin/bash

pull_images() {
  echo "Docker pulling images..."
  docker compose pull
}

build_collectora() {
  echo "Building CollectorA image..."
  docker compose -f ../../docker-compose.yml build collectora --detach
}

pull_images
build_collectora
