# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# The file was modified by UsoltsevI
# See the original: https://apache.googlesource.com/hbase/+/rel/1.0.1/dev-support/hbase_docker

FROM ubuntu:22.04

# Install Git, which is missing from the Ubuntu base images.
RUN apt update && apt install -y git wget

# Add the dependencies from the hbase_docker folder and delete ones we don't need.
WORKDIR /root
ADD . /root
RUN find . -not -name "*tar.gz" -delete

# Install Java.
RUN wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
RUN mkdir -p /usr/java
RUN tar xzf *jdk* --strip-components 1 -C /usr/java
ENV JAVA_HOME /usr/java

# Install Maven.
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
RUN mkdir -p /usr/local/apache-maven
RUN tar xzf *maven* --strip-components 1 -C /usr/local/apache-maven
ENV MAVEN_HOME /usr/local/apache-maven

# Add Java and Maven to the path.
ENV PATH /usr/java/bin:/usr/local/apache-maven/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

# Pull down HBase and build it into /root/hbase
RUN git clone https://github.com/apache/hbase
RUN mvn -T 4 clean install -DskipTests assembly:single -f ./hbase/pom.xml

# Set HBASE_HOME, add it to the path
ENV HBASE_HOME /root/hbase
ENV PATH /root/hbase/bin:/usr/java/bin:/usr/local/apache-maven/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

# start HBase
# CMD ["/bin/bash", "-c", "start-hbase.sh; hbase shell"]
