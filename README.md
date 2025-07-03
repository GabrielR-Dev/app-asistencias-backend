AssApp

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

Instalación

1. Clona el repositorio:
   git clone https://github.com/GabrielR-Dev/app-asistencias-backend.git
   cd app-asistencias-backend

2. Instalar dependencias:
   npm install


Configuracion de archivo application.properties
  En el siguiente archivo puedes modificar datos para: trabajar de manera local o bien dejar por defecto ya que la DB por defecto se encuentra en un servicio en la nube.

  Si optas por levantarlo de manera locar dejo las propiedades a modificar:
     spring.datasource.url= (ingresa datos locales)
     spring.datasource.username= (ingresa datos locales)
     spring.datasource.password= (ingresa datos locales)

    IMPORTAANT! si queres modificar la base de datos (Actualmente es postgresql) debes modificar el archivo POM 
      - quitar dependencia de postgreSQL
      - instalar las dependencias de la DB que se decea utilizar
      - ademas de las primeras propiedades tambien debes modificar esta propiedad en application.properties: 
            spring.datasource.driver-class-name=org.postgresql.Driver

Enlace repositorio frontend: https://github.com/GabrielR-Dev/app-asistencias-frontend

Descarga AsApp: LINK

Colaboradores:
   - Valentin039
   - eemaevv12
   - InTheSide1901

Contacto
GitHub: GabrielR-Dev

  
