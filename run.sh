#!/usr/bin/env bash

docker run -d \
-p 80:8080 \
--rm \
-v /data/logs/hitcounter:/var/log \
-e APP_LOG_APPENDER=rollingFile \
-e DB_HOST=167.71.114.232 \
-e DB_NAME=hitcount \
-e DB_USER=root \
-e DB_PASSWORD=jobjob \
alekslitvinenk/hitcounter \
 0.0.0.0 8080