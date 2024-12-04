
###
# build CollectorA
###
FROM ubuntu:22.04
WORKDIR /CollectorA

COPY ./installation.sh ./installation.sh

RUN chmod +x installation.sh
RUN ./installation.sh
RUN rm ./installation.sh

# HBase Zookeeper client port
EXPOSE 2181
# REST
EXPOSE 8080
# thrift