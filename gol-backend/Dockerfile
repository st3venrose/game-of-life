FROM maven:3.6.0-jdk-13-alpine AS build
WORKDIR /usr/src/app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package

FROM openjdk:13-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/usr/app/app.jar"]
