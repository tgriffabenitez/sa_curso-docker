ARG MSCV_NAME=ms-courses

FROM openjdk:17-jdk-alpine as builder
ARG MSCV_NAME

WORKDIR /app/${MSCV_NAME}

COPY ./pom.xml /app
COPY  ./.mvn ./.mvn
COPY mvnw .
COPY pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY /src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
ARG MSCV_NAME

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/$MSCV_NAME/target/ms-courses-0.0.1-SNAPSHOT.jar .

ENV PORT=8086
EXPOSE $PORT

CMD ["java", "-jar", "ms-courses-0.0.1-SNAPSHOT.jar"]