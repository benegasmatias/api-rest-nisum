# api-rest-nisum
API RESTful de Registro de Usuarios
Esta aplicación expone una API RESTful para la creación de usuarios. Los usuarios pueden registrarse proporcionando su nombre, correo electrónico, contraseña y una lista de teléfonos. La API valida los datos, registra a los usuarios y proporciona un token de acceso. La aplicación está desarrollada en Java con Spring Boot y utiliza una base de datos en memoria H2.  A continuación, se detallan las características y cómo probar la aplicación.

Características
Registro de usuarios con campos de nombre, correo electrónico, contraseña y teléfonos.
Validación del formato de correo electrónico y contraseña.
Comprobación de duplicados en el correo electrónico.
Generación de un UID para el acceso a la API.
Almacenamiento en base de datos en memoria H2.
Devolución de una respuesta JSON con los detalles del usuario registrado.
Pruebas unitarias incluidas.
Diagrama de la Solución
Aquí se presenta un diagrama de alto nivel que ilustra la estructura general de la aplicación:

Diagrama de la Solución

Prerrequisitos

Asegúrate de tener instalados los siguientes componentes antes de ejecutar la aplicación:

Java 8 o superior
Maven
Git

Instrucciones de Uso
Clona el repositorio desde GitHub:


-git clone https://github.com/tu-usuario/tu-repositorio.git

Navega al directorio de la aplicación:


-cd api-rest

Ejecuta la aplicación con Maven:

-mvn spring-boot:run

La aplicación se ejecutará en http://localhost:8080.

Para entrar a la base de dato H2 http://localhost:8080/api/h2-ui/
con los atributos

    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:testdb
    username: sa
    password:
Utiliza una herramienta como Postman o curl para probar los endpoints de la API.

Endpoints

POST api/user: Registra un nuevo usuario. El cuerpo de la solicitud debe seguir el formato especificado en la descripción del proyecto.

POST api/auth/login: inicia sesion 
Ejemplo de Registro

URL: http://localhost:8080/registro

Método: POST


Cuerpo de la Solicitud (ejemplo):

    {
    
        "name": "Juan Rodriguez",
        
        "email": "juan@rodriguez.org",
        
        "password": "hunter2",
        
        "phones": [
        
                    {
                    "number": "1234567",
                    
                    "citycode": "1",
                    
                    "countrycode": "57"
                    
                    }]
    }

Respuesta Exitosa (ejemplo):

        {
        
            "id": "1", // ID del usuario
            
            "created": "2023-10-15T12:34:56", // Fecha de creación
            
            "modified": "2023-10-15T12:34:56", // Fecha de última modificación
            
            "last_login": "2023-10-15T12:34:56", // Fecha del último inicio de sesión
            
            "token": "UIID", 
            
            "isactive": true // Estado de usuario activo
        
        }

Respuesta de Error (ejemplo):

{

    "mensaje": "El correo ya registrado"

}

Diagrama de flujo del registro 

https://app.creately.com/d/VoC3gzO0aNB/view