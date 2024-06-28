FROM gradle:8.5-jdk21-alpine as builder
USER root
WORKDIR /builder
COPY . /builder
RUN gradle build --stacktrace -x test --no-daemon

FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 8000
COPY --from=builder /builder/build/libs/server.jar .
USER nobody
CMD ["java", "-jar", "server.jar"]