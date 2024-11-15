# Etapa 1: Construir la aplicación usando Maven y Eclipse Temurin OpenJDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS builder
# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app
# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline
# Copiar el código fuente de la aplicación
COPY src ./src



# Construir el paquete de la aplicación
RUN mvn package -DskipTests
# Etapa 2: Ejecutar la aplicación usando Eclipse Temurin OpenJDK 17 JRE
FROM eclipse-temurin:17-jre-jammy
# Establecer el directorio de trabajo en el contenedor
WORKDIR /app
# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=builder /app/target/*.jar /app/app.jar
# Exponer el puerto en el que la aplicación se ejecuta
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]