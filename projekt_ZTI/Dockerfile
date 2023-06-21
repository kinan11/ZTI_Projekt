FROM openjdk:17
ARG JAR_FILE=./target/projekt_ZTI-0.0.1-SNAPSHOT.jar/*.jar
COPY ./target/projekt_ZTI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]