# CollectorA

Related project: [AnalyzerA](https://github.com/UsoltsevI/AnalyzerA)

This project was created in order to collect some 
information about well-known platforms.
Note that it collects only publicly available data.

The information collected can be used to estimate the following: 
* the amount of space occupied by one user
* the amount of connections between users
* some features of the above

It can be useful when designing new platforms.

## Application Structure
See [scheme in Figma](https://www.figma.com/board/RzTIebuqjBTp3RjnWyckLG/CollectorA?node-id=0-1&node-type=canvas&t=JJLXHee1CrzO8b3q-0).
Or you can see this scheme as png in [/docs/CollectorA.png](./docs/CollectorA.png). 
*Scheme has not updated yet.*

__HBase__ was chosen as the data storage system. 
This platform allows you to create distributed and fast 
storage systems. 

For ease of development, docker compose is used.
The application runs two containers: the database and 
the application itself. Containers communicate with
each other via localhost.

__Stack:__ Spring Boot, Gradle, Hbase, Docker. 

## Usage

You can start the project using docker compose.
See the docker compose command description: 
[/docs/Docker.md](./docs/Docker.md). 

To manage the project work see [/docs/Scripts.md](./docs/Scripts.md).

### Usage example: assembling and launching application using docker compose
__Assembling:__

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
collectora_hbase and collectora_collectora must be on the list.

Logs in to collectora to manage the application
```shell
./docker/client/login.sh
```

Building an application:
```shell
./scripts/build.sh
```

__Running:__
```shell
./scripts/run.sh
```

__Destroying:__

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

*The section will be finalized later...*

## Toolchain
* [Spring Framework](https://spring.io/)
  - [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/3.2.5.RELEASE/spring-framework-reference/htmlsingle/#overview-usagescenarios)
  - [Spring Boot](https://spring.io/projects/spring-boot)
* [Hbase](https://hbase.apache.org/)
  - [Hbase reference guide](https://hbase.apache.org/book.html)
  - [HBase (client) javadoc](https://hbase.apache.org/devapidocs/org/apache/hadoop/hbase/client/package-summary.html)
  - [HBase configuration properties](https://docs.ezmeral.hpe.com/datafabric-customer-managed/78/HBase/HBaseConfigurationProperties.html)
  - [HBase Dockerfile](https://apache.googlesource.com/hbase/+/rel/1.0.1/dev-support/hbase_docker)
  Note that the official Dockerfile is outdated. It's updated
  version: [/docker/hbase/Dockerfile](./docker/hbase/Dockerfile)
  - [HBase git](https://github.com/apache/hbase)
* [Gradle](https://gradle.com/)
  - [Learning Gradle with TomGeorgy](https://tomgregory.com)
* [HTML parser: Jsoup](https://jsoup.org/)
  - [Jsoup HTML Parser Documentation](https://jsoup.org/apidocs/org/jsoup/Jsoup.html)
* [Docker](https://www.docker.com/)
  - [Docker hub](https://hub.docker.com/)
  - [Docker compose run](https://docs.docker.com/reference/cli/docker/compose/run/)
* [Google code style](https://habr.com/ru/articles/513176/)
