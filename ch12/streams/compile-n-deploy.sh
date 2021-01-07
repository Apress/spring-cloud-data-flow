#!/bin/bash

for i in `ls -d */`
do
  echo "Accessing: $i"
  cd $i
  ./mvnw clean package -DskipTests
  ./mvnw deploy -DskipTests
  cd ..
done
echo "Done"
