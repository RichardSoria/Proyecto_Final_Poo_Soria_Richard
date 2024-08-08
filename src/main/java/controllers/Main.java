package controllers;

// Importaciones necesarias para JavaFX
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// Clase principal que extiende Application para crear una aplicación JavaFX
public class Main extends Application {

    // Método que se ejecuta al iniciar la aplicación
    @Override
    public void start(Stage stage) throws IOException {
        // Carga el diseño de la interfaz de usuario desde un archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/iniciar_sesion_view.fxml"));
        Parent root = fxmlLoader.load();

        // Crea una escena con el diseño cargado
        Scene scene = new Scene(root);
        root.requestFocus();  // Solicita el enfoque para la interfaz de usuario

        // Establece el título de la ventana de la aplicación
        stage.setTitle("Gestión de Aulas y Laboratorios ESFOT");

        // Añade un icono a la ventana de la aplicación
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));

        // Establece que la ventana no se puede redimensionar
        stage.setResizable(false);

        // Configura la escena en la ventana de la aplicación y la muestra
        stage.setScene(scene);
        stage.show();
    }

    // Método principal que lanza la aplicación
    public static void main(String[] args) {
        launch();  // Inicia la aplicación JavaFX
    }
}
