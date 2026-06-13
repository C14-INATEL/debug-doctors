FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="https://github.com/C14-INATEL/debug-doctors"

WORKDIR /app

COPY target/debug-doctors-0.0.1-SNAPSHOT.jar api.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "api.jar"]

