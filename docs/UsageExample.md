# Usage example
## Assembling and launching application using docker compose

### Clone project:
```shell
git clone git@github.com:UsoltsevI/CollectorA.git
```

### Assembling:

Creation docker compose containers:
```shell
./docker/client/create.sh
./docker/client/up.sh
```
It takes approximately 15 minutes
to create images and up the containers.


Checking that the containers are running:
```shell
docker ps
```
hbase, zookeeper and collectora must be on the list.

Start zookeeper in zookeeper container:
```shell
./docker/client/login.sh zookeeper
./bin/zkService.sh start
exit
```

Copy hbase-site.xml to hbase container and start hbase:
```shell
docker cp ./docker/hbase/hbase-site.xml hbase:/root/hbase/conf/hbase-site.xml
./docker/client/login.sh hbase
start-hbase.sh
exit
```

Log in to collectora to compile the application
```shell
./docker/client/login.sh
```

Build the application:
```shell
./scripts/build.sh
```

### Running:
```shell
./scripts/run.sh
```

### Destroying:

To destroy the application in container:
```shell
./scripts/destroy.sh
```

Exit from container:
```shell
exit
```

Stop all containers:
```shell
./docker/client/stop.sh
```

Delete all containers:
```shell
./docker/client/down.sh
```
