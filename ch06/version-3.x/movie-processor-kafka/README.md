# Commands

```shell
$ docker exec -it kafka /opt/bitnami/kafka/bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic log
> {"title":"The Matrix","actor":"Keanu Reeves","year":1999,"genre":"science-fiction"}
> {"title":"Memento","actor":" Guy Pearce ","year":2000,"genre":"drama"}
> {"title":"IT","actor":"Bill Skarsgård","year":2017,"genre":"horror"}
```