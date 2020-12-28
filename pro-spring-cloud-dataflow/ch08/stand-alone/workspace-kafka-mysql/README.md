# How to use this Folder

## Start 
1. Download the JARs files, you can execute the `./download.sh` script.
2. Open a Terminal and run the Docker Compose command to start MySQL and Kafka Servers.
   ```shell
   $ docker-compose up -d
   ```
3. Open a new Terminal and run Skipper
   ```shell
   $ java -jar spring-cloud-skipper-server-2.3.2.RELEASE.jar
   ```
4. Open a new Terminal and run Spring Cloud Data Flow
   ```shell
   $ java -jar spring-cloud-dataflow-server-2.4.2.RELEASE.jar
   ```


## Shutdown
1. You can do `Ctrl+c` in the Skipper and Data Flow Servers.
2. Go where the `docker-compose.yml` file is, and execute
   ```shell
   $ docker-compose down
   ```

   

