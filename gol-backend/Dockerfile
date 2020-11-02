FROM openjdk:15-jdk-alpine AS build
WORKDIR /usr/src/app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B
COPY src ./src

RUN ./mvnw clean package

FROM openjdk:15-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "--enable-preview", "-jar","/usr/app/app.jar"]
