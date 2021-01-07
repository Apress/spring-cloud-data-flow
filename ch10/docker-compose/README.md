# Using Docker Compose

These files have also **NATs** server included.

- Start Up
	```shell
	export DATAFLOW_VERSION=2.6.0
	export SKIPPER_VERSION=2.5.0
	docker-compose -f docker-compose-rabbitmq.yml up
	```
- Shutdown
	```
	docker-compose -f docker-compose-rabbitmq.yml down
	```