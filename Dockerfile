FROM openjdk:17-jdk

COPY target/4TWIN6-G5-Wizards-1.0.7-SNAPSHOT.jar .

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "4TWIN6-G5-Wizards-1.0.7-SNAPSHOT.jar"]

