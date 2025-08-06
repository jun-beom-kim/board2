FROM gradle:8.13-jdk21 AS builder
WORKDIR /app

COPY . .

# 실행 권한 추가
RUN chmod +x ./gradlew

# Gradle 빌드 실행
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]