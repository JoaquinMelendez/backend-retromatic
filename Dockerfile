FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar ./backend-retromatic.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend-retromatic.jar"]