# --- Etapa 1: Testing de la aplicación (Opcional, si tienes tests unitarios/integración) ---
FROM maven:3.8.8-eclipse-temurin-17 AS test
WORKDIR /user_app

# Copia el descriptor de dependencias y el código para los tests
COPY pom.xml .
COPY src ./src

# Ejecuta los tests. Si tienes un wrapper de Maven (mvnw), úsalo.
# Si no, puedes usar 'mvn test' directamente.
# RUN chmod +x mvnw
# RUN ./mvnw test
RUN mvn test

# --- Etapa 2: Compilación del proyecto ---
FROM maven:3.8.8-eclipse-temurin-17 AS compile
WORKDIR /user_app

# Copia el descriptor de dependencias y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del código y compila el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 3: Imagen liviana de ejecución (Producción) ---
FROM eclipse-temurin:17-jre
WORKDIR /user_app

# Copia el JAR compilado de la etapa 'compile'
COPY --from=compile /user_app/target/*.jar user_app.jar

# Expone el puerto que tu aplicación usa
EXPOSE 8081

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "user_app.jar"]