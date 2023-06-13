# Variables globales para todos los builders
ARG MSCV_NAME=ms-users

FROM openjdk:17-jdk-alpine as builder

# Llamo a la variable global para usarla en este builder
ARG MSCV_NAME

WORKDIR /app/$MSCV_NAME

COPY ./pom.xml /app
COPY  ./.mvn ./.mvn
COPY mvnw .
COPY pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY /src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

# Llamo a la variable global para usarla en este builder
ARG MSCV_NAME

ARG TARGET_DIR=/app/$MSCV_NAME/target

COPY --from=builder $TARGET_DIR/ms-users-0.0.1-SNAPSHOT.jar .

# variables de entorno
ENV PORT=8085
EXPOSE $PORT

CMD ["java", "-jar", "ms-users-0.0.1-SNAPSHOT.jar"]