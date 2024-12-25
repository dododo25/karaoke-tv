#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn
COPY src ./src
RUN ls -la . && ./mvnw -f ./pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/karaoke-tv-0.0.1.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/target/karaoke-tv-0.0.1.jar"]