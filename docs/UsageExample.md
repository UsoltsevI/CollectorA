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

Build the application
```shell
docker exec hbase ./CollectorA/scripts/build.sh
```

### Running:
Run zookeeper and hbase:
```shell
./docker/client/run.sh
```
Run the application:
```shell
./docker/client/login.sh
cd CollectorA
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
