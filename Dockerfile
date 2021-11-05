FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/geolocalizacion-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} geolocalizacion.jar
ENTRYPOINT ["java","-jar","/geolocalizacion.jar"]