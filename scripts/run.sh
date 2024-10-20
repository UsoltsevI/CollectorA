#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR

java -jar ../build/libs/CollectorA-0.0.1.jar
