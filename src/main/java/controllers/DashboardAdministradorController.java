package controllers;

import clases_sistema.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
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
    private TextField campo_cedula_reserva_laboratorio;

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
    private TableView<reserva_aulas> tabla_mostrar_reservas_aulas;

    @FXML
    private TableView<reserva_laboratorios> tabla_reservas_laboratorios_mostrar;

    private ObservableList<nuevo_usuario> listaUsuarios;
    private FilteredList<nuevo_usuario> filteredData;

    private ObservableList<reserva_aulas> listaReservasAulas;
    private FilteredList<reserva_aulas> filteredDataReservasAulas;

    private ObservableList<reserva_laboratorios> listaReservasLaboratorios;
    private FilteredList<reserva_laboratorios> filteredDataReservasLaboratorios;

    String nombreApellidoUsuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Mostrar nombre de usuario conectado
        String nombreUsuarioConectado = usuarioConectado.getInstance().getNombreUsuarioConectado();
        nombre_usuario_conectado.setText(nombreUsuarioConectado);

        // Inicializar columnas de la tabla de usuarios
        columna_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        columna_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columna_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columna_contrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        columna_numero_celular.setCellValueFactory(new PropertyValueFactory<>("numero_celular"));
        columna_tipo_usuario.setCellValueFactory(new PropertyValueFactory<>("tipo_rol"));

        // Inicializar lista de usuarios y FilteredList
        listaUsuarios = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(listaUsuarios, e -> true);

        // Cargar usuarios por defecto
        tabla_mostrar_usuarios.setItems(filteredData);

        // Añadir event handler para cada MenuItem en modo_bases_datos_usuarios
        for (MenuItem item : modo_bases_datos_usuarios.getItems()) {
            item.setOnAction(e -> {
                modo_bases_datos_usuarios.setText(item.getText());
                cargarUsuarios(); // Recargar usuarios cuando se seleccione una nueva base de datos
            });
        }

        // Añadir event handler para cada MenuItem en campo_rol_usuario
        for (MenuItem item : campo_rol_usuario.getItems()) {
            item.setOnAction(e -> campo_rol_usuario.setText(item.getText()));
        }

        campo_buscar_usuario.textProperty().addListener((observable, oldValue, newValue) -> buscarUsuario());
        campo_buscar_aula.textProperty().addListener((observable, oldValue, newValue) -> buscarAula());

        // Cargar usuarios por defecto
        SortedList<nuevo_usuario> listaOrdenada = new SortedList<>(filteredData);
        listaOrdenada.comparatorProperty().bind(tabla_mostrar_usuarios.comparatorProperty());
        tabla_mostrar_usuarios.setItems(listaOrdenada);

        // Inicializar columnas de la tabla de reservas de aulas
        columna_aula.setCellValueFactory(new PropertyValueFactory<>("aula"));
        columna_aula_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columna_aula_horario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columna_aula_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        // Inicializar lista de reservas de aulas y FilteredList
        listaReservasAulas = FXCollections.observableArrayList();
        filteredDataReservasAulas = new FilteredList<>(listaReservasAulas, e -> true);

        // Cargar reservas de aulas por defecto
        tabla_mostrar_reservas_aulas.setItems(filteredDataReservasAulas);
        cargarReservasAulas();

        // Añadir event handler para cada MenuItem en campo_seleccionar_aula
        for (MenuItem item : campo_seleccionar_aula.getItems()) {
            item.setOnAction(e -> campo_seleccionar_aula.setText(item.getText()));
        }

        // Añadir event handler para cada MenuItem en campo_seleccionar_horario_aula
        for (MenuItem item : campo_seleccionar_horario_aula.getItems()) {
            item.setOnAction(e -> campo_seleccionar_horario_aula.setText(item.getText()));
        }

        // Inicializar columnas de la tabla de reservas de laboratorios
        columna_laboratorio.setCellValueFactory(new PropertyValueFactory<>("laboratorio"));
        columna_laboratorio_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columna_laboratorio_horario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columna_laboratorio_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));

        // Inicializar lista de reservas de laboratorios y FilteredList
        listaReservasLaboratorios = FXCollections.observableArrayList();
        filteredDataReservasLaboratorios = new FilteredList<>(listaReservasLaboratorios, e -> true);

        // Cargar reservas de laboratorios por defecto
        tabla_reservas_laboratorios_mostrar.setItems(filteredDataReservasLaboratorios);
        cargarReservasLaboratorios();

        // Añadir event handler para cada MenuItem en campo_seleccionar_laboratorios
        for (MenuItem item : campo_seleccionar_laboratorios.getItems()) {
            item.setOnAction(e -> campo_seleccionar_laboratorios.setText(item.getText()));
        }

        // Añadir event handler para cada MenuItem en campo_seleccionar_horario_laboratorio
        for (MenuItem item : campo_seleccionar_horario_laboratorio.getItems()) {
            item.setOnAction(e -> campo_seleccionar_horario_laboratorio.setText(item.getText()));
        }

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

        } else if (usuarioExiste(campo_cedula.getText())) {
            mostrarAlerta("Error al añadir usuario", "El usuario ya se encuentra registrado");

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

                    Document doc = new Document("cedula", campo_cedula.getText())
                            .append("nombre", campo_nombre.getText())
                            .append("apellido", campo_apellido.getText())
                            .append("correo", campo_correo.getText())
                            .append("contrasena", generateHash(campo_contrasena.getText()))
                            .append("numero_celular", campo_numero_celular.getText())
                            .append("tipo_rol", campo_rol_usuario.getText());
                    collection.insertOne(doc);
                    mostrarConfirmacion("Usuario añadido", "El usuario ha sido añadido exitosamente");
                    botonLimpiarCamposUsuarios();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al añadir usuario", "Error al añadir usuario en la base de datos: " + e.getMessage());
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

        } else if (usuarioExiste(campo_cedula.getText())) {
            mostrarAlerta("Error al añadir usuario", "El usuario ya se encuentra registrado");

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
                    botonLimpiarCamposUsuarios();
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
                    botonLimpiarCamposUsuarios();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar usuario", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    public void botonLimpiarCamposUsuarios() {
        campo_cedula.clear();
        campo_nombre.clear();
        campo_apellido.clear();
        campo_correo.clear();
        campo_contrasena.clear();
        campo_numero_celular.clear();
        campo_rol_usuario.setText("Tipo de Usuario");
    }

    // Buscar aula
    public void buscarAula() {
        String filter = campo_buscar_aula.getText();
        filteredDataReservasAulas.setPredicate(reserva -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filter.toLowerCase();
            return reserva.getAula().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getFecha().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getHorario().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getCedula().toLowerCase().contains(lowerCaseFilter);
        });
    }


    // Mostrar reserva de aulas
    public void cargarReservasAulas() {
        listaReservasAulas.clear(); // Limpiar la lista actual antes de cargar nuevas reservas

        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "Reservas_Aulas";

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            for (Document doc : collection.find()) {
                reserva_aulas reserva = new reserva_aulas(
                        doc.getString("aula"),
                        doc.getString("fecha"),
                        doc.getString("horario"),
                        doc.getString("cedula")
                );
                listaReservasAulas.add(reserva);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al cargar reservas de aulas", "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    // Seleccionar reserva de aulas
    public void seleccionarAulaReserva() {
        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            campo_seleccionar_aula.setText(reservaSeleccionada.getAula());
            campo_seleccionar_fecha_aula.setValue(java.time.LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_aula.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_aula.setText(reservaSeleccionada.getCedula());
        }
    }

    public void botonRealizarReservaAula() {
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_aula.getText());

        if (campo_seleccionar_aula.getText().isEmpty() || campo_seleccionar_fecha_aula.getValue() == null || campo_seleccionar_horario_aula.getText().isEmpty() || campo_cedula_reserva_aula.getText().isEmpty()) {
            mostrarAlerta("Error al realizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_aula.getText().length() != 10) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getAula().equals(campo_seleccionar_aula.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()))) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado esta aula en la misma fecha y horario");

        } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_aula.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()))) {

            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (comprobarReservaLaboratorios(campo_cedula_reserva_aula.getText(), campo_seleccionar_fecha_aula.getValue().toString(), campo_seleccionar_horario_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Realizar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea realizar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Aulas";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document doc = new Document("aula", campo_seleccionar_aula.getText())
                            .append("fecha", campo_seleccionar_fecha_aula.getValue().toString())
                            .append("horario", campo_seleccionar_horario_aula.getText())
                            .append("cedula", campo_cedula_reserva_aula.getText());
                    collection.insertOne(doc);
                    mostrarConfirmacion("Reserva realizada", "La reserva ha sido realizada exitosamente");
                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al realizar reserva", "Error al realizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }


    public void botonActualizarReservaAula() {
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_aula.getText());

        if (campo_seleccionar_aula.getText().isEmpty() || campo_seleccionar_fecha_aula.getValue() == null || campo_seleccionar_horario_aula.getText().isEmpty() || campo_cedula_reserva_aula.getText().isEmpty()) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_aula.getText().length() != 10) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getAula().equals(campo_seleccionar_aula.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText())) && !campo_seleccionar_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getAula())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado esta aula en la misma fecha y horario");

        } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_aula.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()) && !campo_cedula_reserva_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getCedula()))) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (comprobarReservaLaboratorios(campo_cedula_reserva_aula.getText(), campo_seleccionar_fecha_aula.getValue().toString(), campo_seleccionar_horario_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else if (campo_seleccionar_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getAula())
                && campo_seleccionar_fecha_aula.getValue().toString().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getFecha())
                && campo_seleccionar_horario_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getHorario())
                && campo_cedula_reserva_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al actualizar reserva", "No se ha realizado ningún cambio en los campos");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Actualizar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea actualizar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Aulas";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("aula", tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getAula())
                            .append("fecha", tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getFecha())
                            .append("horario", tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getHorario())
                            .append("cedula", tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getCedula());

                    Document doc = new Document("aula", campo_seleccionar_aula.getText())
                            .append("fecha", campo_seleccionar_fecha_aula.getValue().toString())
                            .append("horario", campo_seleccionar_horario_aula.getText())
                            .append("cedula", campo_cedula_reserva_aula.getText());
                    UpdateResult result = collection.replaceOne(query, doc);
                    if (result.getMatchedCount() > 0) {
                        mostrarConfirmacion("Reserva actualizada", "La reserva ha sido actualizada exitosamente");
                    } else {
                        mostrarAlerta("Error al actualizar reserva", "No se encontró la reserva para actualizar");
                    }
                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al actualizar reserva", "Error al actualizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }


    public void botonEliminarReservaAula() {
        if (campo_seleccionar_aula.getText().isEmpty() || campo_seleccionar_fecha_aula.getValue() == null || campo_seleccionar_horario_aula.getText().isEmpty() || campo_cedula_reserva_aula.getText().isEmpty()) {
            mostrarAlerta("Error al eliminar reserva", "Por favor, seleccione una reserva a eliminar.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea eliminar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Aulas";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("aula", campo_seleccionar_aula.getText())
                            .append("fecha", campo_seleccionar_fecha_aula.getValue().toString())
                            .append("horario", campo_seleccionar_horario_aula.getText())
                            .append("cedula", campo_cedula_reserva_aula.getText());
                    collection.deleteOne(query);
                    mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar reserva", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    public void botonLimpiarCamposReservaAula() {
        campo_seleccionar_aula.setText("Aulas");
        campo_seleccionar_fecha_aula.setValue(null);
        campo_seleccionar_horario_aula.setText("Horarios");
        campo_cedula_reserva_aula.setText("");
    }

    // Buscar laboratorio
    public void buscarLaboratorio(){
        String filter = campo_buscar_laboratorio.getText();
        filteredDataReservasLaboratorios.setPredicate(reserva -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filter.toLowerCase();
            return reserva.getLaboratorio().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getFecha().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getHorario().toLowerCase().contains(lowerCaseFilter) ||
                    reserva.getCedula().toLowerCase().contains(lowerCaseFilter);
        });
    }

    // Mostrar reserva de laboratorios
    public void cargarReservasLaboratorios() {
        listaReservasLaboratorios.clear(); // Limpiar la lista actual antes de cargar nuevas reservas

        String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
        String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
        String collectionName = "Reservas_Laboratorios";

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            for (Document doc : collection.find()) {
                reserva_laboratorios reserva = new reserva_laboratorios(
                        doc.getString("laboratorio"),
                        doc.getString("fecha"),
                        doc.getString("horario"),
                        doc.getString("cedula")
                );
                listaReservasLaboratorios.add(reserva);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al cargar reservas de laboratorios", "Error al consultar la base de datos: " + e.getMessage());
        }
    }

    // Seleccionar reserva de laboratorios
    public void seleccionarLaboratorioReserva() {
        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            campo_seleccionar_laboratorios.setText(reservaSeleccionada.getLaboratorio());
            campo_seleccionar_fecha_laboratorios.setValue(java.time.LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_laboratorio.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_laboratorio.setText(reservaSeleccionada.getCedula());
        }
    }

    // Realizar reserva de laboratorios
    public void botonRealizarReservaLaboratorio() {
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText());

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty() || campo_cedula_reserva_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al realizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_laboratorio.getText().length() != 10) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (comprobarReservaAulas(campo_cedula_reserva_laboratorio.getText(), campo_seleccionar_fecha_laboratorios.getValue().toString(), campo_seleccionar_horario_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText()))) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado este laboratorio en la misma fecha y horario");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText()))) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else if (comprobarReservaAulas(campo_cedula_reserva_laboratorio.getText(), campo_seleccionar_fecha_laboratorios.getValue().toString(), campo_seleccionar_horario_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Realizar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea realizar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Laboratorios";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document doc = new Document("laboratorio", campo_seleccionar_laboratorios.getText())
                            .append("fecha", campo_seleccionar_fecha_laboratorios.getValue().toString())
                            .append("horario", campo_seleccionar_horario_laboratorio.getText())
                            .append("cedula", campo_cedula_reserva_laboratorio.getText());
                    collection.insertOne(doc);
                    mostrarConfirmacion("Reserva realizada", "La reserva ha sido realizada exitosamente");
                    botonLimpiarCamposReservaLaboratorio();
                    cargarReservasLaboratorios();
                } catch (Exception e) {
                    mostrarAlerta("Error al realizar reserva", "Error al realizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    // Actualizar reserva de laboratorios
    public void botonActualizarReservaLaboratorio() {
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText());

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty() || campo_cedula_reserva_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_laboratorio.getText().length() != 10) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (comprobarReservaAulas(campo_cedula_reserva_laboratorio.getText(), campo_seleccionar_fecha_laboratorios.getValue().toString(), campo_seleccionar_horario_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())) && !campo_seleccionar_laboratorios.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getLaboratorio())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado este laboratorio en la misma fecha y horario");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())) && !campo_cedula_reserva_laboratorio.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else if (campo_seleccionar_laboratorios.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getLaboratorio())
                && campo_seleccionar_fecha_laboratorios.getValue().toString().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getFecha())
                && campo_seleccionar_horario_laboratorio.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getHorario())
                && campo_cedula_reserva_laboratorio.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al actualizar reserva", "No se ha realizado ningún cambio en los campos");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Actualizar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea actualizar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Laboratorios";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("laboratorio", tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getLaboratorio())
                            .append("fecha", tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getFecha())
                            .append("horario", tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getHorario())
                            .append("cedula", tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getCedula());

                    Document doc = new Document("laboratorio", campo_seleccionar_laboratorios.getText())
                            .append("fecha", campo_seleccionar_fecha_laboratorios.getValue().toString())
                            .append("horario", campo_seleccionar_horario_laboratorio.getText())
                            .append("cedula", campo_cedula_reserva_laboratorio.getText());
                    UpdateResult result = collection.replaceOne(query, doc);
                    if (result.getMatchedCount() > 0) {
                        mostrarConfirmacion("Reserva actualizada", "La reserva ha sido actualizada exitosamente");
                    } else {
                        mostrarAlerta("Error al actualizar reserva", "No se encontró la reserva para actualizar");
                    }
                    botonLimpiarCamposReservaLaboratorio();
                    cargarReservasLaboratorios();
                } catch (Exception e) {
                    mostrarAlerta("Error al actualizar reserva", "Error al actualizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    // Eliminar reserva de laboratorios
    public void botonEliminarReservaLaboratorio() {
        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty() || campo_cedula_reserva_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al eliminar reserva", "Por favor, seleccione una reserva a eliminar.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Reserva");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea eliminar esta reserva?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
            Optional<ButtonType> opcion = alert.showAndWait();

            if (opcion.get() == ButtonType.OK) {
                String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
                String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
                String collectionName = "Reservas_Laboratorios";

                try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                    MongoDatabase database = mongoClient.getDatabase(databaseName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    Document query = new Document("laboratorio", campo_seleccionar_laboratorios.getText())
                            .append("fecha", campo_seleccionar_fecha_laboratorios.getValue().toString())
                            .append("horario", campo_seleccionar_horario_laboratorio.getText())
                            .append("cedula", campo_cedula_reserva_laboratorio.getText());
                    collection.deleteOne(query);
                    mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                    botonLimpiarCamposReservaLaboratorio();
                    cargarReservasLaboratorios();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar reserva", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    // Limpiar campos de reserva de laboratorios
    public void botonLimpiarCamposReservaLaboratorio() {
        campo_seleccionar_laboratorios.setText("Laboratorios");
        campo_seleccionar_fecha_laboratorios.setValue(null);
        campo_seleccionar_horario_laboratorio.setText("Horarios");
        campo_cedula_reserva_laboratorio.setText("");
    }

    public void moduloBotonUsuario() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void moduloBotonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void moduloBotonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonModulos() {
        dashboard_modulos.setVisible(true);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonUsuarios() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
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