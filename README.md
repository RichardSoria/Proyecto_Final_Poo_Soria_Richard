
# Proyecto Final POO - Richard Soria

## Descripción del Proyecto

Este proyecto es una aplicación Java desarrollada utilizando JavaFX, Maven y el IDE IntelliJ IDEA. La aplicación permite a los usuarios iniciar sesión y acceder a diferentes opciones según su rol (profesor, estudiante, administrador).

## Instalación

### Prerrequisitos

1. **Java JDK 22**: Asegúrese de tener instalada la versión 22 de Java Development Kit.
2. **Maven**: El proyecto utiliza Maven para la gestión de dependencias.
3. **IntelliJ IDEA**: Se recomienda usar el IDE IntelliJ IDEA para facilitar la configuración y ejecución del proyecto.

### Pasos de Instalación

1. **Clonar el Repositorio desde GitHub**:
   - Navegue a la página del repositorio en GitHub.
   - Haga clic en el botón `Fork` para crear una copia del repositorio en su cuenta.
   - Luego, haga clic en el botón `Code` y seleccione `Open with IntelliJ IDEA` para clonar el repositorio directamente en su IDE.

2. **Importar el Proyecto en IntelliJ IDEA**:
   - Abra IntelliJ IDEA.
   - Seleccione `Get from VCS` y pegue la URL del repositorio clonado.
   - IntelliJ IDEA descargará y abrirá el proyecto automáticamente.

3. **Configurar SDK**:
   - Vaya a `File` > `Project Structure` > `Project`.
   - Configure el SDK del proyecto a Java JDK 22.

4. **Instalar Dependencias**:
   - Maven debería descargar automáticamente todas las dependencias necesarias cuando importe el proyecto. Si no es así, puede forzar la actualización de dependencias en IntelliJ IDEA:
     - Vaya al panel `Maven` en la barra lateral derecha.
     - Haga clic en el ícono de `Refresh` para actualizar las dependencias.

5. **Instalar Librerías de JavaFX**:
   - Asegúrese de que todas las librerías necesarias para JavaFX estén instaladas. Maven manejará estas dependencias automáticamente si están especificadas en el archivo `pom.xml`.

## Configuración

### Estructura del Proyecto

A continuación, se describe la estructura principal del proyecto y la función de cada directorio/archivo importante:

```
Proyecto_Final_Poo_Soria_Richard/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── clases_sistema/
│   │   │   │   ├── credenciales_avisos/
│   │   │   │   ├── enviar_correo/
│   │   │   │   ├── nuevo_usuario/
│   │   │   │   ├── reserva_aulas/
│   │   │   │   ├── reserva_laboratorios/
│   │   │   │   └── usuarioConectado/
│   │   │   └── controllers/
│   │   │       ├── DashboardAdministradorController.java
│   │   │       ├── DashboardEstudianteController.java
│   │   │       ├── DashboardProfesorController.java
│   │   │       ├── IniciarSesionController.java
│   │   │       └── Main.java
│   │   └── resources/
│   │       ├── images/
│   │       │   └── logo.png
│   │       ├── styles/
│   │       │   └── estilos.css
│   │       └── views/
│   │           ├── dashboard_administrador_view.fxml
│   │           ├── dashboard_estudiante_view.fxml
│   │           ├── dashboard_profesor_view.fxml
│   │           └── iniciar_sesion_view.fxml
├── pom.xml
└── README.md
```

- **`src/main/java/clases_sistema/credenciales_avisos/`**: Contiene las clases relacionadas con las credenciales y avisos.
- **`src/main/java/clases_sistema/enviar_correo/`**: Contiene las clases relacionadas con el envío de correos.
- **`src/main/java/clases_sistema/nuevo_usuario/`**: Contiene las clases para la gestión de nuevos usuarios.
- **`src/main/java/clases_sistema/reserva_aulas/`**: Contiene las clases para la reserva de aulas.
- **`src/main/java/clases_sistema/reserva_laboratorios/`**: Contiene las clases para la reserva de laboratorios.
- **`src/main/java/clases_sistema/usuarioConectado/`**: Contiene las clases relacionadas con los usuarios conectados.
- **`src/main/java/controllers/`**: Contiene las clases controladoras para manejar la lógica de negocio y la interacción con los usuarios a través de las vistas JavaFX.
- **`src/main/java/controllers/Main.java`**: Clase principal que inicia la aplicación.
- **`src/main/resources/views/`**: Contiene los archivos FXML que definen las interfaces de usuario de JavaFX.
- **`src/main/resources/css/`**: Contiene los archivos CSS para los estilos de la aplicación.
- **`src/main/resources/images/`**: Contiene las imágenes utilizadas en la aplicación.
- **`pom.xml`**: Archivo de configuración de Maven.

### Configuración del Proyecto

1. **Configurar Controladores**:
   - Los controladores se encuentran en `src/main/java/controllers/` y están vinculados a las vistas correspondientes en `src/main/resources/views/`.

2. **Configurar Vistas**:
   - Los archivos FXML para las vistas están en `src/main/resources/views/`. Asegúrese de que las rutas y nombres de archivos coincidan con los especificados en los controladores.

3. **Configurar Recursos**:
   - Las imágenes y archivos CSS deben estar ubicados en `src/main/resources/images/` y `src/main/resources/css/` respectivamente.

## Ejecución

Para ejecutar la aplicación:

1. **Ejecutar la Clase Principal**:
   - En IntelliJ IDEA, navegue a `src/main/java/controllers/Main.java`.
   - Haga clic derecho en `Main.java` y seleccione `Run 'Main'`.

2. **Proceso de Inicio de Sesión**:
   - La aplicación iniciará y mostrará la ventana de inicio de sesión definida en `iniciar_sesion_view.fxml`.
   - Dependiendo del rol seleccionado (profesor, estudiante, administrador), el usuario será redirigido al dashboard correspondiente.

---

## Enlace Manual de Guía de Usuario para el Sistema de Gestión de Aulas y Laboratorios ESFOT ##
https://youtu.be/kshBIiQVDvM

Este README proporciona una guía detallada sobre cómo instalar, configurar y ejecutar el proyecto. Si hay algún detalle adicional o específico que desees incluir, házmelo saber.