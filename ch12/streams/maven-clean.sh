#!/bin/bash

for i in `ls -d */`
do
  echo "Accessing: $i"
  cd $i
  ./mvnw clean 
  cd ..
done
echo "Done"
