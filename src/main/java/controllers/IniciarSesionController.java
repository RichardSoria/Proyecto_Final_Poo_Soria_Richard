package controllers;

/**
 * La clase IniciarSesionController es un controlador que gestiona la lógica de la vista de inicio de sesión.
 * Permite a los usuarios iniciar sesión en la aplicación y redirigirlos al dashboard correspondiente según su tipo de usuario.
 * Implementa la interfaz Initializable de JavaFX para inicializar los componentes de la interfaz de usuario.
 * @autor Richard Soria
 */

// Importaciones necesarias
import clases_sistema.credenciales_avisos;
import clases_sistema.usuarioConectado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.bson.Document;
import com.mongodb.client.FindIterable;

import java.io.IOException;

public class IniciarSesionController extends credenciales_avisos {

    // Definición de los elementos de la interfaz de usuario que permiten la entrada de datos, la selección de opciones y la ejecución de acciones.
    @FXML
    private TextField campo_correo;
    @FXML
    private PasswordField campo_contrasena;
    @FXML
    private MenuButton menu_tipo_rol;
    @FXML
    private Button boton_iniciar_sesion;

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido completamente procesado.
     * Se llama para inicializar un controlador después de que su elemento raíz haya sido procesado por completo.
     * */
    // Método que se llama al inicializar el controlador
    @FXML
    public void initialize() {
        // Agregar manejadores de eventos a los items del MenuButton para actualizar el texto
        for (MenuItem item : menu_tipo_rol.getItems()) {
            item.setOnAction(e -> menu_tipo_rol.setText(item.getText()));
        }
    }

    /**
     * Método que maneja el inicio de sesión de los usuarios.
     * Permite a los usuarios iniciar sesión en la aplicación y redirigirlos al dashboard correspondiente según su tipo de usuario.
     * */
    // Método que maneja el inicio de sesión
    @FXML
    public void iniciarSesion() {
        // Obtener los valores de los campos de texto y menú
        String correo = campo_correo.getText();
        String contrasena = campo_contrasena.getText();
        String hashedContrasena = generateHash(contrasena);  // Hashear la contraseña
        String tipo_rol = menu_tipo_rol.getText();

        // Variables para almacenar la información del usuario conectado
        String nombreUsuarioConectado = "";
        String correoUsuarioConectado = "";
        String cedulaUsuarioConectado = "";
        String tipoRolUsuarioConectado = "";

        // Validaciones iniciales
        if (correo.isEmpty() || contrasena.isEmpty() || tipo_rol.equals("Tipo de Usuario")) {
            mostrarAlerta("Error al iniciar sesión", "Por favor, llene todos los campos y seleccione un tipo de usuario");
            return;
        } else if (!validarCorreo(correo)) {
            mostrarAlerta("Error al iniciar sesión", "Correo inválido");
            return;
        }

        // URI de conexión a MongoDB
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        // Selección de la colección de la base de datos según el tipo de usuario
        switch (tipo_rol) {
            case "Administrador":
                collectionName = "Administradores";
                break;
            case "Profesor":
                collectionName = "Profesores";
                break;
            case "Estudiante":
                collectionName = "Estudiantes";
                break;
            default:
                mostrarAlerta("Error al iniciar sesión", "Tipo de usuario no válido");
                return;
        }

        // Conexión y consulta a la base de datos MongoDB
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Buscar documento que coincida con el correo y la contraseña hasheada
            FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", hashedContrasena));
            if (documents.first() != null) {
                // Mostrar confirmación de inicio de sesión exitoso
                mostrarConfirmacion("Inicio de sesión exitoso", "Bienvenido " + documents.first().get("nombre") + " " + documents.first().get("apellido") +
                        "\nHa iniciado sesión como " + tipo_rol);

                // Configurar la información del usuario conectado
                nombreUsuarioConectado = documents.first().get("nombre") + " " + documents.first().get("apellido");
                usuarioConectado.getInstance().setNombreUsuarioConectado(nombreUsuarioConectado);

                correoUsuarioConectado = documents.first().get("correo").toString();
                usuarioConectado.getInstance().setCorreoUsuarioConectado(correoUsuarioConectado);

                cedulaUsuarioConectado = documents.first().get("cedula").toString();
                usuarioConectado.getInstance().setCedulaUsuarioConectado(cedulaUsuarioConectado);

                tipoRolUsuarioConectado = tipo_rol;
                usuarioConectado.getInstance().setTipoRolUsuarioConectado(tipoRolUsuarioConectado);

                // Cargar la vista del dashboard correspondiente según el tipo de usuario
                if (tipo_rol.equals("Administrador")) {
                    cargarVistaDashboard("/views/dashboard_administrador_view.fxml", "Dashboard Administrador");
                } else if (tipo_rol.equals("Profesor")) {
                    cargarVistaDashboard("/views/dashboard_profesor_view.fxml", "Dashboard Profesor");
                } else if (tipo_rol.equals("Estudiante")) {
                    cargarVistaDashboard("/views/dashboard_estudiante_view.fxml", "Dashboard Estudiante");
                }
            } else {
                mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
            }
        } catch (Exception e) {
            mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    /**
     * Cargar la vista del dashboard correspondiente según el tipo de usuario.
     * Permite cargar la vista del dashboard correspondiente según el tipo de usuario.
     * */
    // Método auxiliar para cargar la vista del dashboard
    private void cargarVistaDashboard(String fxmlPath, String titulo) throws IOException {
        boton_iniciar_sesion.getScene().getWindow().hide();  // Ocultar la ventana actual
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));  // Cargar la nueva vista
        Stage stage = new Stage();  // Crear una nueva ventana
        Scene scene = new Scene(root);  // Crear una nueva escena
        root.requestFocus();  // Solicitar enfoque
        stage.setTitle(titulo);  // Establecer el título de la ventana
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));  // Añadir el icono
        stage.setResizable(false);  // Establecer la ventana como no redimensionable
        stage.setScene(scene);  // Establecer la escena en la ventana
        stage.show();  // Mostrar la ventana
    }
}
