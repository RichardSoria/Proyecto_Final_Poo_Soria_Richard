package controllers;

/**
     * La clase DashboardEstudianteController gestiona las acciones de la interfaz de usuario
 * para la reserva de aulas en la aplicación.
 * Proporciona métodos para manejar las reservas y la navegación del dashboard.
 *
 * Esta clase incluye métodos para realizar y gestionar reservas, cambiar entre diferentes
 * vistas del dashboard y manejar la sesión del usuario.
 *
 * Dependencias externas:
 * - JavaFX para la interfaz gráfica de usuario.
 * - Clases de reserva: ReservaAula y ReservaLaboratorio.
 * - Clase UsuarioConectado para gestionar la sesión del usuario.
 * - Clase EnviarCorreo para enviar notificaciones por correo electrónico.
 * - Clase CredencialesAvisos para mostrar alertas y confirmaciones.
 *
 * @autor Richard Soria
 */

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

public class DashboardEstudianteController extends credenciales_avisos implements Initializable {

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
    private ObservableList<reserva_aulas> listaReservasAulas;

    @FXML
    private FilteredList<reserva_aulas> filteredDataReservasAulas;

    /**
     * Variables de instancia para manejar la información del usuario conectado.
     * Estas variables se inicializan con los valores del usuario conectado.
     * @param nombreApellidoUsuario Nombre y apellido del usuario conectado.
     * Se obtiene a partir de la cédula del usuario.
     * @param nombreApellidoUsuarioReservado Nombre y apellido del usuario que ya ha reservado un aula.
     * Se obtiene a partir de la cédula del usuario.
     * @param correoUsuario Correo electrónico del usuario conectado.
     * @param cedulaUsuario Cédula del usuario conectado.
     * @param tipoRolUsuario Tipo de rol del usuario conectado.
     * @param enviarCorreo Instancia de la clase EnviarCorreo para enviar notificaciones por correo electrónico.
     */
    // Variables para manejar información del usuario conectado
    String nombreApellidoUsuario;
    String nombreApellidoUsuarioReservado;
    String correoUsuario = usuarioConectado.getInstance().getCorreoUsuarioConectado();
    String cedulaUsuario = usuarioConectado.getInstance().getCedulaUsuarioConectado();
    String tipoRolUsuario = usuarioConectado.getInstance().getTipoRolUsuarioConectado();
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
         */
        // Mostrar nombre de usuario conectado
        String nombreUsuarioConectado = usuarioConectado.getInstance().getNombreUsuarioConectado();
        nombre_usuario_conectado.setText(nombreUsuarioConectado);

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

