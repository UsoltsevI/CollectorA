#!/bin/bash

docker exec zookeeper ./bin/zkServer.sh start
docker exec hbase start-hbase.sh
