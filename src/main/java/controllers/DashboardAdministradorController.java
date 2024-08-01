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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private FilteredList<nuevo_usuario> filteredData;

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
        filteredData = new FilteredList<>(listaUsuarios, e -> true);

        tabla_mostrar_usuarios.setItems(filteredData);

        // Añadir event handler para cada MenuItem en modo_bases_datos_usuarios
        for (MenuItem item : modo_bases_datos_usuarios.getItems()) {
            item.setOnAction(e -> {
                modo_bases_datos_usuarios.setText(item.getText());
                cargarUsuarios(); // Recargar usuarios cuando se seleccione una nueva base de datos
            });
        }

        for (MenuItem item : campo_rol_usuario.getItems()) {
            item.setOnAction(e -> campo_rol_usuario.setText(item.getText()));
        }

        campo_buscar_usuario.textProperty().addListener((observable, oldValue, newValue) -> buscarUsuario());

        SortedList<nuevo_usuario> listaOrdenada = new SortedList<>(filteredData);
        listaOrdenada.comparatorProperty().bind(tabla_mostrar_usuarios.comparatorProperty());
        tabla_mostrar_usuarios.setItems(listaOrdenada);
    }

    public void buscarUsuario() {
        String filter = campo_buscar_usuario.getText();
        filteredData.setPredicate(usuario -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filter.toLowerCase();
            return usuario.getCedula().toLowerCase().contains(lowerCaseFilter) ||
                    usuario.getNombre().toLowerCase().contains(lowerCaseFilter) ||
                    usuario.getApellido().toLowerCase().contains(lowerCaseFilter) ||
                    usuario.getCorreo().toLowerCase().contains(lowerCaseFilter) ||
                    usuario.getNumero_celular().toLowerCase().contains(lowerCaseFilter) ||
                    usuario.getTipo_rol().toLowerCase().contains(lowerCaseFilter);
        });
    }

    public void cargarUsuarios() {
        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "";

        switch (modo_bases_datos_usuarios.getText()) {
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

    public void seleccionarUsuario() {
        nuevo_usuario usuarioSeleccionado = tabla_mostrar_usuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            campo_cedula.setText(usuarioSeleccionado.getCedula());
            campo_nombre.setText(usuarioSeleccionado.getNombre());
            campo_apellido.setText(usuarioSeleccionado.getApellido());
            campo_correo.setText(usuarioSeleccionado.getCorreo());
            campo_contrasena.setText(usuarioSeleccionado.getContrasena());
            campo_numero_celular.setText(usuarioSeleccionado.getNumero_celular());
            campo_rol_usuario.setText(usuarioSeleccionado.getTipo_rol());
        }
    }

    public void botonAnadirUsuario() {
        if (campo_cedula.getText().isEmpty() || campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_correo.getText().isEmpty() || campo_contrasena.getText().isEmpty() || campo_numero_celular.getText().isEmpty() || campo_rol_usuario.getText().equals("Tipo de Usuario")) {
            mostrarAlerta("Error al añadir usuario", "Por favor, llene todos los campos y seleccione un tipo de usuario");
        } else if (!validarCorreo(campo_correo.getText())) {
            mostrarAlerta("Error al añadir usuario", "Correo Institucional inválido");

        } else if (campo_cedula.getText().length() != 10) {
            mostrarAlerta("Error al añadir usuario", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula.getText())) {
            mostrarAlerta("Error al añadir usuario", "Cédula inválida");

        } else if (campo_numero_celular.getText().length() != 10) {
            mostrarAlerta("Error al añadir usuario", "Número de celular inválido");

        } else if (!campoNumerico(campo_numero_celular.getText())) {
            mostrarAlerta("Error al añadir usuario", "Número de celular inválido");

        } else if (campo_contrasena.getText().length() < 8) {
            mostrarAlerta("Error al añadir usuario", "La contraseña debe tener al menos 8 caracteres");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Añadir Usuario");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea añadir este usuario?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "";

                switch (campo_rol_usuario.getText()) {
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
                        mostrarAlerta("Error al cargar usuarios", "Modo de base de datos no válido");
                        return;
                }

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("cedula", campo_cedula.getText());
                    Document doc = new Document("cedula", campo_cedula.getText())
                            .append("nombre", campo_nombre.getText())
                            .append("apellido", campo_apellido.getText())
                            .append("correo", campo_correo.getText())
                            .append("contrasena", generateHash(campo_contrasena.getText()))
                            .append("numero_celular", campo_numero_celular.getText())
                            .append("tipo_rol", campo_rol_usuario.getText());
                    collection.insertOne(doc);
                    mostrarConfirmacion("Usuario añadido", "El usuario ha sido añadido exitosamente");
                    botonLimpiarCampos();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al añadir usuario", "Error al añadir usuario a la base de datos: el usuario ya se encuentra registrado.");
                }
            }
        }
    }

    public void botonAcualizarUsuario() {
        if (campo_cedula.getText().isEmpty() || campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_correo.getText().isEmpty() || campo_contrasena.getText().isEmpty() || campo_numero_celular.getText().isEmpty() || campo_rol_usuario.getText().equals("Tipo de Usuario")) {
            mostrarAlerta("Error al añadir usuario", "Por favor, llene todos los campos y seleccione un tipo de usuario");
        } else if (!validarCorreo(campo_correo.getText())) {
            mostrarAlerta("Error al añadir usuario", "Correo Institucional inválido");

        } else if (campo_cedula.getText().length() != 10) {
            mostrarAlerta("Error al añadir usuario", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula.getText())) {
            mostrarAlerta("Error al añadir usuario", "Cédula inválida");

        } else if (campo_numero_celular.getText().length() != 10) {
            mostrarAlerta("Error al añadir usuario", "Número de celular inválido");

        } else if (!campoNumerico(campo_numero_celular.getText())) {
            mostrarAlerta("Error al añadir usuario", "Número de celular inválido");

        } else if (campo_contrasena.getText().length() < 8) {
            mostrarAlerta("Error al añadir usuario", "La contraseña debe tener al menos 8 caracteres");

        } else if (campo_cedula.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getCedula())
                && campo_nombre.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getNombre())
                && campo_apellido.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getApellido())
                && campo_correo.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getCorreo())
                && campo_contrasena.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getContrasena())
                && campo_numero_celular.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getNumero_celular())
                && campo_rol_usuario.getText().equals(tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getTipo_rol())) {
            mostrarAlerta("Error al actualizar usuario", "No se ha realizado ningún cambio en los campos");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Actualizar Usuario");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea actualizar este usuario?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "";

                switch (campo_rol_usuario.getText()) {
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
                        mostrarAlerta("Error al cargar usuarios", "Modo de base de datos no válido");
                        return;
                }

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("cedula", campo_cedula.getText());
                    Document doc = new Document("cedula", campo_cedula.getText())
                            .append("nombre", campo_nombre.getText())
                            .append("apellido", campo_apellido.getText())
                            .append("correo", campo_correo.getText())
                            .append("contrasena", generateHash(campo_contrasena.getText()))
                            .append("numero_celular", campo_numero_celular.getText())
                            .append("tipo_rol", campo_rol_usuario.getText());
                    collection.replaceOne(query, doc);
                    mostrarConfirmacion("Usuario actualizado", "El usuario ha sido actualizado exitosamente");
                    botonLimpiarCampos();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al actualizar usuario", "Error al actualizar usuario en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    public void botonEliminarUsuario() {
        if (campo_cedula.getText().isEmpty() || campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_correo.getText().isEmpty() || campo_contrasena.getText().isEmpty() || campo_numero_celular.getText().isEmpty() || campo_rol_usuario.getText().equals("Tipo de Usuario")) {
            mostrarAlerta("Error al eliminar usuario", "Por favor, seleccione un usuario a eliminar.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Usuario");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea eliminar este usuario?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "";

                switch (campo_rol_usuario.getText()) {
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
                        mostrarAlerta("Error al cargar usuarios", "Modo de base de datos no válido");
                        return;
                }

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("cedula", campo_cedula.getText());
                    collection.deleteOne(query);
                    mostrarConfirmacion("Usuario eliminado", "El usuario ha sido eliminado exitosamente");
                    botonLimpiarCampos();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar usuario", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    public void botonLimpiarCampos() {
        campo_cedula.clear();
        campo_nombre.clear();
        campo_apellido.clear();
        campo_correo.clear();
        campo_contrasena.clear();
        campo_numero_celular.clear();
        campo_rol_usuario.setText("Tipo de Usuario");
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
        Optional<ButtonType> opcionSalirSesion = alert.showAndWait();

        try {
            if (opcionSalirSesion.get() == ButtonType.OK) {
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
