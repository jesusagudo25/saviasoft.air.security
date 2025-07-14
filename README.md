# 🔐 saviasoft.air.security – Microservicio de Seguridad y Autenticación (JWT)

Este microservicio es responsable de la **autenticación, autorización y gestión de usuarios** dentro del ecosistema de monitoreo ambiental basado en microservicios. Utiliza **JWT (JSON Web Tokens)** para garantizar un acceso seguro y controlado a los demás servicios del sistema.

Forma parte de la arquitectura completa de la plataforma de monitoreo de calidad del aire desarrollada por **Saviasoft**.

---

## 🧱 Arquitectura general

Este servicio actúa como **proveedor de tokens JWT** y gestiona el registro e inicio de sesión de usuarios. Está conectado a un sistema de descubrimiento de servicios y se comunica con los demás microservicios mediante autenticación basada en token.

- 🧭 **Eureka Client:** Registro y descubrimiento
- 🌐 **Gateway API:** Valida el token y redirige tráfico
- 🔐 **JWT:** Seguridad entre servicios
- 🗃️ **PostgreSQL:** Almacenamiento de usuarios y roles

---

## 🔐 Flujo de Autenticación JWT

1. El usuario se registra o inicia sesión mediante `/auth/register` o `/auth/login`
2. El servicio genera un **token JWT** firmado, que incluye información sobre el usuario y su rol
3. El token es enviado en cada request posterior a través del header `Authorization: Bearer <token>`
4. Los demás microservicios validan el token con una clave secreta compartida (o pública en caso de firma asimétrica)
5. El Gateway puede actuar como primer filtro para validar autenticación antes de reenviar la solicitud

---

## 🧰 Tecnologías

* Spring Boot 3
* Spring Security 6
* JWT (jjwt / jose4j / nimbus)
* PostgreSQL (Base de datos de usuarios)
* Spring Data JPA
* Eureka Client
* OpenAPI / Swagger

---

## 🧪 Ejemplo de Peticiones

### ▶️ Registro de usuario

**POST** `/auth/register`

```json
{
  "username": "juanperez",
  "email": "juan@correo.com",
  "password": "MiClaveSegura123"
}
````

### ▶️ Login de usuario

**POST** `/auth/login`

```json
{
  "email": "juan@correo.com",
  "password": "MiClaveSegura123"
}
```

**Respuesta:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

---

## 📂 Endpoints

| Método | Ruta             | Descripción                          |
| ------ | ---------------- | ------------------------------------ |
| POST   | `/auth/register` | Registro de nuevos usuarios          |
| POST   | `/auth/login`    | Autenticación y entrega de token JWT |
| GET    | `/auth/me`       | Información del usuario autenticado  |
| GET    | `/auth/validate` | Valida un token JWT                  |

---

## 🔐 Seguridad

* Clave secreta en `.env` o `application.yml`
* Hasheo de contraseñas con BCrypt
* Validación automática de tokens en cada request mediante `OncePerRequestFilter`

---

## 🧑‍💻 Autor / Colaborador

Desarrollado por: jagudo2514@gmail.com.
