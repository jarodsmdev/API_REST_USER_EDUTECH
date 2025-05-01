# User Management API

Este proyecto es una API RESTful desarrollada con **Spring Boot** que permite la gestión de usuarios. Incluye operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y utiliza el patrón DTO para la transferencia de datos entre las capas de la aplicación.

## Características

- **CRUD de usuarios**: Crear, obtener, actualizar y eliminar usuarios.
- **Validación de datos**: Validación de entradas mediante anotaciones de `jakarta.validation`.
- **Patrón DTO**: Uso de `UserDto` para transferir datos entre la capa de servicio y el controlador.
- **Mapeo automático**: Implementación de MapStruct para mapear entre entidades y DTOs.
- **Manejo de excepciones**: Gestión de errores como `DuplicateKeyException` y `UserNotFoundException`.
- **Base de datos**: Persistencia de datos utilizando JPA con una base de datos relacional.

## Requisitos previos

- **Java**: Versión 17 o superior.
- **Maven**: Versión 3.8 o superior.
- **Base de datos**: Configurada en el archivo `application.properties`. Por defecto viene la configuración para MySQL. (Puedes realizar los cambios a otro motor de bases de datos si así lo prefieres).

## Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/jarodsmdev/API_REST_USER_EDUTECH.git
   ```

2. Configura la base de datos en el archivo `src/main/resources/application.properties` si deseas usar una base de datos diferente a MySQL.
  - Configura las variables del entorno indicadas en este archivo.
  - `${DB_ENDPOINT}`: Dirección del host donde se encuentra la base de datos (por ejemplo, IP o nombre del servidor).
  - `${DB_PORT}`: Puerto en el que escucha la base de datos (por defecto 3306 para MySQL).
  - `${DB_NAME}`: Nombre de la base de datos a la que se va a conectar la aplicación.
  - `${DB_USERNAME}`: Usuario con el que se autentica la conexión a la base de datos.
  - `${DB_PASSWORD}`: Contraseña del usuario para acceder a la base de datos.

3. Compila el proyecto con Maven:
   ```bash
   mvn clean install
   ```

4. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints

### Base URL
```
http://localhost:8080/api/v1/users
```

### Endpoints disponibles

| Método | Endpoint         | Descripción                              | Ejemplo de JSON de entrada/salida |
|--------|------------------|------------------------------------------|------------------------------------|
| GET    | `/`              | Obtiene todos los usuarios.              | N/A                                |
| GET    | `/{uuid}`        | Obtiene un usuario por su ID.            | N/A                                |
| POST   | `/`              | Crea un nuevo usuario.                   | Ver ejemplo de JSON más abajo.    |
| PUT    | `/`              | Actualiza un usuario existente.          | Ver ejemplo de JSON más abajo.    |
| DELETE | `/{uuid}`        | Elimina un usuario por su ID.            | N/A                                |

### Ejemplo de JSON de entrada/salida

#### Crear o actualizar un usuario
```json
{
  "userId": "b29052e1-2df8-4b2d-aa2f-6dd5cbac3c64",
  "firstName": "Susana",
  "lastName": "Oria",
  "birthDate": "1991-07-22",
  "email": "susana.oria@example.com",
  "phone": "+987654321",
  "address": "Calle Ficticia 321, Ciudad",
  "active": false,
  "rol": "ROLE_ADMIN"
}
```

## Estructura del proyecto

```
src/main/java/com/briones/users/management
├── controller       # Controladores REST
├── exception        # Clases de manejo de excepciones
├── model            # Entidades JPA y DTOs
│   ├── dto          # Clases DTO
├── repository       # Repositorios JPA
├── service          # Lógica de negocio
└── UserManagementApiApplication.java  # Clase principal
```

## Dependencias principales

- **Spring Boot Starter Web**: Para construir la API REST.
- **Spring Boot Starter Data JPA**: Para la persistencia de datos.
- **Spring Boot Starter Validation**: Para la validación de entrada de datos.
- **MySQL Database**: Base de datos MySQL para desarrollo y pruebas.
- **MapStruct**: Para el mapeo entre entidades y DTOs.
- **Lombok**: Para reducir el código boilerplate.
- **Swagger UI**: Para documentar y probar los endpoints de la API de manera interactiva.
  - Una vez que la aplicación esté en ejecución, puedes acceder a la interfaz de Swagger UI en la siguiente URL:
  ```http://localhost:8081/swagger-ui/index.html```

## Excepciones personalizadas

- **DuplicateKeyException**: Se lanza cuando se intenta crear o actualizar un usuario con un correo electrónico ya existente.
- **UserNotFoundException**: Se lanza cuando no se encuentra un usuario con el ID o correo proporcionado.

## Pruebas

Para ejecutar las pruebas, utiliza el siguiente comando:
```bash
mvn test
```

## Autor

Desarrollado por **jarodsmdev**.

## Licencia

Este proyecto está licenciado bajo la [MIT License](LICENSE).
