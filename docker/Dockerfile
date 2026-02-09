FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY gradle gradle
COPY gradlew gradlew
COPY build.gradle settings.gradle ./
COPY src src
RUN chmod +x gradlew \
  && ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
