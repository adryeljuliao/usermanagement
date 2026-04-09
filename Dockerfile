# Stage 1: Build
FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copiar código fonte
COPY src ./src

# Build da aplicação (sem testes para agilizar)
RUN gradle clean build -x test --no-daemon