#!/bin/sh
java -jar spring-cloud-skipper-server-2.5.0.jar \
--spring.datasource.url=jdbc:mysql://localhost:3306/dataflow \
--spring.datasource.username=root \
--spring.datasource.password=rootpw \
--spring.datasource.driver-class-name=org.mariadb.jdbc.Driver