        // Configurar DatePicker para reservas de aulas
        configurarDatePicker(campo_seleccionar_fecha_aula);

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
            campo_seleccionar_fecha_aula.setValue(LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_aula.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_aula.setText(reservaSeleccionada.getCedula());
        }
    }

    /**
     * Realizar la reserva de un aula.
     * Válida los campos de la reserva de aula y realiza la reserva en la base de datos.
     * Envía una notificación por correo electrónico al usuario que realiza la reserva.
     * Muestra un mensaje de confirmación si la reserva se realiza con éxito.
     * Muestra un mensaje de error si ocurre un problema al realizar la reserva.
     * Actualiza la tabla de reservas de aulas con la nueva reserva.
     * Limpia los campos de texto después de realizar la reserva.
     * Actualiza la lista de reservas de aulas con la nueva reserva.
     * Comprueba si el usuario ya ha reservado un laboratorio en la misma fecha y horario.
     * Comprueba si ya existe una reserva para la misma aula, fecha y horario.
     * Comprueba si el usuario ya ha reservado un aula en la misma fecha y horario.
     * Comprueba si la cédula del usuario es válida y si el usuario existe en el sistema.
     */
    // Método para realizar una reserva de aulas
    public void botonRealizarReservaAula() {
        campo_cedula_reserva_aula.setText(cedulaUsuario);
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_aula.getText(), tipoRolUsuario);

        if (campo_seleccionar_aula.getText().isEmpty() || campo_seleccionar_fecha_aula.getValue() == null || campo_seleccionar_horario_aula.getText().isEmpty() || campo_cedula_reserva_aula.getText().isEmpty()) {
            mostrarAlerta("Error al realizar reserva", "Por favor, llene todos los campos");
        } else if (campo_cedula_reserva_aula.getText().length() != 10) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");
        } else if (!campoNumerico(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");
        } else if (!usuarioExiste(campo_cedula_reserva_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");
        } else {
            // Buscar si ya existe una reserva para la misma aula, fecha y horario
            Optional<reserva_aulas> reservaExistente = listaReservasAulas.stream()
                    .filter(reserva -> reserva.getAula().equals(campo_seleccionar_aula.getText())
                            && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString())
                            && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()))
                    .findFirst();

            if (reservaExistente.isPresent()) {
                String cedulaReservada = reservaExistente.get().getCedula();
                nombreApellidoUsuarioReservado = mostrarNombreApellidoUsuario(cedulaReservada);
                mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuarioReservado + " ya ha reservado esta aula en la misma fecha y horario");

            } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_aula.getText())
                    && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString())
                    && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()))) {
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
                                .append("cedula", cedulaUsuario);
                        collection.insertOne(doc);

                        enviarCorreo.enviarCorreo(correoUsuario, "Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
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
    }

    /**
     * Actualizar una reserva de aula.
     * Obtiene la reserva de aula seleccionada en la tabla.
     * Válida los campos de la reserva de aula y actualiza la reserva en la base de datos.
     * Envía una notificación por correo electrónico al usuario que actualiza la reserva.
     * Muestra un mensaje de confirmación si la reserva se actualiza con éxito.
     * Muestra un mensaje de error si ocurre un problema al actualizar la reserva.
     * Actualiza la tabla de reservas de aulas con la reserva actualizada.
     * Limpia los campos de texto después de actualizar la reserva.
     * Comprueba si la cédula del usuario es válida y si el usuario existe en el sistema.
     * Comprueba si el usuario tiene permisos para actualizar la reserva de otro usuario.
     * Comprueba si se ha realizado algún cambio en los campos de la reserva.
     * Comprueba si se ha encontrado la reserva para actualizar.
     * Comprueba si el usuario ya ha reservado un laboratorio en la misma fecha y horario.
     */
    // Método para actualizar una reserva de aula
    public void botonActualizarReservaAula() {
        campo_cedula_reserva_aula.setText(cedulaUsuario);
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_aula.getText(), tipoRolUsuario);

        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, seleccione una reserva a actualizar");
            return;
        }

        nombreApellidoUsuarioReservado = mostrarNombreApellidoUsuario(reservaSeleccionada.getCedula());

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
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuarioReservado + " ya ha reservado esta aula en la misma fecha y horario");

        } else if (listaReservasAulas.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_aula.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_aula.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_aula.getText()) && !campo_cedula_reserva_aula.getText().equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getCedula()))) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (comprobarReservaLaboratorios(campo_cedula_reserva_aula.getText(), campo_seleccionar_fecha_aula.getValue().toString(), campo_seleccionar_horario_aula.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else if(reservaSeleccionada == null && !reservaEncontrada) {
            mostrarAlerta("Error al actualizar reserva", "No se encontró la reserva para actualizar");

        } else if (!cedulaUsuario.equals(tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al actualizar reserva", "No tiene permisos para actualizar la reserva de otro usuario");

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

                    enviarCorreo.enviarCorreo(correoUsuario, "Actualización de Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del aula " + campo_seleccionar_aula.getText() + " para el día " + campo_seleccionar_fecha_aula.getValue().toString() + " en el horario de " + campo_seleccionar_horario_aula.getText() + " ha sido actualizada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");
                    mostrarConfirmacion("Reserva actualizada", "La reserva ha sido actualizada exitosamente");

                    botonLimpiarCamposReservaAula();
                    cargarReservasAulas();
                } catch (Exception e) {
                    mostrarAlerta("Error al actualizar reserva", "Error al actualizar reserva en la base de datos: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Eliminar una reserva de aula.
     * Obtiene la reserva de aula seleccionada en la tabla.
     * Válida los campos de la reserva de aula y elimina la reserva de la base de datos.
     * Envía una notificación por correo electrónico al usuario que elimina la reserva.
     * Muestra un mensaje de confirmación si la reserva se elimina con éxito.
     * Muestra un mensaje de error si ocurre un problema al eliminar la reserva.
     * Actualiza la tabla de reservas de aulas sin la reserva eliminada.
     * Limpia los campos de texto después de eliminar la reserva.
     * Comprueba si el usuario tiene permisos para eliminar la reserva de otro usuario.
     * Comprueba si se ha seleccionado una reserva para eliminar.
     * Comprueba si se ha encontrado la reserva para eliminar.
     * Comprueba si la cédula del usuario es válida y si el usuario existe en el sistema.
     * */
    // Método para eliminar una reserva de aula
    public void botonEliminarReservaAula() {
        reserva_aulas reservaSeleccionada = tabla_mostrar_reservas_aulas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            mostrarAlerta("Error al eliminar reserva", "Por favor, seleccione una reserva a eliminar.");
            return;
        }

        nombreApellidoUsuario = obtenerNombreApellidoUsuario(cedulaUsuario, tipoRolUsuario);

        if (!cedulaUsuario.equals(reservaSeleccionada.getCedula())) {
            mostrarAlerta("Error al eliminar reserva", "No tiene permisos para eliminar la reserva de otro usuario");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Reserva");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea eliminar esta reserva?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(IniciarSesionController.class.getResourceAsStream("/images/esfot_buho.png")));
        Optional<ButtonType> opcion = alert.showAndWait();

        if (opcion.isPresent() && opcion.get() == ButtonType.OK) {
            String mongoUri = "mongodb+srv://Richard-Soria:RichardSoria%401899@aulas-laboratorios-esfo.o7jjnmz.mongodb.net/";
            String databaseName = "Base_Datos_Aulas_Laboratorios_ESFOT";
            String collectionName = "Reservas_Aulas";

            try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                MongoCollection<Document> collection = database.getCollection(collectionName);

                Document query = new Document("aula", reservaSeleccionada.getAula())
                        .append("fecha", reservaSeleccionada.getFecha())
                        .append("horario", reservaSeleccionada.getHorario())
                        .append("cedula", reservaSeleccionada.getCedula());

                collection.deleteOne(query);

                enviarCorreo.enviarCorreo(correoUsuario, "Eliminación de Reserva de Aula", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                        "Su reserva del aula " + reservaSeleccionada.getAula() + " para el día " + reservaSeleccionada.getFecha() + " en el horario de " + reservaSeleccionada.getHorario() + " ha sido eliminada exitosamente.\n\n" +
                        "Saludos cordiales,\n" +
                        "Gestión de Aulas y Laboratorios ESFOT");

                mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                botonLimpiarCamposReservaAula();
                cargarReservasAulas();
            } catch (Exception e) {
                mostrarAlerta("Error al eliminar reserva", "Error al eliminar reserva en la base de datos: " + e.getMessage());
            }
        }
    }

    /**
     * Limpiar los campos de reserva de aula.
     * Limpia los campos de texto para seleccionar aula, fecha, horario y cédula.
     */
    // Método para limpiar campos de reserva de aula
    public void botonLimpiarCamposReservaAula() {
        campo_seleccionar_aula.setText("Aulas");
        campo_seleccionar_fecha_aula.setValue(null);
        campo_seleccionar_horario_aula.setText("Horarios");
        campo_cedula_reserva_aula.setText("");
    }

    /**
     * Muestra el módulo de reservas de aulas.
     * Oculta los demás módulos.
     * Limpia los campos de reserva de aula.
     */
    // Métodos para cambiar entre módulos del dashboard
    public void moduloBotonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        botonLimpiarCamposReservaAula();
    }

    /**
     * Muestra el módulo general.
     * Oculta los demás módulos.
     * Limpia los campos de reserva de aula.
     */
    public void botonModulos() {
        dashboard_modulos.setVisible(true);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposReservaAula();
    }

    /**
     * Muestra el módulo de reservas de aulas.
     * Oculta el módulo de reservas de laboratorios.
     * Limpia los campos de reserva de aula.
     */
    public void botonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        botonLimpiarCamposReservaAula();
    }

    /**
     * Cierra la sesión del usuario.
     * Muestra un mensaje de confirmación para cerrar la sesión.
     * Cierra la ventana actual y muestra la ventana de inicio de sesión.
     * */
    // Método para cerrar sesión
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
                usuarioConectado.getInstance().cerrarSesion();
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