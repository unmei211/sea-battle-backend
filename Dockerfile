FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
ADD pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean verify package --fail-never -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
