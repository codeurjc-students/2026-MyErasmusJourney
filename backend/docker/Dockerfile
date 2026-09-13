
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /project

COPY ../pom.xml /project/

RUN mvn dependency:go-offline

COPY ../src /project/src

RUN mvn -B package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /usr/src/app/

COPY --from=builder /project/target/*.jar /usr/src/app/app.jar

EXPOSE 8443

CMD [ "java", "-jar", "app.jar" ]