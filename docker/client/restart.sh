#!/bin/bash

DC_COMMAND="restart"

BASEDIR=$(dirname "$0")
cd $BASEDIR
CONFIG_FILE="../image/config.sh"

if [ ! -f "$CONFIG_FILE" ]; then
    echo "Configuration file not found!"
    exit 1
fi

source "$CONFIG_FILE"

if [ -z CONTAINERS ]; then
  echo "Container names are not set in $CONFIG_FILE"
  exit 1
fi

service=$1

for i in ${CONTAINERS[@]}; do
  if [ "$service" == "${CONTAINERS[$i]}" ]; then
    docker compose $DC_COMMAND $service
  fi
done

if [ $# -eq 0 ]; then
  docker compose $DC_COMMAND
else
  echo "Error: Write nothing or one of the following services: $CONTAINER_NAME1, $CONTAINER_NAME2 after script."
fi
