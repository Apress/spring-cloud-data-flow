
function getMovieRequest(){
    return `{
  "MovieRequest": {
    "action": "create",
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
      }
    ]
  }
}
    `;
}

$(function(){
    $('#movieRequest').val(getMovieRequest());
    $('#sendRequest').click(function (){

        $.ajax
        ({
            type: "POST",
            url: '/v1/api/movies',
            dataType: 'json',
            async: false,
            contentType: 'application/json',
            data: $('#movieRequest').val(),
            success: function (data) {
                alert(data.MovieResponse.message);
            }
        })
    });

});