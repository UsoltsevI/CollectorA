#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR

cd ..
./gradlew javadoc --warning-mode=none
