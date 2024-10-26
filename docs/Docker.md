# Docker Command Description
Management is performed from the [/docker/client](../docker/client)
directory.

### ./create.sh
Creates docker images and pulls it to docker containers 
using docker compose.

### ./up.sh
This commands ups all necessary containers for 
the program to work.

### ./start.sh
This command is the same as `docker compose start ...`.
You can start a specific container by the command 
`./start.sh <container name>`.
If you tap just a `./start.sh` all containers will start.

### ./login.sh
Logs in to the docker container "collectora".

### ./restart.sh
This command is the same as `docker compose restart ...`.
You can restart a specific container by the command 
`./restart.sh <container name>`.
If you tap just a `./restart.sh` all containers will restart.

### ./stop.sh
This command is the same as `docker compose stop ...`.
You can stop a specific container by the command
`./stop.sh <container name>`.
If you tap just a `./stop.sh` all containers will stop.

### ./down.sh
Downs all containers and deletes all of it.
