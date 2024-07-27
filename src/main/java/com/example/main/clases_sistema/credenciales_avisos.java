package com.example.main.clases_sistema;

import com.example.main.MainController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class credenciales_avisos {

    // Validar correo electrónico
    private static final String EMAIL_PATTERN = "^[a-zA-Z]+\\.[a-zA-Z]+@epn\\.edu\\.ec$";
    public boolean validarCorreo(String correo) {
        return correo.matches(EMAIL_PATTERN);
    }

    // Generar hash de la contraseña
    public static String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Convertir bytes a hexadecimal
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void mostrarAlerta(String titulo, String mensaje) {
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
}
