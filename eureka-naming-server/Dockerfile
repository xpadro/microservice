FROM openjdk:8-jdk

VOLUME /tmp

ARG JAR_FILE=./eureka-naming-server/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]