###
# build CollectorA
###
FROM ubuntu:22.04
WORKDIR /CollectorA

COPY ./installation.sh ./installation.sh

RUN chmod +x installation.sh
RUN ./installation.sh
RUN rm ./installation.sh
