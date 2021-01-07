import groovy.json.JsonSlurper
import groovy.json.JsonOutput


def jsonSlurper = new JsonSlurper()
def movie = jsonSlurper.parseText(new String(payload))


def connection = new URL( "https://imdb8.p.rapidapi.com/title/get-ratings?tconst=${movie.id}")
                 .openConnection() as HttpURLConnection

connection.setRequestProperty( 'x-rapidapi-host', 'imdb8.p.rapidapi.com' )
connection.setRequestProperty( 'x-rapidapi-key', KEY)  //<-- Change Me
connection.setRequestProperty( 'Accept', 'application/json' )
connection.setRequestProperty( 'Content-Type', 'application/json')

if ( connection.responseCode == 200 ) {
    
    def imdb = connection.inputStream.withCloseable { inStream ->
        new JsonSlurper().parse( inStream as InputStream )
    }
    
    movie.imdb = [ "rating": imdb.rating, "ratingCount": imdb.ratingCount ]    

} else {
    println connection.responseCode + ": " + connection.inputStream.text
}

JsonOutput.toJson(movie)