#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR

./destroy.sh
./build.sh
