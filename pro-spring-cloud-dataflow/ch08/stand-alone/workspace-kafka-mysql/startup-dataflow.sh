#!/bin/sh
java -jar spring-cloud-dataflow-server-2.6.0.jar \
--spring.datasource.url=jdbc:mysql://localhost:3306/dataflow \
--spring.datasource.username=root \
--spring.datasource.password=rootpw \
--spring.datasource.driver-class-name=org.mariadb.jdbc.Driver \
--spring.cloud.dataflow.applicationProperties.stream.spring.cloud.stream.kafka.binder.brokers=PLAINTEXT://localhost:9092 \
--spring.cloud.dataflow.applicationProperties.stream.spring.cloud.stream.kafka.streams.binder.brokers=PLAINTEXT://localhost:9092 \
--spring.cloud.dataflow.applicationProperties.stream.spring.cloud.stream.kafka.binder.zkNodes=localhost:2181 \
--spring.cloud.dataflow.applicationProperties.stream.spring.cloud.stream.kafka.streams.binder.zkNodes=localhost:2181