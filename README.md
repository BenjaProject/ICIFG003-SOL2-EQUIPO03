# PetShop Online USS 🐾

Este proyecto es una aplicación web e-commerce completa para una tienda de mascotas, desarrollada como parte de la evaluación práctica de la asignatura. La solución se compone de un backend robusto en Spring Boot y un frontend reactivo desarrollado en Angular.

---

## 🏗️ Arquitectura y Tecnologías

### Backend
* **Lenguaje y Framework:** Java 17 con Spring Boot 3.x.
* **Persistencia:** Spring Data JPA con Hibernate.
* **Base de Datos:** PostgreSQL.
* **Seguridad y Control:** Inicialización de datos automática mediante scripts SQL condicionales anti-duplicidad.

### Frontend
* **Framework:** Angular 17+ con Standalone Components.
* **Manejo de Estado:** Angular Signals y Stores reactivos.
* **Estilos:** CSS3 puro con técnicas de Flexbox y CSS Grid.
* **Responsive Design:** Soporte completo para Desktop (>1024px), Tablet (768px-1023px) y Móviles (<768px).

---

## 🚀 Instrucciones de Configuración y Despliegue

### Requisitos Previos
* **Java 17 JDK** o superior.
* **Node.js** (versión LTS recomendada) y **npm**.
* **PostgreSQL** instalado y corriendo localmente.

---

### 1. Configuración del Backend

1. Crea una base de datos en PostgreSQL con el nombre `petshop_db`.
2. Revisa el archivo de propiedades en `/backend/src/main/resources/application.properties` y ajusta las credenciales si es necesario:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/petshop_db
   spring.datasource.username=tu_usuario_postgres
   spring.datasource.password=tu_contraseña
   ```
3. Ejecuta el backend utilizando el Maven Wrapper. Desde la raíz de la carpeta `/backend`, ejecuta:
   * **En Windows (PowerShell):**
     ```powershell
     ./mvnw.cmd spring-boot:run
     ```
   * **En macOS/Linux:**
     ```bash
     ./mvnw spring-boot:run
     ```
4. Al arrancar, Spring Boot creará el esquema y ejecutará el script `/backend/src/main/resources/data.sql` para poblar automáticamente los datos del cliente, las categorías y los productos iniciales sin generar duplicados.

---

### 2. Configuración del Frontend

1. Dirígete a la carpeta `/frontend`.
2. Instala las dependencias del proyecto:
   ```bash
   npm install
   ```
3. Levanta el servidor de desarrollo local de Angular:
   ```bash
   ng serve
   ```
4. Abre tu navegador en [http://localhost:4200](http://localhost:4200).

---

## 🎯 Funcionalidades Implementadas (RF)

* **RF01: Página Principal:** Header con título, menú de navegación dinámico, listado de productos destacados, barra lateral con una oferta destacada y pie de página con contacto.
* **RF02: Catálogo de Productos:** Despliegue dinámico de tarjetas que muestran la imagen del producto, nombre, categoría, precio, stock disponible y botón para agregar al carrito.
* **RF03: Filtrado de Productos:** Selector que permite filtrar por "Todos", "Perros", "Gatos" o "Accesorios", redibujando el DOM de forma reactiva e instantánea.
* **RF04: Carrito de Compras:** Al presionar "Agregar al carrito", se comunica con el backend descontando stock en base de datos. Se incluye una vista dedicada ("Pestaña Carrito") para listar artículos, ver subtotales, eliminar elementos individuales o vaciar el carrito completo, con sincronización de inventario en tiempo real.
* **RF05: Formulario de Contacto:** Campos con validación interactiva del lado del cliente (campos obligatorios, validación de correo por regex y mensaje mínimo de 20 caracteres con contador en tiempo real) y envío exitoso a la API del backend.

---

## ♿ Accesibilidad y Diseño Responsivo
* Vinculación de etiquetas `<label>` mediante atributos `for`/`id`.
* Textos alternativos (`[alt]`) en todas las imágenes.
* Ciclo lógico y navegable mediante tabulación por teclado.
* Visualización responsiva completa: la grilla principal pasa a una sola columna en pantallas medianas y móviles, las tarjetas ocupan el 100% de la pantalla y el menú de navegación se contrae a una disposición vertical en smartphones.
