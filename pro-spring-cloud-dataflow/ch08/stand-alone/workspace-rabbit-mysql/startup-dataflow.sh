#!/bin/sh
java -jar spring-cloud-dataflow-server-2.6.0.jar \
--spring.datasource.url=jdbc:mysql://localhost:3306/dataflow \
--spring.datasource.username=root \
--spring.datasource.password=rootpw \
--spring.datasource.driver-class-name=org.mariadb.jdbc.Driver \
--spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.host=localhost