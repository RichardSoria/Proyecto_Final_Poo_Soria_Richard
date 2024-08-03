package controllers;

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

public class IniciarSesionController extends credenciales_avisos {
    @FXML
    private TextField campo_correo;
    @FXML
    private PasswordField campo_contrasena;
    @FXML
    private MenuButton menu_tipo_rol;
    @FXML
    private Button boton_iniciar_sesion;



    @FXML
    public void initialize() {
        // Agregar manejadores de eventos a los items del MenuButton
        for (MenuItem item : menu_tipo_rol.getItems()) {
            item.setOnAction(e -> menu_tipo_rol.setText(item.getText()));
        }
    }

    @FXML
    public void iniciarSesion() {
        String correo = campo_correo.getText();
        String contrasena = campo_contrasena.getText();
        String hashedContrasena = generateHash(contrasena);
        String tipo_rol = menu_tipo_rol.getText();

        String nombreUsuarioConectado = "";
        String correoUsuarioConectado = "";
        String cedulaUsuarioConectado = "";
        String tipoRolUsuarioConectado = "";

        if (correo.isEmpty() || contrasena.isEmpty() || tipo_rol.equals("Tipo de Usuario")) {
            mostrarAlerta("Error al iniciar sesión", "Por favor, llene todos los campos y seleccione un tipo de usuario");
            return;
        } else if (!validarCorreo(correo)) {
            mostrarAlerta("Error al iniciar sesión", "Correo inválido");
            return;
        }

        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

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

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", hashedContrasena));
            if (documents.first() != null) {
                mostrarConfirmacion("Inicio de sesión exitoso", "Bienvenido " + documents.first().get("nombre") + " " + documents.first().get("apellido") +
                        "\nHa iniciado sesión como " + tipo_rol);

                nombreUsuarioConectado = documents.first().get("nombre") + " " + documents.first().get("apellido");
                usuarioConectado.getInstance().setNombreUsuarioConectado(nombreUsuarioConectado);

                correoUsuarioConectado = documents.first().get("correo").toString();
                usuarioConectado.getInstance().setCorreoUsuarioConectado(correoUsuarioConectado);

                cedulaUsuarioConectado = documents.first().get("cedula").toString();
                usuarioConectado.getInstance().setCedulaUsuarioConectado(cedulaUsuarioConectado);

                tipoRolUsuarioConectado = tipo_rol;
                usuarioConectado.getInstance().setTipoRolUsuarioConectado(tipoRolUsuarioConectado);

                if (tipo_rol.equals("Administrador")) {
                    boton_iniciar_sesion.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard_administrador_view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    root.requestFocus();
                    stage.setTitle("Dashboard Administrador");
                    stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                } else if (tipo_rol.equals("Profesor")) {
                    boton_iniciar_sesion.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard_profesor_view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    root.requestFocus();
                    stage.setTitle("Dashboard Profesor");
                    stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                } else if (tipo_rol.equals("Estudiante")) {
                    boton_iniciar_sesion.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/views/dashboard_estudiante_view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    root.requestFocus();
                    stage.setTitle("Dashboard Estudiante");
                    stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
            }
        } catch (Exception e) {
            mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
        }
    }
}
