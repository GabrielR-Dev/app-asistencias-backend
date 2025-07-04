AsApp - Backend

Backend desarrollado en Spring Boot para gestionar asistencias de usuarios. Backend diseñado para funcionar como parte de un sistema educativo o de control laboral, permite registrar llegadas a una asistencia (que puede ser de una clase 
particular, marcar asistencia en el trabajo, o un evento de alguien famoso) y autenticar usuarios de forma segura.

Tecnologias utilizadas
- Spring Boot
- JPA
- PostgreSQL
- JWT para autenticación  
- HMAC-SHA para hash de contraseñas  
- Configuracion de CORS
- Swagger para facilitar la documentacion y el consumo de la API
- DTO (Data Trnsfer Object) para evitar una comunicacion directa con entidades

Requisitos previos
- Java 17
- Maven
- PostgreSQL o la base de datos que desees usar
- Ide como IntelliJ o Eclipse

Instalacion

1. Clona el repositorio:
   git clone https://github.com/GabrielR-Dev/app-asistencias-backend.git
   cd app-asistencias-backend

2. Instalar dependencias:
   npm install

Configurar el archivo application.properties

Si querés ejecutar el backend de forma local, deberas configurar tu base de datos PostgreSQL en el archivio application.properties:

      spring.application.name=Asistencias
      spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      spring.datasource.driver-class-name=org.postgresql.Driver
      spring.jpa.hibernate.ddl-auto=update
      
   Si dejás la configuración por defecto, se conectara con una base de datos alojada en Render (nube).

    IMPORTAANT! si queres modificar la base de datos (Actualmente es postgresql) debes modificar el archivo POM 
      - quitar dependencia de postgreSQL
      - instalar las dependencias de la DB que se decea utilizar
      - ademas de las primeras propiedades tambien debes modificar esta propiedad en application.properties: 

      
         spring.datasource.driver-class-name=org.postgresql.Driver


Swagger (Documentación de la API)
   Cuando la app esté corriendo, accede a la documentación Swagger en:
   
      http://localhost:8080/swagger-ui/index.html

Enlace repositorio frontend: 

      https://github.com/GabrielR-Dev/app-asistencias-frontend


Descarga AsApp APK: [LINK](https://drive.google.com/file/d/1pZZsuIG_Qk3AW2p6qiC0k-xHDSmLrK0r/view)

Colaboradores:
   - @Valentin039 – Maquetador y desarrollo frontend
   - @eemaevv12 – Documentación y desarrollo backend
   - @InTheSide1901 – UI/UX y frontend
   - @GabrielR-Dev – Full Stack & despliegue

Contacto
   GitHub: @GabrielR-Dev

  
