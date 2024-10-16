
###
# build CollectorA
###
FROM openjdk
WORKDIR /caa
COPY .. .
RUN ../gradlew build
CMD ["java", "-jar", "./build/libs/CollectorA-0.0.1.jar"]

###
# build hbase
###
WORKDIR /tmp
ARG HBASE_REF='master'
ENV HBASE_HOME /opt/hbase
RUN git clone https://github.com/apache/hbase.git --single-branch --depth 1 --branch "${HBASE_REF}"

# patch the in-process zookeeper to bind to all interfaces, otherwise we can't access it from outside the container
# and will be forced to use an external zookeeper cluster (not ideal for testing)
# at time of writing (July 7, 2021) this is the only way to change the bind of the in-process zookeeper
RUN sed -i 's/InetAddress.getLoopbackAddress().getHostName()/"0.0.0.0"/g' \
    ./hbase/hbase-zookeeper/src/main/java/org/apache/hadoop/hbase/zookeeper/MiniZooKeeperCluster.java

RUN mvn clean install -DskipTests --quiet assembly:single -f ./hbase/pom.xml
RUN mkdir -p /opt/hbase
RUN find /tmp/hbase/hbase-assembly/target -iname '*.tar.gz' -not -iname '*client*' \
  | head -n 1 \
  | xargs -I{} tar xzf {} --strip-components 1 -C ${HBASE_HOME}


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