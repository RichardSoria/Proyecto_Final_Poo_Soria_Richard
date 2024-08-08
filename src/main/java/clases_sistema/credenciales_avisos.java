package clases_sistema;


/**
 * La clase credenciales_avisos contiene métodos para validar credenciales de usuarios y mostrar mensajes de alerta.
 * Permite validar correos electrónicos, generar hash de contraseñas, validar campos numéricos y mostrar alertas.
 * @autor Richard Soria
 */

// Importaciones necesarias
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import controllers.IniciarSesionController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class credenciales_avisos {

    /**
     * Validar correo electrónico.
     * Método que permite validar un correo electrónico con una expresión regular.
     * */
    // Validar correo electrónico
    private static final String EMAIL_PATTERN = "^[a-zA-Z]+\\.[a-zA-Z]+@epn\\.edu\\.ec$";
    public boolean validarCorreo(String correo) {
        return correo.matches(EMAIL_PATTERN);
    }

    /**
     * Generar hash de contraseña.
     * Método que permite generar un hash de una contraseña utilizando el algoritmo SHA-256.
     * */
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

    /**
     * Coversión de bytes a hexadecimal.
     * Método que permite convertir un arreglo de bytes a una cadena de texto en hexadecimal.
     * */
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

    /**
     * validar campo numérico.
     * Método que permite validar un campo numérico con una expresión regular.
     * */
    // Validar campo numérico
    public boolean campoNumerico(String texto) {
        return texto.matches("\\d+");
    }

    /**
     * Mostrar alerta.
     * Método que permite mostrar una alerta con un mensaje de error.
     * */
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new javafx.scene.image.Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
        alert.showAndWait();
    }

    /**
     * Usuario existe.
     * Método que permite verificar si un usuario existe en la base de datos.
     * */
    public boolean usuarioExiste(String cedula) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            String[] collections = {"Administradores", "Profesores", "Estudiantes"};

            for (String collectionName : collections) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                Document query = new Document("cedula", cedula);
                if (collection.find(query).first() != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return false;
    }

    /**
     * Usuario existe en colección.
     * Método que permite verificar si un usuario existe en una colección específica de la base de datos.
     * */
    public boolean usuarioExisteCollection(String cedula, String campo_tipo_rol) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        switch (campo_tipo_rol) {
            case "Administrador":
                collectionName = "Administradores";
                break;
            case "Profesor":
                collectionName = "Profesores";
                break;
            case "Estudiante":
                collectionName = "Estudiantes";
                break;
        }

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("cedula", cedula);
            if (collection.find(query).first() != null) {
                return true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return false;
    }

    /**
     * Usuario existe en colección por correo.
     * Método que permite verificar si un usuario existe en una colección específica de la base de datos por su correo electrónico.
     * */
    public boolean usuarioExisteCollectionCorreo(String correo, String campo_tipo_rol) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        switch (campo_tipo_rol) {
            case "Administrador":
                collectionName = "Administradores";
                break;
            case "Profesor":
                collectionName = "Profesores";
                break;
            case "Estudiante":
                collectionName = "Estudiantes";
                break;
        }

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("correo", correo);
            if (collection.find(query).first() != null) {
                return true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return false;
    }

    /**
     * Usuario existe en colección por número de celular.
     * Método que permite verificar si un usuario existe en una colección específica de la base de datos por su número de celular.
     * */
    public boolean usuarioExisteCollectionNumeroCelular(String numeroCelular, String campo_tipo_rol) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        switch (campo_tipo_rol) {
            case "Administrador":
                collectionName = "Administradores";
                break;
            case "Profesor":
                collectionName = "Profesores";
                break;
            case "Estudiante":
                collectionName = "Estudiantes";
                break;
        }

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("numero_celular", numeroCelular);
            if (collection.find(query).first() != null) {
                return true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtener nombre y apellido de usuario.
     * Método que permite obtener el nombre y apellido de un usuario en la base de datos.
     * */
    public String obtenerNombreApellidoUsuario(String cedula, String campo_tipo_rol) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";
        String nombreApellidoUsuario = null;

        switch (campo_tipo_rol) {
            case "Administrador":
                collectionName = "Administradores";
                break;
            case "Profesor":
                collectionName = "Profesores";
                break;
            case "Estudiante":
                collectionName = "Estudiantes";
                break;
        }

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("cedula", cedula);
            Document usuario = collection.find(query).first();
            if (usuario != null) {
                nombreApellidoUsuario = usuario.getString("nombre") + " " + usuario.getString("apellido");
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return nombreApellidoUsuario;
    }

    /**
     * Mostrar nombre y apellido de usuario.
     * Método que permite mostrar el nombre y apellido de un usuario en la base de datos.
     * */
    public String mostrarNombreApellidoUsuario(String cedula) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String nombreApellidoUsuario = null;

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            String[] collections = {"Administradores", "Profesores", "Estudiantes"};

            for (String collectionName : collections) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                Document query = new Document("cedula", cedula);
                Document usuario = collection.find(query).first();
                if (usuario != null) {
                    nombreApellidoUsuario = usuario.getString("nombre") + " " + usuario.getString("apellido");
                    break;
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }
        return nombreApellidoUsuario;
    }

    /**
     * Obtener correo de usuario.
     * Método que permite obtener el correo de un usuario en la base de datos.
     * */
    public String obtenerCorreoUsuario(String cedula){
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String correoUsuario = null;

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            String[] collections = {"Administradores", "Profesores", "Estudiantes"};

            for (String collectionName : collections) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                Document query = new Document("cedula", cedula);
                Document usuario = collection.find(query).first();
                if (usuario != null) {
                    correoUsuario = usuario.getString("correo");
                    break;
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar usuario", "Error al consultar la base de datos: " + e.getMessage());
        }

        return correoUsuario;
    }

    /**
     * Comprobar reserva laboratorios.
     * Método que permite comprobar si una reserva de laboratorios ya existe en la base de datos.
     * */
    public Boolean comprobarReservaLaboratorios(String cedula, String fecha, String horario) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        Boolean reservaExiste = false;

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("Reservas_Laboratorios");
            Document query = new Document("cedula", cedula)
                    .append("fecha", fecha)
                    .append("horario", horario);
            if (collection.find(query).first() != null) {
                reservaExiste = true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar reserva", "Error al consultar la base de datos: " + e.getMessage());
        }
        return reservaExiste;
    }

    /**
     * Comprobar reserva aulas.
     * Método que permite comprobar si una reserva de aulas ya existe en la base de datos.
     * */
    public Boolean comprobarReservaAulas(String cedula, String fecha, String horario) {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        Boolean reservaExiste = false;

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("Reservas_Aulas");
            Document query = new Document("cedula", cedula)
                    .append("fecha", fecha)
                    .append("horario", horario);
            if (collection.find(query).first() != null) {
                reservaExiste = true;
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar reserva", "Error al consultar la base de datos: " + e.getMessage());
        }
        return reservaExiste;
    }

    /**
     * Mostrar confirmación.
     * Método que permite mostrar una alerta de confirmación con un mensaje de éxito.
     * */
    public void mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Agregar el icono de la aplicación a la ventana de alerta
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));

        // Crear ImageView con la imagen del check mark
        Image imagenVisto = new Image(IniciarSesionController.class.getResourceAsStream("/images/check.png"));
        ImageView imageView = new ImageView(imagenVisto);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        // Añadir el ImageView a la alerta
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
}
