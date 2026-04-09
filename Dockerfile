# Stage 1: Build
FROM gradle:9.4-jdk21-alpine AS builder

WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copiar código fonte
COPY src ./src

# Build da aplicação (sem testes para agilizar)
RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar o JAR do estágio de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Expor porta
EXPOSE 8080

# Configurações de JVM otimizadas para containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
