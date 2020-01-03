FROM openjdk:8-jre-alpine

COPY target/tasks-api-*.jar tasks-api.jar

ENTRYPOINT exec java -jar tasks-api.jar