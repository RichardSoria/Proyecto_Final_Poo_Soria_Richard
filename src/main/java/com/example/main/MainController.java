package com.example.main;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.bson.Document;
import com.mongodb.client.FindIterable;

public class MainController {
    @FXML
    private TextField campo_correo;
    @FXML
    private PasswordField campo_contrasena;
    @FXML
    private MenuButton menu_tipo_rol;

    private static final String EMAIL_PATTERN = "^[a-zA-Z]+\\.[a-zA-Z]+@epn\\.edu\\.ec$";

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new javafx.scene.image.Image(MainController.class.getResourceAsStream("/com/example/main/images/esfot_buho.png")));
        alert.showAndWait();
    }

    public void mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Agregar el icono de la aplicación a la ventana de alerta
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(MainController.class.getResourceAsStream("/com/example/main/images/esfot_buho.png")));

        // Crear ImageView con la imagen del check mark
        Image imagenVisto = new Image(MainController.class.getResourceAsStream("/com/example/main/images/check.png"));
        ImageView imageView = new ImageView(imagenVisto);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        // Añadir el ImageView a la alerta
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    public boolean validarCorreo(String correo) {
        return correo.matches(EMAIL_PATTERN);
    }

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
        String tipo_rol = menu_tipo_rol.getText();


        if(correo.isEmpty() || contrasena.isEmpty() || tipo_rol.equals("Tipo de Usuario")) {
            mostrarAlerta("Error al iniciar sesión", "Por favor, llene todos los campos y seleccione un tipo de usuario");
            return;
        } else if (!validarCorreo(correo)) {
            mostrarAlerta("Error al iniciar sesión", "Correo inválido");
            return;

        } else if (tipo_rol.equals("Administrador")) {
            try (MongoClient mongoClient = MongoClients.create("mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/")) {
                MongoDatabase database = mongoClient.getDatabase("Base_Datos_Aulas_Laboratorios_ESFOT");
                MongoCollection<Document> collection = database.getCollection("Administradores");

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", contrasena));
                if (documents.first() != null) {
                    mostrarConfirmacion("Inicio de sesión exitoso", "Bienvenido " + documents.first().get("nombre") + " " + documents.first().get("apellido") +
                            "\nHa iniciado sesión como Administrador");
                } else {
                    mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
                }
            } catch (Exception e) {
                mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
            }

        } else if (tipo_rol.equals("Profesor")) {
            try (MongoClient mongoClient = MongoClients.create("mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/")) {
                MongoDatabase database = mongoClient.getDatabase("Base_Datos_Aulas_Laboratorios_ESFOT");
                MongoCollection<Document> collection = database.getCollection("Profesores");

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", contrasena));
                if (documents.first() != null) {
                    mostrarConfirmacion("Inicio de sesión exitoso", "Bienvenido " + documents.first().get("nombre") + " " + documents.first().get("apellido") +
                            "\nHa iniciado sesión como Profesor");
                } else {
                    mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
                }
            } catch (Exception e) {
                mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
            }
        } else if (tipo_rol.equals("Estudiante")) {
            try (MongoClient mongoClient = MongoClients.create("mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/")) {
                MongoDatabase database = mongoClient.getDatabase("Base_Datos_Aulas_Laboratorios_ESFOT");
                MongoCollection<Document> collection = database.getCollection("Estudiantes");

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", contrasena));
                if (documents.first() != null) {
                    mostrarConfirmacion("Inicio de sesión exitoso", "Bienvenido " + documents.first().get("nombre") + " " + documents.first().get("apellido") +
                            "\nHa iniciado sesión como Estudiante");
                } else {
                    mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
                }
            } catch (Exception e) {
                mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
            }

        }
    }
}