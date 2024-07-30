package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/dashboard_administrador_view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setTitle("Gestión de Aulas y Laboratorios ESFOT");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}