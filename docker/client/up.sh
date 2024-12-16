#!/bin/bash

docker compose up collectora --detach

BASEDIR=$(dirname "$0")
cd $BASEDIR

./configure.sh
