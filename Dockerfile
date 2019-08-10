FROM openjdk:11.0-jre
COPY /target/scala-2.12 /usr/src/myapp
WORKDIR /usr/src/myapp
ENTRYPOINT [ "java", "-jar", "hitcounter-assembly-0.1.jar" ]
CMD [ "$@" ]