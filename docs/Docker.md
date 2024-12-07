# Docker Command Description
Management is performed from the [/docker/client](../docker/client)
directory.

Note: *all containers* means all containers mentioned 
in file [docker/image/config.sh](../docker/image/config.sh)
if variable `CONTAINERS`.

### ./create.sh
Creates docker images and pulls it to docker containers 
using docker compose.

### ./up.sh
This commands ups *all containers* for 
the program to work.

### ./start.sh <container-name>
This command is the same as `docker compose start <container-name>`.
If you tap just a `./start.sh` *all containers* will start.

### ./login.sh <container-name>
Logs in to the docker container <container-name>.
Default container is "collectora".

### ./restart.sh <container-name>
This command is the same as `docker compose restart <container-name>`.
If you tap just a `./restart.sh` *all containers* will restart.

### ./stop.sh <container-name>
This command is the same as `docker compose stop <container-name>`.
If you tap just a `./stop.sh` *all containers* will stop.

### ./down.sh
Downs all containers and deletes all of it.
