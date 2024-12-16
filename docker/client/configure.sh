#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR

docker cp ../hbase/hbase-site.xml hbase:/root/hbase/conf/hbase-site.xml
