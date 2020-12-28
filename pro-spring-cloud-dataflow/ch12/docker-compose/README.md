# Using Docker Compose

- Start Up
	```shell
	export DATAFLOW_VERSION=2.6.0
	export SKIPPER_VERSION=2.5.0	
	docker-compose -f ./docker-compose-rabbitmq.yml -f ./docker-compose-prometheus.yml up
	```
- Shutdown
	```
	docker-compose -f docker-compose-rabbitmq.yml down
	```