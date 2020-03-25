FROM openjdk:8-jdk

VOLUME /tmp

ARG JAR_FILE=./bookstore/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]