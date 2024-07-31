package controllers;

import clases_sistema.credenciales_avisos;
import clases_sistema.nuevo_usuario;
import clases_sistema.usuarioConectado;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import controllers.IniciarSesionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardAdministradorController extends credenciales_avisos implements Initializable {

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
    private TableColumn<nuevo_usuario, String> columna_apellido;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_aula;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_aula_cedula;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_aula_fecha;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_aula_horario;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_cedula;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_correo;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_laboratorio;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_laboratorio_cedula;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_laboratorio_fecha;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_laboratorio_horario;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_nombre;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_numero_celular;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_tipo_usuario;

    @FXML
    private TableColumn<nuevo_usuario, String> columna_contrasena;

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
    private TableView<nuevo_usuario> tabla_mostrar_usuarios;

    @FXML
    private TableView<?> tabla_mostrar_reservas_aulas;

    @FXML
    private TableView<?> tabla_reservas_laboratorios_mostrar;

    private ObservableList<nuevo_usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nombreUsuarioConectado = usuarioConectado.getInstance().getNombreUsuarioConectado();
        nombre_usuario_conectado.setText(nombreUsuarioConectado);

        columna_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columna_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columna_contrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        columna_numero_celular.setCellValueFactory(new PropertyValueFactory<>("numero_celular"));
        columna_tipo_usuario.setCellValueFactory(new PropertyValueFactory<>("tipo_rol"));

        listaUsuarios = FXCollections.observableArrayList();
        tabla_mostrar_usuarios.setItems(listaUsuarios);

        // Añadir event handler para cada MenuItem en modo_bases_datos_usuarios
        for (MenuItem item : modo_bases_datos_usuarios.getItems()) {
            item.setOnAction(e -> {modo_bases_datos_usuarios.setText(item.getText());
                cargarUsuarios(); // Recargar usuarios cuando se seleccione una nueva base de datos
            });
        }
    }

    private void cargarUsuarios() {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        switch (modo_bases_datos_usuarios.getText())
        {
            case "Administradores":
                collectionName = "Administradores";
                break;
            case "Profesores":
                collectionName = "Profesores";
                break;
            case "Estudiantes":
                collectionName = "Estudiantes";
                break;
            default:
                mostrarAlerta("Error al cargar usuarios", "Modo de base de datos no válido");
                return;
        }

        listaUsuarios.clear(); // Limpiar la lista actual antes de cargar nuevos usuarios

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            for (Document doc : collection.find()) {
                nuevo_usuario usuario = new nuevo_usuario(
                        doc.getString("cedula"),
                        doc.getString("nombre"),
                        doc.getString("apellido"),
                        doc.getString("correo"),
                        doc.getString("contrasena"),
                        doc.getString("numero_celular"),
                        doc.getString("tipo_rol")
                );
                listaUsuarios.add(usuario);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al cargar usuarios", "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    public void moduloBotonUsuario() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void moduloBotonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void moduloBotonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
    }

    public void botonModulos() {
        dashboard_modulos.setVisible(true);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonUsuarios() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
    }

    public void botonLabs() {
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

        try {
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
}
