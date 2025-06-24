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

### Base URL

La API estará disponible en la siguiente URL:

```url
http://localhost/api/v1/users
```

## Ejecución con Docker

Este proyecto incluye un `Dockerfile` y un archivo `docker-compose.yml` para facilitar la construcción y ejecución de la aplicación en un contenedor Docker.

### Requisitos previos

- **Docker**: Asegúrate de tener Docker instalado en tu sistema. [Guía de instalación de Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: Asegúrate de tener Docker Compose instalado. [Guía de instalación de Docker Compose](https://docs.docker.com/compose/install/)
- **Archivo `.env`**: Es necesario crear un archivo `.env` en la raíz del proyecto con las siguientes variables de entorno configuradas:

```dotenv
DB_ENDPOINT=<endpoint_de_tu_base_de_datos>
DB_PORT=<puerto_de_tu_base_de_datos>
DB_NAME=<nombre_de_tu_base_de_datos>
DB_USERNAME=<usuario_de_tu_base_de_datos>
DB_PASSWORD=<contraseña_de_tu_base_de_datos>
```

### Construcción de la imagen Docker y ejecución

1. Abre una terminal y navega hasta la raíz del proyecto.
2. Ejecuta el siguiente comando para construir la imagen Docker:
   ```bash
   docker compose up -d --build
   ```
3. Una vez que la imagen se haya construido y el contenedor esté en ejecución, puedes acceder a la API en la siguiente URL:
   ```
   http://localhost/api/v1/users
   ```
4. Para detener y eliminar el contenedor, ejecuta:
   ```bash
    docker compose down
    ```
   Esto detendrá y eliminará el contenedor, pero no eliminará la imagen.

### Notas importantes

El archivo `.env` no se incluye en el repositorio por razones de seguridad. Asegúrate de crearlo y configurarlo correctamente antes de ejecutar los contenedores.
El contenedor de la aplicación utiliza las variables de entorno definidas en el archivo `.env` para conectarse a la base de datos

## Endpoints

La API expone varios endpoints para interactuar con la información de los usuarios. A continuación de detallan los métodos HTTP y sus respectivas rutas.

### Endpoints disponibles

`http://localhost/`: Ruta base para indicar el funcionamiento de la API.
`http://localhost/swagger-ui/index.html`: Ruta para acceder a la documentación de la API generada por Swagger.

---

`http://localhost/api/v1/users`: Ruta base para la gestión de usuarios.
`http://localhost/api/v2/users`: Ruta base para la gestión de usuarios con implementación HATEOAS.


#### Ruta Raíz (/)

| Método | Endpoint         | Descripción                              |                                                 |
|--------|------------------|------------------------------------------|-------------------------------------------------|
| GET    | `/`              | Verifica el estado de la API.            | `Aplication is running` mostrará en el navegador|

#### Ruta de Usuarios (`api/v1/users`)

| Método | Endpoint         | Descripción                              | Ejemplo de JSON de entrada/salida  |
|--------|------------------|------------------------------------------|------------------------------------|
| GET    | `/`              | Obtiene todos los usuarios.              | N/A                                |
| GET    | `/{uuid}`        | Obtiene un usuario por su ID.            | N/A                                |
| POST   | `/`              | Crea un nuevo usuario.                   | Ver ejemplo de JSON más abajo.     |
| PUT    | `/`              | Actualiza un usuario existente.          | Ver ejemplo de JSON más abajo.     |
| DELETE | `/{uuid}`        | Elimina un usuario por su ID.            | N/A                                |

### Ruta de Usuarios (`api/v2/users`) Se implementó HATEOAS

| Método | Endpoint         | Descripción                              | Ejemplo de JSON de entrada/salida  |
|--------|------------------|------------------------------------------|------------------------------------|
| GET    | `/`              | Obtiene todos los usuarios HATEOAS       | N/A                                |
| GET    | `/{uuid}`        | Obtiene un usuario por su ID.            | N/A                                |
| POST   | `/`              | Crea un nuevo usuario.                   | Ver ejemplo de JSON más abajo.     |
| PUT    | `/`              | Actualiza un usuario existente.          | Ver ejemplo de JSON más abajo.     |
| DELETE | `/{uuid}`        | Elimina un usuario por su ID.            | N/A                                |

### Ejemplo de JSON de entrada/salida

#### Ejemplo de respuesta con HATEOAS

```json
{
  "_embedded": {
    "userDtoList": [
      {
        "userId": "b29052e1-2df8-4b2d-aa2f-6dd5cbac3c64",
        "firstName": "Susana",
        "lastName": "Oria",
        "email": "susana.oria@example.com",
        "_links": {
          "self": {
            "href": "http://localhost/api/v2/users/b29052e1-2df8-4b2d-aa2f-6dd5cbac3c64"
          },
          "all-users": {
            "href": "http://localhost/api/v2/users"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost/api/v2/users"
    }
  }
}
```

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

```plain
src/main
      | /java/com/briones/users/management
      | ├── controller       # Controladores REST
      | ├── exception        # Clases de manejo de excepciones
      | ├── model            # Entidades JPA y DTOs
      | │   ├── dto          # Clases DTO
      | ├── repository       # Repositorios JPA
      | ├── service          # Lógica de negocio
      | └── UserManagementApiApplication.java  # Clase principal
      | /resources
      | ├── application.properties  # Configuración de la aplicación
      | ├── application-test.properties  # Configuración para pruebas
      | └── static
      └── templates
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
  ```http://localhost/swagger-ui/index.html```

## Excepciones personalizadas

- **DuplicateKeyException**: Se lanza cuando se intenta crear o actualizar un usuario con un correo electrónico ya existente.
- **UserNotFoundException**: Se lanza cuando no se encuentra un usuario con el ID o correo proporcionado.

## Pruebas

Este proyecto incluye pruebas unitarias centradas en la capa de servicios, utilizando **JUnit** y **Mockito** para simular dependencias y verificar el comportamiento de los métodos de negocio.

Además, se ha integrado **JaCoCo (Java Code Coverage)** para generar reportes de cobertura de código de forma automática durante la ejecución de las pruebas.

### Ejecutar pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

### Generar reporte de cobertura con JaCoCo

El reporte HTML de JaCoco se genera automáticamente al ejecutar los tests con Maven.  Para visualizarlo:

1. Ejecuta

```
mvn clean verify
```

2. Abre el archivo generado en la ruta

```
target/site/jacoco/index.html
```

## Autor

Desarrollado por **Leonel Briones Palacios**.

## Licencia

Este proyecto está licenciado bajo la [MIT License](LICENSE).
