# Log App Starter

This `log-sink` needs to know about the destination. This app will be listening to the `task-events`.

The `application.properties` This app will be listening to the `task-events`.

```
server.port=8083
spring.cloud.stream.bindings.input.destination=task-events
```
You can run this sink first just with:

```
java -jar log-sink-rabbit-2.1.3.RELEASE.jar 
```

