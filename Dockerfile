#
# Build stage
#
FROM openjdk:17-jdk-slim AS build
RUN ls -la ./usr && ./mvnw -f ./pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
ARG JAR_FILE=./target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]