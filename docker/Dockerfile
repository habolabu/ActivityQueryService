FROM gradle:jdk17-alpine
WORKDIR /activity-query-service

COPY src /activity-query-service/src/
COPY build.gradle .
COPY lombok.config .

RUN gradle clean build -x test

ENTRYPOINT ["java", "-jar", "./build/libs/activity-query-service-1.0.0.jar"]