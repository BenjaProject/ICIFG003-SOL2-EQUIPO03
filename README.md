# PetShop Online USS 🐾

Este proyecto es una aplicación web e-commerce completa para una tienda de mascotas, desarrollada como parte de la evaluación práctica de la asignatura. La solución se compone de un backend robusto en Spring Boot y un frontend reactivo desarrollado en Angular, orquestados localmente con Docker.

---

## 🏗️ Arquitectura y Tecnologías

### Backend
* **Lenguaje y Framework:** Java 17 con Spring Boot 4.x.
* **Persistencia:** Spring Data JPA con Hibernate (creación de tablas automática mediante `ddl-auto=update`).
* **Base de Datos:** MySQL 8.0 (migrado de PostgreSQL).
* **Auditoría & Logging:** Implementación de `@Slf4j` con trazas del sistema dirigidas al archivo local `logs/backend.log`.
* **Inicialización:** Inicialización de datos automática mediante scripts SQL DML (`data.sql`) adaptados con cláusulas `INSERT IGNORE` y `FROM DUAL` compatibles con MySQL.

### Frontend
* **Framework:** Angular 17+ con Standalone Components.
* **Manejo de Estado:** Refactorizado completamente utilizando **Angular Signals** y Stores reactivos.
* **Estilos y Usabilidad:** CSS3 puro responsivo (Flexbox/Grid), animaciones de elevación hover 3D y reemplazo de emojis/texto por **iconos vectoriales SVG nativos** (papelera, carrito, lupa, etiquetas, etc.).
* **Buscador Predictivo:** Buscador de autocompletado con sugerencias flotantes y filtrado en tiempo real basado en señales computadas (`productosFiltrados`).
* **Estabilidad Visual:** Ordenamiento estable por ID de detalle (`idDetalleCarrito ASC`) implementado en backend y frontend para evitar desplazamientos de filas al modificar cantidades.

### Infraestructura (Orquestación DevOps)
* **Dockerización Local:** Contenedores aislados y vinculados mediante **Docker Compose**:
  * `mysql-database`: Motor MySQL 8.0 en puerto host **`3307`** (puerto interno `3306`) para evitar conflictos con servidores locales ocupados en el puerto `3306`. Incluye persistencia de datos mediante volúmenes.
  * `api-backend`: API REST de Spring Boot en puerto **`8080`**.
  * `web-frontend`: Servidor Nginx en puerto **`4200`** configurado para enrutamiento SPA reactivo (prevención de errores 404 en refrescos F5).

---

## 🚀 Despliegue con Docker Compose (Primera vez)

### Requisitos Previos
* **Docker Desktop** instalado y en ejecución en el sistema.

### 1. Levantar la Aplicación Completa
Abre una terminal (PowerShell, CMD o Git Bash) en la carpeta raíz del proyecto y ejecuta:

```powershell
docker compose up --build -d
```

*   `--build` compilará el código fuente Java de la API y empaquetará el frontend de Angular en el servidor Nginx de forma local.
*   `-d` iniciará los servicios en segundo plano.

### 2. Verificar el Funcionamiento
Una vez que el proceso finalice, puedes acceder a las siguientes URLs:
*   **Tienda Web (Frontend):** [http://localhost:4200](http://localhost:4200)
*   **API REST (Backend):** [http://localhost:8080](http://localhost:8080)

### 3. Inspeccionar la Base de Datos
Para ingresar de forma rápida a la consola interactiva de MySQL dentro del contenedor, ejecuta el helper batch en la raíz:
```powershell
./check.bat
```
Una vez dentro, puedes auditar las tablas y productos sembrados mediante:
```sql
SELECT id_producto, nombre_producto, precio, stock FROM producto;
```

---

## 🚦 Estrategia de Ramas y Control de Hitos (Git Flow)

*   **Hito Inicial (Solemne 2):** Resguardado bajo la etiqueta semántica `V1.0.0` para garantizar trazabilidad y posibilitar rollbacks.
*   **Rama DEV:** Utilizada para el desarrollo diario, implementaciones de características individuales y resolución de bugs rápidos.
*   **Rama QA:** Entorno de estabilización, pruebas de integración y simulación de fallos. **Esta es la rama final desde donde se debe clonar el proyecto.**

---

## 🎯 Funcionalidades e Interacciones Persistentes (RF)

*   **RF01 - Catálogo e UI Dinámica:** Interfaz responsiva con elevación hover 3D en las tarjetas de productos, visualización de precios y stock en tiempo real.
*   **RF02 - Buscador Predictivo (Autocomplete):** Barra de búsqueda con sugerencias interactivas flotantes y filtrado reactivo instantáneo.
*   **RF03 - Carrito de Compras Persistente:** Controladores inline (`- 1 +`) con validaciones de stock físico. Los cambios persisten en MySQL inmediatamente y el carrito sobrevive a los reinicios de sesión.
*   **RF04 - Formulario de Contacto:** Campos con validación reactiva del lado del cliente y persistencia de mensajes enviados en el backend.
*   **RF05 - Resiliencia Ante Caídas (Offline Handling):** Si el backend se detiene, el frontend detecta la desconexión automáticamente mostrando un banner de mantenimiento amigable (*"Servicio temporalmente no disponible. Estamos trabajando para volver pronto."*) y un botón de **"Reintentar"** para reconectarse de manera fluida una vez que el backend se inicie de nuevo.
