# Client Tools: cURL, Httpie, jq


## Single Movie
```json
{
      "id": "tt0133093",
   "title": "The Matrix",
   "actor": "Keanu Reeves",
    "year": 1999,
   "genre": "fiction",
   "stars": 5
}
```

## API
We are going to use: [API Dojo](https://rapidapi.com/user/apidojo) - [IMDB](https://rapidapi.com/apidojo/api/imdb8).

- **GET**: ***title/get-details***
- **GET**: ***title/get-ratings***

## Transform
```json
{
     "id": "tt0133093",
  "title": "The Matrix",
  "actor": "Keanu Reeves",
   "year": 1999,
  "genre": "fiction",
  "stars": 5,
   "imdb": {
   	    "image": "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
       "rating": 8.7,
  "ratingCount": 1605968 
   }
}
```


## Sample Data
```json
{
  "movies": [
    {
         "id": "tt0133093",
      "title": "The Matrix",
      "actor": "Keanu Reeves",
       "year": 1999,
      "genre": "fiction",
      "stars": 5
    },
    {
         "id": "tt0209144",
      "title": "Memento",
      "actor": "Guy Pearce",
       "year": 2000,
      "genre": "drama",
      "stars": 4
    },
    {
        "id": "tt0482571",
      "title": "The Prestige",
      "actor": "Christian Bale",
       "year": 2006,
      "genre": "drama",
      "stars": 3
    },
    {
         "id": "tt0486822",
      "title": "Disturbia",
      "actor": "Shia LaBeouf",
       "year": 2007,
      "genre": "drama",
      "stars": 3
    }
  ]
}
```

## Maven Apps
- Kafka - https://dataflow.spring.io/kafka-maven-latest
- RabbitMQ - https://dataflow.spring.io/rabbitmq-maven-latest
- Task - https://dataflow.spring.io/task-maven-latest

## Registering Apps 

Using `localhost`:

- **cURL**:
   ```shell
   curl -s -X POST \
    -d "uri=https://dataflow.spring.io/rabbitmq-maven-latest" \
    -d "force=true" \
    localhost:9393/ | jq .
   ```
- **Httpie**:
   ```shell
   http -f POST \
    :9393/apps \
    uri=https://dataflow.spring.io/rabbitmq-maven-latest \
    force=true
   ```
   If you are using another server (different from `localhost`) you can do:
   ```shell
   http -f POST \
    https://myserver.com:9393/apps \
    uri=https://dataflow.spring.io/rabbitmq-maven-latest \
    force=true
   ```

## Post Movie Data

```shell
curl -s -X POST \
-H "Content-Type: application/json" \
-d '{"movies":[{"id":"tt0133093","title":"The Matrix","actor":"Keanu Reeves","year":1999,"genre":"fiction","stars":5},{"id":"tt0209144","title":"Memento","actor":"Guy Pearce","year":2000,"genre":"drama","stars":4},{"id":"tt0482571","title": "The Prestige","actor":"Christian Bale","year":2006,"genre":"drama","stars":3},{"id":"tt0486822","title":"Disturbia","actor":"Shia LaBeouf","year":2007,"genre":"drama","stars":3}]}' \
http://localhost:9001
```
