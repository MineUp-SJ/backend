# Etapa 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos los archivos necesarios y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el resto del código y construimos el jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final liviana
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el jar desde la etapa de build
COPY --from=build /app/target/orchestrator-0.0.1.jar app.jar

# Exponer el puerto (ajustar si tu app usa otro)
EXPOSE 8080

# Comando para ejecutar el jar
ENTRYPOINT ["java", "-jar", "app.jar"]