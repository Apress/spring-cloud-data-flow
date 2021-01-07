package com.apress.cloud.stream.movie;

public enum MovieDslStream {
    MOVIE("movie",
            "http --port=9001 | splitter --expression=\"#jsonPath(payload, '$.movies')\" | " +
            "groovy-transform --script=\"https://raw.githubusercontent.com/felipeg48/scdf-scripts/master/movie-transform.groovy\" | " +
            "jdbc --columns=\"id:id,title:title,actor:actor,year:year,genre:genre,stars:stars,rating:imdb.rating,ratingcount:imdb.ratingCount\" " +
            "--table-name=\"movies\" --password=\"rootpw\" --driver-class-name=\"org.mariadb.jdbc.Driver\" --username=\"root\" " +
            "--url=\"jdbc:mysql://mysql:3306/reviews?autoReconnect=true&useSSL=false\""),
    STARS("stars",":movie.splitter > filter --expression=\"#jsonPath(payload,'$.stars') > 3\" | log"),
    IMDB("imdb-high-rating",":movie.groovy-transform > filter --expression=\"#jsonPath(payload,'$.imdb.rating') > 8.0\" | log");

    private String name;
    private String definition;

    MovieDslStream(String name, String definition){
        this.name = name;
        this.definition = definition;
    }

    public String getName(){
        return this.name;
    }
    public String getDefinition() {
        return this.definition;
    }
}
