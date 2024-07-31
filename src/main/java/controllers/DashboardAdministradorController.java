package controllers;

import clases_sistema.credenciales_avisos;
import clases_sistema.nuevo_usuario;
import clases_sistema.usuarioConectado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardAdministradorController extends credenciales_avisos implements Initializable {
    private nuevo_usuario nuevo_usuario = new nuevo_usuario();

    @FXML
    private Button boton_actualizar_aula;

    @FXML
    private Button boton_actualizar_laboratorio;

    @FXML
    private Button boton_actualizar_usuario;

    @FXML
    private Button boton_anadir_usuario;

    @FXML
    private Button boton_aulas;

    @FXML
    private Button boton_eliminar_aula;

    @FXML
    private Button boton_eliminar_laboratorio;

    @FXML
    private Button boton_eliminar_usuario;

    @FXML
    private Button boton_labs;

    @FXML
    private Button boton_limpiar_campo_reserva_aula;

    @FXML
    private Button boton_limpiar_campos;

    @FXML
    private Button boton_limpiar_campos_laboratorio;

    @FXML
    private Button boton_modulos;

    @FXML
    private Button boton_reservar_aula;

    @FXML
    private Button boton_reservar_laboratorio;

    @FXML
    private Button boton_salir_sesion;

    @FXML
    private Button boton_usuarios;

    @FXML
    private TextField campo_apellido;

    @FXML
    private TextField campo_buscar_aula;

    @FXML
    private TextField campo_buscar_laboratorio;

    @FXML
    private TextField campo_buscar_usuario;

    @FXML
    private TextField campo_cedula;

    @FXML
    private TextField campo_cedula_reserva_aula;

    @FXML
    private PasswordField campo_contrasena;

    @FXML
    private TextField campo_correo;

    @FXML
    private TextField campo_nombre;

    @FXML
    private TextField campo_numero_celular;

    @FXML
    private TextField campo_reserva_usuario_laboratorio;

    @FXML
    private MenuButton campo_rol_usuario;

    @FXML
    private MenuButton campo_seleccionar_aula;

    @FXML
    private DatePicker campo_seleccionar_fecha_aula;

    @FXML
    private DatePicker campo_seleccionar_fecha_laboratorios;

    @FXML
    private MenuButton campo_seleccionar_horario_aula;

    @FXML
    private MenuButton campo_seleccionar_horario_laboratorio;

    @FXML
    private MenuButton campo_seleccionar_laboratorios;

    @FXML
    private TableColumn<?, ?> columna_apellido;

    @FXML
    private TableColumn<?, ?> columna_aula;

    @FXML
    private TableColumn<?, ?> columna_aula_cedula;

    @FXML
    private TableColumn<?, ?> columna_aula_fecha;

    @FXML
    private TableColumn<?, ?> columna_aula_horario;

    @FXML
    private TableColumn<?, ?> columna_cedula;

    @FXML
    private TableColumn<?, ?> columna_correo;

    @FXML
    private TableColumn<?, ?> columna_laboratorio;

    @FXML
    private TableColumn<?, ?> columna_laboratorio_cedula;

    @FXML
    private TableColumn<?, ?> columna_laboratorio_fecha;

    @FXML
    private TableColumn<?, ?> columna_laboratorio_horario;

    @FXML
    private TableColumn<?, ?> columna_nombre;

    @FXML
    private TableColumn<?, ?> columna_numero_celular;

    @FXML
    private TableColumn<?, ?> columna_tipo_usuario;

    @FXML
    private TableColumn<?, ?> cplumna_contrasena;

    @FXML
    private AnchorPane dashboard_gestion_usuarios;

    @FXML
    private AnchorPane dashboard_modulos;

    @FXML
    private AnchorPane dashboard_reservar_aulas;

    @FXML
    private AnchorPane dashboard_reservar_laboratorios;

    @FXML
    private MenuButton modo_bases_datos_usuarios;

    @FXML
    private Button modulo_boton_aulas;

    @FXML
    private Button modulo_boton_labs;

    @FXML
    private Button modulo_boton_usuario;

    @FXML
    private Label nombre_usuario_conectado;

    @FXML
    private TableView<?> tabla_mostrar_reservas_aulas;

    @FXML
    private TableView<?> tabla_mostrar_usuarios;

    @FXML
    private TableView<?> tabla_reservas_laboratorios_mostrar;


    public void moduloBotonUsuario()
    {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void moduloBotonAulas()
    {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void moduloBotonLabs()
    {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void botonModulos()
    {
        dashboard_modulos.setVisible(true);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonUsuarios()
    {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonAulas()
    {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonLabs()
    {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
    }

    public void salirInicioSesion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea cerrar sesión?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
        Optional<ButtonType> opcion = alert.showAndWait();

        try{
            if (opcion.get() == ButtonType.OK) {
                boton_salir_sesion.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/views/iniciar_sesion_view.fxml"));
                Scene scene = new Scene(root);
                root.requestFocus();
                stage.setTitle("Gestión de Aulas y Laboratorios ESFOT");
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/esfot_buho.png")));
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nombreUsuarioConectado = usuarioConectado.getInstance().getNombreUsuarioConectado();
        nombre_usuario_conectado.setText(nombreUsuarioConectado);
    }
}

/*
package controllers;

import clases_sistema.credenciales_avisos;
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
                }
            } else {
                mostrarAlerta("Error al iniciar sesión", "Correo, contraseña o tipo de usuario incorrectos");
            }
        } catch (Exception e) {
            mostrarAlerta("Error al iniciar sesión", "Error al consultar la base de datos: " + e.getMessage());
        }
    }
}
*/