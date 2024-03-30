FROM openjdk:17-jdk

COPY target/Backend-1.0.2-SNAPSHOT.jar .

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "Backend-1.0.2-SNAPSHOT.jar"]

