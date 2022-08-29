#!/bin/bash

echo "Starting ecore...."
sleep 10
if [ "$DEBUG_MODE" ]; then
  echo "Starting in DEBUG_MODE at port: $DEBUG_PORT"
  ./usr/local/openjdk-11/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:"$DEBUG_PORT" -jar /application.jar
else
  ./usr/local/openjdk-11/bin/java -jar /application.jar
fi
