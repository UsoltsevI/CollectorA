
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
EXPOSE 9090
# master
EXPOSE 16000
# Master UI
EXPOSE 16010
# regionserver
EXPOSE 16020
# regionserver UI
EXPOSE 16030