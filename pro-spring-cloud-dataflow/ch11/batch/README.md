# Running Examples


## Docker - MySQL

```shell
docker run -d --rm --name mysql \
 -e MYSQL_DATABASE=movies \
 -e MYSQL_USER=root \
 -e MYSQL_ROOT_PASSWORD=rootpw \
 -p 3306:3306 \
 mysql:5.7.25
```

```shell
docker exec dataflow-mysql mysql -uroot -prootpw -e "create database movies"
```

```shell
mysql -h localhost --protocol TCP -uroot -prootpw movies
```

## Movie Batch

One single line:

```
./mvnw spring-boot:run -Dspring-boot.run.arguments="url=https://bit.ly/2ZM57Kq imdbId=spring dropbox.path=/IMDB/ dropbox.local-tmp-folder=/tmp/ dropbox.token=YOUR-TOKEN"
```

Another alternative:

```
./mvnw -DskipTests clean package
java -jar target/movie-batch-0.0.1.jar url=https://bit.ly/2ZM57Kq imdbId=spring dropbox.path=/IMDB/ dropbox.local-tmp-folder=/tmp/ dropbox.token=YOUR-TOKEN
```