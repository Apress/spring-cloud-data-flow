#!/bin/bash

mkdir log

cat <<EOF >>log/application.properties
# Server
server.port=8083

# Spring Cloud Stream
spring.cloud.stream.bindings.input.destination=task-events
EOF

wget -P log/ https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/log-sink-rabbit/2.1.3.RELEASE/log-sink-rabbit-2.1.3.RELEASE.jar
