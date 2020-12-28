#!/bin/bash

curl -s -X POST \
-H "Content-Type: application/json" \
-d '{"movies":[{"id":"tt0133093","title":"The Matrix","actor":"Keanu Reeves","year":1999,"genre":"fiction","stars":5},{"id":"tt0209144","title":"Memento","actor":"Guy Pearce","year":2000,"genre":"drama","stars":4},{"id":"tt0482571","title": "The Prestige","actor":"Christian Bale","year":2006,"genre":"drama","stars":3},{"id":"tt0486822","title":"Disturbia","actor":"Shia LaBeouf","year":2007,"genre":"drama","stars":3}]}' \
http://localhost:9001