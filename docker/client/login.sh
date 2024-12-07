#!/bin/bash

USER="root"

service=$1

if [[ -z $service ]]; then
  service="collectora"
fi

docker exec -it --user "$USER" "$service" /bin/bash
