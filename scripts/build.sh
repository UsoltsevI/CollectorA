#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR

../gradlew clean build
