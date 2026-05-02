import sys
if '--dockerfile' in sys.argv:
    content = """FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
"""
    with open("Dockerfile", "w", encoding="utf-8", newline="\n") as f:
        f.write(content)
    print("Dockerfile created")
    sys.exit(0)
    
content = """spring:
  application:
    name: industrial-equipment-api

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true

  jackson:
    serialization:
      write-dates-as-timestamps: false

server:
  port: 8080

app:
  jwt:
    secret: ${JWT_SECRET}
    expiry-ms: ${JWT_EXPIRY_MS}
"""

with open("src/main/resources/application.yml", "w", encoding="utf-8", newline="\n") as f:
    f.write(content)
print("application.yml written successfully")
