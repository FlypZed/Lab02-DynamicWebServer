# AREP Lab 02 - Web Service

Aplicación web que incluye HTML, CSS y JavaScript para mostrar un servicio web mediante el pedido de servicios.

## Inicio

### Requisitos

Antes de ejecutar el proyecto, asegúrese de contar con las siguientes herramientas instaladas:

- **Maven** - Administrador de dependencias y ciclo de vida del proyecto.
- **Java** - Ambiente de desarrollo.
- **Git** - Sistema de control de versiones y descarga del repositorio.

### Instalación y configuración

Para ejecutar el programa, siga estos pasos:

1. Clone el repositorio con el siguiente comando:

   ```sh
   git clone https://github.com/FlypZed/Lab01-WebServer.git
   ```

2. Acceda a la carpeta del repositorio descargado y ejecute el siguiente comando para iniciar la aplicación:

   ```sh
   mvn clean package exec:java -D"exec.mainClass"="app.App"
   ```

3. Una vez en ejecución, acceda desde su navegador al siguiente enlace:

    - [http://localhost:35000/home.html](http://localhost:35000/home.html)



1. Si intenta acceder a un servicio inexistente, se redirigirá a una página de error 404. Ejemplo:

    - [http://localhost:35000/imdex.html](http://localhost:35000/imdex.html)



1. Para visualizar un archivo específico, como una hoja de estilos, use:

    - [http://localhost:35000/app/home.css](http://localhost:35000/app/home.css)

---

## Arquitectura

Este prototipo sigue una arquitectura de **servidor web monolítico**, donde todas las funcionalidades están empaquetadas en una sola aplicación. La comunicación asíncrona con los servicios REST en el backend se maneja mediante JavaScript en el frontend.

---

## Documentación

La documentación del proyecto se encuentra en la carpeta `javadoc`. Para regenerarla, ejecute el siguiente comando:

```sh
mvn javadoc:javadoc
```

---

## Tecnologías utilizadas

- [Maven](https://maven.apache.org/) - Administrador de dependencias y construcción del proyecto.

---

## Alcance del Proyecto y Características

### 1. Método `GET` Estático para Servicios REST

Se implementa un método `get()` que permite a los desarrolladores definir servicios REST utilizando funciones lambda.

**Ejemplo de Uso:**

```java
get("/hello", (req, res) -> "hello world!");
```

Este mecanismo permite definir rutas claras y simples, mapeando URLs a funciones específicas para manejar las solicitudes y respuestas.

### 2. Mecanismo de Extracción de Parámetros de Consulta

Se implementa un mecanismo para extraer parámetros de consulta de las solicitudes y hacerlos accesibles dentro de los servicios REST.

**Ejemplo de Uso:**

```java
get("/hello", (req, res) -> "hello " + req.getValues("name"));
```

Esta funcionalidad facilita la creación de servicios REST dinámicos y parametrizados, permitiendo acceder fácilmente a los valores de los parámetros de consulta.

### 3. Especificación de Ubicación de Archivos Estáticos

Se introduce un método `staticfiles()` que permite a los desarrolladores definir la carpeta donde se ubican los archivos estáticos.

**Ejemplo de Uso:**

```java
staticfiles("webroot/public");
```

El framework buscará los archivos estáticos en el directorio especificado, como `target/classes/webroot/public`, facilitando la organización y gestión de recursos estáticos.

**Ejemplo de Uso:**

El siguiente código inicia un servidor web que sirve una aplicación con archivos estáticos ubicados en `target/classes/webroot`. Los servicios REST responderán a las siguientes solicitudes:

- [http://localhost:8080/App/hello?name=Aglaea](http://localhost:8080/App/hello?name=Aglaea)
- [http://localhost:8080/App/pi](http://localhost:8080/App/pi)

**Código de Ejemplo:**

```java
public static void main(String[] args) {
    staticfiles("/webroot");
    get("/hello", (req, resp) -> "Hello " + req.getValues("name"));
    get("/pi", (req, resp) -> {
        return String.valueOf(Math.PI);
    });
}
```

En este ejemplo, los servicios REST se publican con el prefijo `/App`. Este prefijo es solo una sugerencia y puede modificarse según las necesidades del desarrollador.

---

## Versionado

**Versión:** 2.0

---

## Autores

- **Andres Felipe Parra Quiroga**

---

## Detalles Técnicos

El sistema sigue una arquitectura basada en API REST. Se han aplicado diversos patrones de diseño para mejorar la estructura y eficiencia del código:

- **Gestión de caché:** Implementada con el patrón de diseño **Singleton**, asegurando una única instancia de caché en el servidor.
- **Extensibilidad:** Uso de una única clase `PagesServices` con el patrón **Singleton**, permitiendo el acceso a recursos almacenados en disco sin necesidad de un servicio individual por cada archivo.
- **Patrones de diseño:** Aplicación de **Fachada** y **Singleton** para mejorar la organización y reutilización del código.
- **Modularización:** Clases diseñadas bajo el principio de **responsabilidad única**, facilitando la escalabilidad y mantenimiento del código.

---


---

