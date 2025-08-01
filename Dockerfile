# 1단계: 빌드 스테이지
FROM openjdk:21-slim AS build
WORKDIR /app

# 의존성 관련 파일만 먼저 복사
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY gradlew gradlew
COPY gradle gradle

# 의존성만 다운로드
RUN chmod +x ./gradlew && ./gradlew dependencies

# 소스 복사 (변경 가능성이 높은 부분은 나중에)
COPY src src

# 실제 빌드
RUN ./gradlew build -x test

# 2단계: 런타임 스테이지
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

