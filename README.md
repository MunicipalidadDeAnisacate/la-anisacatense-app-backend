# 🛠️ Backend - La Anisacatense

Este es el backend oficial de la aplicación móvil **La Anisacatense**, desarrollado con **Spring Boot 3.3.1** y **Java 17**. Gestiona las solicitudes, usuarios, estados de reclamos, notificaciones y más, brindando soporte a la app móvil disponible para iOS y Android.

---

## 🚀 Tecnologías principales

- Spring Boot
- Spring Security + JWT
- WebSockets
- JPA (Hibernate)
- MySQL
- Google Cloud Platform (Cloud SQL, Storage)
- Twilio (SMS y Email)
- Maven

---

## ⚙️ Variables de entorno

Para que el proyecto funcione correctamente, deben ser definidas las siguientes variables:

```env
# Base de datos
HOST_BD, NAME_BD, USER_BD, PASSWORD_BD

# JWT
JWT_SECRET_KEY

# Mail
MAIL_USERNAME, MAIL_PASSWORD

# Twilio
TWILIO_SID, TWILIO_TOKEN, TWILIO_PHONE_NUMBER

# Google Cloud - Buckets
GOOGLE_APPLICATION_BUCKETS_CREDENTIALS, CLOUD_PROYECT_ID, BUCKET_FILES_NAME, BUCKET_PHOTOS_NAME
```

## ▶️ Cómo iniciar el proyecto localmente
Asegurate de tener Java 17 y Maven instalados.
Configurá correctamente las variables de entorno indicadas arriba.

Ejecutá el siguiente comando en la raíz del proyecto:
```
mvn spring-boot:run
```

La aplicación quedará corriendo por defecto en:
```
http://localhost:8080
```

## 🧾 Notas adicionales
La base de datos usada es MySQL, y debe estar previamente creada con el nombre indicado en NAME_BD.
Los archivos estáticos e imágenes se almacenan en Google Cloud Storage.
Las notificaciones automáticas por SMS y correo electrónico son gestionadas con Twilio.
WebSockets están habilitados para notificaciones en tiempo real.