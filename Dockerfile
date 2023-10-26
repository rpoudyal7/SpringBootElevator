FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/elevator-0.0.1-SNAPSHOT.jar elevator.jar
ENTRYPOINT ["java", "-jar", "elevator.jar"]