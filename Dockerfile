FROM eclipse-temurin:21-jre-alpine

COPY build/libs/*.jar app.jar

ENV USE_PROFILE=default

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${USE_PROFILE}", "app.jar"]
