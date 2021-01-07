#!/bin/sh

## Data Flow Servers 
docker pull springcloud/spring-cloud-dataflow-server:2.6.0
docker pull springcloud/spring-cloud-skipper-server:2.5.0

## Middleware
docker pull mysql:5.7.25
docker pull rabbitmq:3.8.3-alpine
docker pull confluentinc/cp-kafka:5.3.1
docker pull confluentinc/cp-zookeeper:5.3.1
docker pull bitnami/zookeeper:latest
docker pull bitnami/kafka:latest
docker pull nats:latest

## Monitoring
docker pull springcloud/spring-cloud-dataflow-grafana-prometheus:2.5.2.RELEASE
docker pull prom/prometheus:v2.12.0
docker pull micrometermetrics/prometheus-rsocket-proxy:latest

## Tools
docker pull springcloud/openjdk:2.0.0.RELEASE

## App Starter

docker pull springcloudstream/http-source-rabbit:2.1.1.RELEASE
docker pull springcloudstream/filter-processor-rabbit:2.1.1.RELEASE
docker pull springcloudstream/groovy-transform-processor-rabbit:2.1.1.RELEASE
docker pull springcloudstream/log-sink-rabbit:2.1.2.RELEASE
