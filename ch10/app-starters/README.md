# Scripts

This `setup.sh` is just to test the custom streams and see the flow.

1. You need to run the `./setup.sh`, this script will download the `splitter` and `log` app-staters and it will create the `application.properties` file with the configuration needed.
2. You need the **RabbitMQ** broker up and running. You can use:
   ```shell
   docker run \
    -d --rm \
    --name rabbit \
    -p 5672:5672 \
    -p 15672:15672 \
    rabbitmq:3.8.3-management-alpine
   ```
3. You can run them with the `java -jar` command.
4. You can test the custom `movie-source` stream app.