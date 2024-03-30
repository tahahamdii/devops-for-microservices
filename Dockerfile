FROM openjdk:17-jdk

COPY target/Backend-0.0.9-SNAPSHOT.jar .

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Backend-0.0.9-SNAPSHOT.jar"]

