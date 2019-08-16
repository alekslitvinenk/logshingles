#!/usr/bin/env bash

docker run -d \
-p 80:8080 \
--rm \
-v /data/logs/hitcounter:/var/log \
-e APP_LOG_APPENDER=rollingFile \
-e DB_USER=<user> \
-e DB_PASSWORD=<password> \
alekslitvinenk/hitcounter \
 0.0.0.0 8080