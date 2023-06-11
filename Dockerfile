FROM maven:3.9-eclipse-temurin-17  AS builder
COPY . .
RUN mvn clean package
##############################################
FROM openjdk:17-slim-bullseye as production
COPY --from=builder target/ska-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]