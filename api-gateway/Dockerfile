FROM openjdk:8-jdk

VOLUME /tmp

ARG JAR_FILE=./api-gateway/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "/app.jar"]