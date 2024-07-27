package com.example.main;

import com.example.main.clases_sistema.credenciales_avisos;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;


public class MainController extends credenciales_avisos {
    @FXML
    private TextField campo_correo;
    @FXML
    private PasswordField campo_contrasena;
    @FXML
    private MenuButton menu_tipo_rol;

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

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", hashedContrasena));
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

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", hashedContrasena));
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

                FindIterable<Document> documents = collection.find(new Document("correo", correo).append("contrasena", hashedContrasena));
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