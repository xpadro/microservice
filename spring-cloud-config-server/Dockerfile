FROM openjdk:8-jdk

VOLUME /tmp

ARG JAR_FILE=./spring-cloud-config-server/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app.jar"]