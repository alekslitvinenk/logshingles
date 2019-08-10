#!/usr/bin/env bash

rm -R target

sbt ';clean ;compile; ;assembly'

docker build -t alekslitvinenk/hitcounter:latest .
docker push alekslitvinenk/hitcounter:latest