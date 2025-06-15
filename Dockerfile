# Etapa 1: Compilación del proyecto
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /user_app

# Copia el descriptor de dependencias y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código solo después
COPY src ./src
RUN mvn clean package

# Etapa 2: Imagen liviana de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /user_app
COPY --from=build /user_app/target/*.jar user_app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user_app.jar"]
