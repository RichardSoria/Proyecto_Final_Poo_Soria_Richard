package controllers;

/**
 * La clase DashboardAdministradorController es un controlador que gestiona las acciones y eventos de la interfaz de usuario
 * para el rol de Administrador en el sistema de Gestión de Aulas y Laboratorios de la ESFOT.
 * Permite realizar operaciones de gestión de usuarios, reservas de aulas y laboratorios, y configuración de la base de datos.
 *
 * Esta clase incluye métodos para cargar, añadir, actualizar y eliminar usuarios, así como para realizar reservas de aulas y laboratorios.
 * También permite cambiar el modo de la base de datos y buscar usuarios y aulas.
 *
 * Dependencias externas:
 * - JavaFX para la interfaz gráfica de usuario.
 * - Clases de reserva: ReservaAula y ReservaLaboratorio.
 * - Clase UsuarioConectado para gestionar la sesión del usuario.
 * - Clase EnviarCorreo para enviar notificaciones por correo electrónico.
 * - Clase CredencialesAvisos para mostrar alertas y confirmaciones.
 * - NuevoUsuario para almacenar la información de los usuarios.
 *
 * @autor Richard Soria
 * */

// Importaciones necesarias para el funcionamiento del controlador
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardAdministradorController extends credenciales_avisos implements Initializable {

    // Definición de elementos de la interfaz de usuario con la anotación @FXML
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

    @FXML
    private ObservableList<nuevo_usuario> listaUsuarios;
    @FXML
    private FilteredList<nuevo_usuario> filteredData;

    @FXML
    private ObservableList<reserva_aulas> listaReservasAulas;
    @FXML
    private FilteredList<reserva_aulas> filteredDataReservasAulas;

    @FXML
    private ObservableList<reserva_laboratorios> listaReservasLaboratorios;
    @FXML
    private FilteredList<reserva_laboratorios> filteredDataReservasLaboratorios;

    /**
     * Variable de instancia para manejar la información del usuario conectado.
     * @param nombreApellidoUsuario Nombre y apellido del usuario conectado.
     * @param enviarCorreo Instancia de la clase EnviarCorreo para enviar notificaciones por correo electrónico.
     * */
    // Variables para manejar información del usuario conectado
    String nombreApellidoUsuario;
    enviar_correo enviarCorreo = new enviar_correo();

    /**
     * Inicializa los componentes del controlador.
     * Define la visibilidad de los paneles del dashboard.
     * Este método es llamado automáticamente después de cargar el archivo FXML.
     */
    // Método de inicialización del controlador
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Inicialización de componentes de la interfaz de usuario.
         * Configuración de columnas de las tablas y eventos de los elementos de la interfaz.
         * Carga de reservas de aulas y laboratorios desde la base de datos.
         * Configuración de DatePicker para seleccionar fechas de reservas.
         * Configuración de MenuItems para seleccionar opciones de aulas y horarios.
         * Configuración de MenuItems para seleccionar opciones de laboratorios y horarios.
         * Mostrar nombre de usuario conectado en la interfaz.
         * Cargar usuarios por defecto en la tabla de usuarios.
         */

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

        // Configurar DatePicker para reservas de aulas
        configurarDatePicker(campo_seleccionar_fecha_aula);

        // Configurar DatePicker para reservas de laboratorios
        configurarDatePicker(campo_seleccionar_fecha_laboratorios);

    }

    /**
     * Configura el DatePicker para seleccionar fechas de reservas.
     * Deshabilita fechas anteriores a la fecha actual y fines de semana.
     * @param datePicker DatePicker para seleccionar fechas de reservas.
     * Se configura para deshabilitar fechas anteriores a la fecha actual y fines de semana.
     * Se establece un color de fondo para las fechas deshabilitadas.
     */
    // Configuración del DatePicker para deshabilitar fechas anteriores y fines de semana
    private void configurarDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Deshabilitar fechas anteriores a hoy
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Color de fondo para fechas deshabilitadas
                }

                // Deshabilitar sábados y domingos
                DayOfWeek day = date.getDayOfWeek();
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Color de fondo para fines de semana
                }
            }
        });
    }

    /**
     * Realizar la búsqueda de un usuario en la tabla de usuarios.
     * Filtra los usuarios por cédula, nombre, apellido, correo, número de celular y tipo de rol.
     * Actualiza la tabla de usuarios con los resultados de la búsqueda.
     */
    // Método para buscar un usuario en la tabla de usuarios
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

    /**
     * Realizar la carga de los usuarios desde la base de datos.
     * Obtiene los usuarios de la colección seleccionada en la base de datos.
     * Actualiza la lista de usuarios con los datos obtenidos.
     * Se manejan excepciones en caso de errores al cargar los usuarios.
     * Se muestra una alerta en caso de error al cargar los usuarios.
     * Se muestra una confirmación al cargar los usuarios exitosamente.
     * */
    // Método para cargar a los usuarios desde la base de datos
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

    /**
     * Selección de un usuario en la tabla de usuarios.
     * Obtiene el usuario seleccionado en la tabla de usuarios.
     * Muestra los datos del usuario seleccionado en los campos de texto.
     */
    // Método para seleccionar un usuario de la tabla de usuarios
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

    /**
     * Añadir un usuario a la base de datos.
     * Válida los campos de texto y selecciona un tipo de usuario.
     * Comprueba que la cédula, correo y número de celular no estén registrados.
     * Añade el usuario a la colección seleccionada en la base de datos.
     * Envía una notificación por correo electrónico al usuario registrado.
     * Muestra una alerta en caso de error al añadir el usuario.
     * Muestra una confirmación al añadir el usuario exitosamente.
     */
    // Método para añadir un usuario a la base de datos
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

        } else if (usuarioExisteCollection(campo_cedula.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al añadir usuario", "El usuario ya se encuentra registrado");

        } else if (usuarioExisteCollectionCorreo(campo_correo.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al añadir usuario", "El correo ya se encuentra registrado");

        } else if (usuarioExisteCollectionNumeroCelular(campo_numero_celular.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al añadir usuario", "El número de celular ya se encuentra registrado");

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

                    enviarCorreo.enviarCorreo(campo_correo.getText(), "Registro de Usuario", "Estimado/a " + campo_nombre.getText() + " " + campo_apellido.getText() + ",\n\n" +
                            "Su usuario ha sido registrado exitosamente en el sistema de Gestión de Aulas y Laboratorios de la ESFOT.\n\n" +
                            "Cédula: " + campo_cedula.getText() + "\n" +
                            "Nombre: " + campo_nombre.getText() + " " + campo_apellido.getText() + "\n" +
                            "Correo: " + campo_correo.getText() + "\n" +
                            "Contraseña: " + campo_contrasena.getText() + "\n" +
                            "Número de Celular: " + campo_numero_celular.getText() + "\n" +
                            "Tipo de Usuario: " + campo_rol_usuario.getText() + "\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    botonLimpiarCamposUsuarios();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al añadir usuario", "Error al añadir usuario en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Actualizar un usuario en la base de datos.
     * Válida los campos de texto y selecciona un tipo de usuario.
     * Actualiza los datos del usuario seleccionado en la tabla de usuarios.
     * Envía una notificación por correo electrónico al usuario actualizado.
     * Muestra una alerta en caso de error al actualizar el usuario.
     * Muestra una confirmación al actualizar el usuario exitosamente.
     * */
    // Método para actualizar un usuario en la base de datos
    public void botonAcualizarUsuario() {
        if (campo_cedula.getText().isEmpty() || campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_correo.getText().isEmpty() || campo_contrasena.getText().isEmpty() || campo_numero_celular.getText().isEmpty() || campo_rol_usuario.getText().equals("Tipo de Usuario")) {
            mostrarAlerta("Error al actualizar usuario", "Por favor, llene todos los campos y seleccione un tipo de usuario");

        } else if (!validarCorreo(campo_correo.getText())) {
            mostrarAlerta("Error al actualizar usuario", "Correo Institucional inválido");

        } else if (campo_cedula.getText().length() != 10) {
            mostrarAlerta("Error al actualizar usuario", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula.getText())) {
            mostrarAlerta("Error al actualizar usuario", "Cédula inválida");

        } else if (campo_numero_celular.getText().length() != 10) {
            mostrarAlerta("Error al actualizar usuario", "Número de celular inválido");

        } else if (!campoNumerico(campo_numero_celular.getText())) {
            mostrarAlerta("Error al actualizar usuario", "Número de celular inválido");

        } else if (campo_contrasena.getText().length() < 8) {
            mostrarAlerta("Error al actualizar usuario", "La contraseña debe tener al menos 8 caracteres");

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

                    Document query = new Document("cedula", tabla_mostrar_usuarios.getSelectionModel().getSelectedItem().getCedula());
                    Document doc = new Document("cedula", campo_cedula.getText())
                            .append("nombre", campo_nombre.getText())
                            .append("apellido", campo_apellido.getText())
                            .append("correo", campo_correo.getText())
                            .append("contrasena", generateHash(campo_contrasena.getText()))
                            .append("numero_celular", campo_numero_celular.getText())
                            .append("tipo_rol", campo_rol_usuario.getText());

                    UpdateResult result = collection.replaceOne(query, doc);

                    if (result.getMatchedCount() > 0) {
                        enviarCorreo.enviarCorreo(campo_correo.getText(), "Actualización de Usuario", "Estimado/a " + campo_nombre.getText() + " " + campo_apellido.getText() + ",\n\n" +
                                "Su usuario ha sido actualizado exitosamente en el sistema de Gestión de Aulas y Laboratorios de la ESFOT.\n\n" +
                                "Cédula: " + campo_cedula.getText() + "\n" +
                                "Nombre: " + campo_nombre.getText() + " " + campo_apellido.getText() + "\n" +
                                "Correo: " + campo_correo.getText() + "\n" +
                                "Contraseña: " + campo_contrasena.getText() + "\n" +
                                "Número de Celular: " + campo_numero_celular.getText() + "\n" +
                                "Tipo de Usuario: " + campo_rol_usuario.getText() + "\n\n" +
                                "Saludos cordiales,\n" +
                                "Gestión de Aulas y Laboratorios ESFOT");

                        mostrarConfirmacion("Usuario actualizado", "El usuario ha sido actualizado exitosamente");
                        botonLimpiarCamposUsuarios();
                        cargarUsuarios();
                    } else {
                        mostrarAlerta("Error al actualizar usuario", "No se encontró el usuario a actualizar");
                    }
                } catch (Exception e) {
                    mostrarAlerta("Error al actualizar usuario", "Error al actualizar usuario en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Eliminar un usuario de la base de datos.
     * Válida los campos de texto y selecciona un tipo de usuario.
     * Comprueba que el usuario a eliminar esté registrado en la base de datos.
     * Elimina el usuario seleccionado de la colección en la base de datos.
     * Envía una notificación por correo electrónico al usuario eliminado.
     * Muestra una alerta en caso de error al eliminar el usuario.
     * Muestra una confirmación al eliminar el usuario exitosamente.
     * */
    // Método para eliminar un usuario de la base de datos
    public void botonEliminarUsuario() {
        if (campo_cedula.getText().isEmpty() || campo_nombre.getText().isEmpty() || campo_apellido.getText().isEmpty() || campo_correo.getText().isEmpty() || campo_contrasena.getText().isEmpty() || campo_numero_celular.getText().isEmpty() || campo_rol_usuario.getText().equals("Tipo de Usuario")) {
            mostrarAlerta("Error al eliminar usuario", "Por favor, seleccione un usuario a eliminar.");

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

        } else if (!usuarioExisteCollection(campo_cedula.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al eliminar usuario", "No se ha eliminado el usuario ya que no se encuentra registrado");

        } else if (!usuarioExisteCollectionCorreo(campo_correo.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al eliminar usuario", "El correo no se encuentra registrado");

        } else if (!usuarioExisteCollectionNumeroCelular(campo_numero_celular.getText(), campo_rol_usuario.getText())) {
            mostrarAlerta("Error al eliminar usuario", "El número de celular no se encuentra registrado");

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

                    enviarCorreo.enviarCorreo(campo_correo.getText(), "Eliminación de Usuario", "Estimado/a " + campo_nombre.getText() + " " + campo_apellido.getText() + ",\n\n" +
                            "Su usuario ha sido eliminado exitosamente del sistema de Gestión de Aulas y Laboratorios de la ESFOT.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    mostrarConfirmacion("Usuario eliminado", "El usuario ha sido eliminado exitosamente");
                    botonLimpiarCamposUsuarios();
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar usuario", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Limpiar los campos de texto de los usuarios.
     * Limpia los campos de texto de cédula, nombre, apellido, correo, contraseña y número de celular.
     * Limpia el campo de texto de tipo de usuario.
     * */
    // Limpiar campos de texto de usuarios
    public void botonLimpiarCamposUsuarios() {
        campo_cedula.clear();
        campo_nombre.clear();
        campo_apellido.clear();
        campo_correo.clear();
        campo_contrasena.clear();
        campo_numero_celular.clear();
        campo_rol_usuario.setText("Tipo de Usuario");
    }

    /**
     * Realizar la búsqueda de reservas de aulas.
     * Filtra las reservas de aulas por aula, fecha, horario y cédula.
     * Actualiza la tabla de reservas de aulas con los resultados de la búsqueda.
     */
    // Método para buscar reservas de aulas
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


    /**
     * Realizar la carga de reservas de laboratorios.
     * Consulta las reservas de laboratorios desde la base de datos.
     * Actualiza la lista de reservas de laboratorios con los resultados de la consulta.
     * Muestra un mensaje de error si ocurre un problema al cargar las reservas.
     */
    // Método para cargar reservas de aulas desde la base de datos
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

    /**
     * Seleccionar una reserva de aulas.
     * Obtiene la reserva de aulas seleccionada en la tabla.
     * Muestra los datos de la reserva en los campos de texto correspondientes.
     */
    // Método para seleccionar una reserva de aulas
    public void seleccionarAulaReserva() {
        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            campo_seleccionar_aula.setText(reservaSeleccionada.getAula());
            campo_seleccionar_fecha_aula.setValue(java.time.LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_aula.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_aula.setText(reservaSeleccionada.getCedula());
        }
    }

    /**
     * Realizar la reserva de un aula.
     * Válida los campos de texto para realizar la reserva de un aula.
     * Comprueba que la cédula sea válida y que el usuario exista en el sistema.
     * Comprueba que el usuario no haya reservado el aula en la misma fecha y horario.
     * Comprueba que el usuario no haya reservado un laboratorio en la misma fecha y horario.
     * Realiza la reserva del aula en la base de datos.
     * Envía una notificación por correo electrónico al usuario que realizó la reserva.
     * Muestra una alerta en caso de error al realizar la reserva.
     * Muestra una confirmación al realizar la reserva exitosamente.
     * */
    // Método para realizar una reserva de aulas
    public void botonRealizarReservaAula() {
        nombreApellidoUsuario = mostrarNombreApellidoUsuario(campo_cedula_reserva_aula.getText());

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

                    enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_aula.getText()), "Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del aula " + campo_seleccionar_aula.getText() + " para el día " + campo_seleccionar_fecha_aula.getValue().toString() + " en el horario de " + campo_seleccionar_horario_aula.getText() + " ha sido realizada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    mostrarConfirmacion("Reserva realizada", "La reserva ha sido realizada exitosamente");
                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al realizar reserva", "Error al realizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Actualizar una reserva de aulas.
     * Válida los campos de texto para actualizar la reserva de un aula.
     * Comprueba que la cédula sea válida y que el usuario exista en el sistema.
     * Comprueba que el usuario no haya reservado el aula en la misma fecha y horario.
     * Comprueba que el usuario no haya reservado un laboratorio en la misma fecha y horario.
     * Actualiza la reserva del aula en la base de datos.
     * Envía una notificación por correo electrónico al usuario que actualizó la reserva.
     * Muestra una alerta en caso de error al actualizar la reserva.
     * Muestra una confirmación al actualizar la reserva exitosamente.
     * */
    // Método para actualizar una reserva de aulas
    public void botonActualizarReservaAula() {
        nombreApellidoUsuario = mostrarNombreApellidoUsuario(campo_cedula_reserva_aula.getText());

        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();

        boolean reservaEncontrada = listaReservasAulas.stream().anyMatch(reserva -> reserva.getAula().equals(campo_seleccionar_aula.getText())
                && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString())
                && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText())
                && reserva.getCedula().equals(campo_cedula_reserva_aula.getText()));

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

        } else if(reservaSeleccionada == null && !reservaEncontrada) {
            mostrarAlerta("Error al actualizar reserva", "No se encontró la reserva para actualizar");
        }

        else if (campo_seleccionar_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getAula())
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
                        enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_aula.getText()), "Actualización de Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                                "Su reserva del aula " + campo_seleccionar_aula.getText() + " para el día " + campo_seleccionar_fecha_aula.getValue().toString() + " en el horario de " + campo_seleccionar_horario_aula.getText() + " ha sido actualizada exitosamente.\n\n" +
                                "Saludos cordiales,\n" +
                                "Gestión de Aulas y Laboratorios ESFOT");
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

    /**
     * Eliminar una reserva de aulas.
     * Válida los campos de texto para eliminar la reserva de un aula.
     * Comprueba que la reserva a eliminar esté registrada en la base de datos.
     * Elimina la reserva del aula seleccionada de la colección en la base de datos.
     * Envía una notificación por correo electrónico al usuario que eliminó la reserva.
     * Muestra una alerta en caso de error al eliminar la reserva.
     * Muestra una confirmación al eliminar la reserva exitosamente.
     * */
    // Método para eliminar una reserva de aulas
    public void botonEliminarReservaAula() {

        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();

        boolean reservaEncontrada = listaReservasAulas.stream().anyMatch(reserva -> reserva.getAula().equals(campo_seleccionar_aula.getText())
                && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString())
                && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText())
                && reserva.getCedula().equals(campo_cedula_reserva_aula.getText()));

        if (campo_seleccionar_aula.getText().isEmpty() || campo_seleccionar_fecha_aula.getValue() == null || campo_seleccionar_horario_aula.getText().isEmpty() || campo_cedula_reserva_aula.getText().isEmpty()) {
            mostrarAlerta("Error al eliminar reserva", "Por favor, seleccione una reserva a eliminar.");

        } else if (reservaSeleccionada == null && !reservaEncontrada) {
            mostrarAlerta("Error al eliminar reserva", "No se encontró la reserva para eliminar");

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

                    enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_aula.getText()), "Eliminación de Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del aula " + campo_seleccionar_aula.getText() + " para el día " + campo_seleccionar_fecha_aula.getValue().toString() + " en el horario de " + campo_seleccionar_horario_aula.getText() + " ha sido eliminada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar reserva", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Limpiar los campos de texto de las reservas de aulas.
     * Limpia los campos de texto de aula, fecha, horario y cédula.
     * */
    // Limpiar campos de texto de reserva de aulas
    public void botonLimpiarCamposReservaAula() {
        campo_seleccionar_aula.setText("Aulas");
        campo_seleccionar_fecha_aula.setValue(null);
        campo_seleccionar_horario_aula.setText("Horarios");
        campo_cedula_reserva_aula.setText("");
    }

    /**
     * Realizar la búsqueda de reservas de laboratorios.
     * Filtra las reservas de laboratorios por laboratorio, fecha, horario y cédula.
     * Actualiza la tabla de reservas de laboratorios con los resultados de la búsqueda.
     */
    // Método para buscar reservas de laboratorios
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

    /**
     * Cargar las reservas de laboratorios.
     * Consulta las reservas de laboratorios desde la base de datos.
     * Actualiza la lista de reservas de laboratorios con los resultados de la consulta.
     * Muestra un mensaje de error si ocurre un problema al cargar las reservas.
     * */
    // Método para cargar reservas de laboratorios desde la base de datos
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

    /**
     * Seleccionar una reserva de laboratorios.
     * Obtiene la reserva de laboratorios seleccionada en la tabla.
     * Muestra los datos de la reserva en los campos de texto correspondientes.
     * */
    // Método para seleccionar una reserva de laboratorios
    public void seleccionarLaboratorioReserva() {
        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            campo_seleccionar_laboratorios.setText(reservaSeleccionada.getLaboratorio());
            campo_seleccionar_fecha_laboratorios.setValue(java.time.LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_laboratorio.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_laboratorio.setText(reservaSeleccionada.getCedula());
        }
    }

    /**
     * Realizar la reserva de un laboratorio.
     * Válida los campos de texto para realizar la reserva de un laboratorio.
     * Comprueba que la cédula sea válida y que el usuario exista en el sistema.
     * Comprueba que el usuario no haya reservado el laboratorio en la misma fecha y horario.
     * Comprueba que el usuario no haya reservado un aula en la misma fecha y horario.
     * Realiza la reserva del laboratorio en la base de datos.
     * Envía una notificación por correo electrónico al usuario que realizó la reserva.
     * Muestra una alerta en caso de error al realizar la reserva.
     * Muestra una confirmación al realizar la reserva exitosamente.
     * */
    // Método para realizar una reserva de laboratorios
    public void botonRealizarReservaLaboratorio() {
        nombreApellidoUsuario = mostrarNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText());

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

                    enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_laboratorio.getText()), "Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del laboratorio " + campo_seleccionar_laboratorios.getText() + " para el día " + campo_seleccionar_fecha_laboratorios.getValue().toString() + " en el horario de " + campo_seleccionar_horario_laboratorio.getText() + " ha sido realizada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    mostrarConfirmacion("Reserva realizada", "La reserva ha sido realizada exitosamente");
                    botonLimpiarCamposReservaLaboratorio();
                    cargarReservasLaboratorios();
                } catch (Exception e) {
                    mostrarAlerta("Error al realizar reserva", "Error al realizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Actualizar una reserva de laboratorios.
     * Válida los campos de texto para actualizar la reserva de un laboratorio.
     * Comprueba que la cédula sea válida y que el usuario exista en el sistema.
     * Comprueba que el usuario no haya reservado el laboratorio en la misma fecha y horario.
     * Comprueba que el usuario no haya reservado un aula en la misma fecha y horario.
     * Actualiza la reserva del laboratorio en la base de datos.
     * Envía una notificación por correo electrónico al usuario que actualizó la reserva.
     * Muestra una alerta en caso de error al actualizar la reserva.
     * Muestra una confirmación al actualizar la reserva exitosamente.
     * */
    // Método para actualizar una reserva de laboratorios
    public void botonActualizarReservaLaboratorio() {
        nombreApellidoUsuario = mostrarNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText());

        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();

        boolean reservaEncontrada = listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText())
                && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString())
                && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())
                && reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()));

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty() || campo_cedula_reserva_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_laboratorio.getText().length() != 10) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (reservaSeleccionada == null && !reservaEncontrada) {
            mostrarAlerta("Error al actualizar reserva", "No se encontró la reserva para actualizar");

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
                        enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_laboratorio.getText()), "Actualización de Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                                "Su reserva del laboratorio " + campo_seleccionar_laboratorios.getText() + " para el día " + campo_seleccionar_fecha_laboratorios.getValue().toString() + " en el horario de " + campo_seleccionar_horario_laboratorio.getText() + " ha sido actualizada exitosamente.\n\n" +
                                "Saludos cordiales,\n" +
                                "Gestión de Aulas y Laboratorios ESFOT");
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

    /**
     * Eliminar una reserva de laboratorios.
     * Válida los campos de texto para eliminar la reserva de un laboratorio.
     * Comprueba que la reserva a eliminar esté registrada en la base de datos.
     * Elimina la reserva del laboratorio seleccionado de la colección en la base de datos.
     * Envía una notificación por correo electrónico al usuario que eliminó la reserva.
     * Muestra una alerta en caso de error al eliminar la reserva.
     * Muestra una confirmación al eliminar la reserva exitosamente.
     * */
    // Método para eliminar una reserva de laboratorios
    public void botonEliminarReservaLaboratorio() {

        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();

        boolean reservaEncontrada = listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText())
                && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString())
                && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())
                && reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()));

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty() || campo_cedula_reserva_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al eliminar reserva", "Por favor, seleccione una reserva a eliminar.");

        } else if (reservaSeleccionada == null && !reservaEncontrada) {
            mostrarAlerta("Error al eliminar reserva", "No se encontró la reserva para eliminar");

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

                    enviarCorreo.enviarCorreo(obtenerCorreoUsuario(campo_cedula_reserva_laboratorio.getText()), "Eliminación de Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del laboratorio " + campo_seleccionar_laboratorios.getText() + " para el día " + campo_seleccionar_fecha_laboratorios.getValue().toString() + " en el horario de " + campo_seleccionar_horario_laboratorio.getText() + " ha sido eliminada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");

                    mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                    botonLimpiarCamposReservaLaboratorio();
                    cargarReservasLaboratorios();
                } catch (Exception e) {
                    mostrarAlerta("Error al eliminar reserva", "Error al consultar la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Limpiar los campos de texto de las reservas de laboratorios.
     * Limpia los campos de texto de laboratorio, fecha, horario y cédula.
     * */
    // Método para limpiar campos de texto de reserva de laboratorios
    public void botonLimpiarCamposReservaLaboratorio() {
        campo_seleccionar_laboratorios.setText("Laboratorios");
        campo_seleccionar_fecha_laboratorios.setValue(null);
        campo_seleccionar_horario_laboratorio.setText("Horarios");
        campo_cedula_reserva_laboratorio.setText("");
    }

    /**
     * Muestra el módulo de gestión de usuarios.
     * Oculta los módulos de reservas de aulas y laboratorios.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    // Métodos para visualizar y ocultar los módulos de la interfaz gráfica
    public void moduloBotonUsuario() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo de reservas de aulas.
     * Oculta los módulos de gestión de usuarios y reservas de laboratorios.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void moduloBotonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo de reservas de laboratorios.
     * Oculta los módulos de gestión de usuarios y reservas de aulas.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void moduloBotonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo general de la interfaz gráfica.
     * Oculta los módulos de gestión de usuarios, reservas de aulas y laboratorios.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void botonModulos() {
        dashboard_modulos.setVisible(true);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo de gestión de usuarios.
     * Oculta los módulos de reservas de aulas y laboratorios.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void botonUsuarios() {
        dashboard_gestion_usuarios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo de reservas de aulas.
     * Oculta los módulos de gestión de usuarios y reservas de laboratorios.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void botonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Muestra el módulo de reservas de laboratorios.
     * Oculta los módulos de gestión de usuarios y reservas de aulas.
     * Limpia los campos de texto de los módulos de reservas y gestión de usuarios.
     * */
    public void botonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_gestion_usuarios.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposUsuarios();
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    /**
     * Cerrar sesión del usuario.
     * Muestra una alerta para confirmar si el usuario desea cerrar sesión.
     * Cierra la ventana actual y muestra la ventana de inicio de sesión.
     * */
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