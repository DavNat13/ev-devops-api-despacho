#Etapa 1: Construcción de la aplicación
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución de la aplicación
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S springgroup && adduser -S springuser -G springgroup
USER springuser:springgroup

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]