#!/bin/bash

mkdir splitter log

cat <<EOF >>splitter/application.properties
# Server
server.port=8081

# Spring Cloud Stream
spring.cloud.stream.bindings.input.destination=movie
spring.cloud.stream.bindings.output.destination=imdb

# Splitter Properties
splitter.expression=#jsonPath(payload,'$.MovieRequest.movies')
EOF

cat <<EOF >>log/application.properties
# Server
server.port=8083

# Spring Cloud Stream
spring.cloud.stream.bindings.input.destination=log
EOF

wget -P splitter/ https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/splitter-processor-rabbit/2.1.2.RELEASE/splitter-processor-rabbit-2.1.2.RELEASE.jar
wget -P log/ https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/log-sink-rabbit/2.1.3.RELEASE/log-sink-rabbit-2.1.3.RELEASE.jar
