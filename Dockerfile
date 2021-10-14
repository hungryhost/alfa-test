FROM gradle:7.2.0-jdk16 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-alpine

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/

ENTRYPOINT ["java", "-jar","/app/alfa-0.0.1-SNAPSHOT.jar"]