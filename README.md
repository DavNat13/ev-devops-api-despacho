# API Despachos - Innovatech Chile

Este repositorio contiene el microservicio de **Gestión de Despachos** desarrollado en Spring Boot para la empresa Innovatech Chile. El proyecto forma parte de la Evaluación Parcial N°2 de la asignatura Introducción a Herramientas DevOps.

## 🚀 Descripción del Proyecto
La API de Despachos es un microservicio encargado de procesar y gestionar la información logística de la empresa. Está diseñado bajo una arquitectura de microservicios, preparado para ser contenedorizado con Docker y desplegado de forma automatizada en AWS.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 17.
* **Framework:** Spring Boot 3.4.4.
* **Gestor de Dependencias:** Maven.
* **Base de Datos:** MySQL 8.0.
* **Contenedorización:** Docker (Multi-stage builds).
* **Orquestación:** Docker Compose.
* **CI/CD:** GitHub Actions.
* **Infraestructura:** AWS (EC2, ECR, SSM).

## 📦 Contenedorización e Infraestructura

### Dockerfile (Multi-stage & Seguridad)
El proyecto utiliza un `Dockerfile` optimizado siguiendo las mejores prácticas de la industria:
1.  **Multi-stage Build:** Se divide en una etapa de `builder` (Maven) para compilar el `.jar` y una etapa final liviana (JRE Alpine) para la ejecución, reduciendo drásticamente el tamaño de la imagen final.
2.  **Usuario No-Root:** Por seguridad, la aplicación no se ejecuta como root, sino con un usuario restringido llamado `springuser` para mitigar riesgos de escalada de privilegios.
3.  **Optimización de Capas:** Se utiliza `mvn dependency:go-offline` para aprovechar el caché de Docker y acelerar las construcciones.

### Docker Compose
El archivo `docker-compose.yml` orquesta el stack completo (API + Base de Datos) asegurando:
* **Persistencia:** Uso de un *Named Volume* (`db-data`) para que los datos de los despachos no se pierdan al reiniciar los contenedores.
* **Redes:** Implementación de una red interna `back-tier` para aislar la comunicación entre microservicios.
* **Variables de Entorno:** Configuración dinámica mediante archivos `.env` para proteger credenciales.

## ⚙️ Configuración y Ejecución Local

### Prerrequisitos
* Docker y Docker Compose instalados.
* Archivo `.env` configurado con las siguientes variables:
    * `DB_ROOT_PASSWORD`, `DB_USER`, `DB_PASSWORD`.
    * `ECR_REPO_URL_DESPACHO`.

### Comandos de Ejecución
```bash
# Levantar el servicio
docker compose up -d api-despacho

# Ver logs de la aplicación
docker logs -f api-despacho

# Detener los servicios
docker compose down
```

## 🔄 Pipeline CI/CD (GitHub Actions)
Se implementó un flujo de entrega continua que se activa automáticamente al realizar un `push` en la rama `deploy`:

1. **Build & Push:** Construye la imagen Docker y la publica en Amazon ECR.
2. **Deploy:** Utiliza **AWS Systems Manager (SSM)** para conectarse a la instancia EC2 de forma segura, descargar la nueva imagen y reiniciar el contenedor sin intervención manual.

## 🛡️ Seguridad en AWS
* **Acceso Restringido:** El backend se despliega en una subred privada y no es accesible directamente desde internet.
* **Security Groups:** La comunicación está limitada solo a peticiones provenientes del Frontend en los puertos configurados (8081).
