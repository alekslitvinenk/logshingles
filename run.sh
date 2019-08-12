#!/usr/bin/env bash

docker run -d \
-p 80:8080 \
--rm \
-v /data/logs:/var/log \
-e APP_LOG_APPENDER=rollingFile \
alekslitvinenk/hitcounter \
 0.0.0.0 8080