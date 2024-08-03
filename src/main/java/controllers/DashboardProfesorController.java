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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardProfesorController extends credenciales_avisos implements Initializable {

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

    private ObservableList<reserva_aulas> listaReservasAulas;
    private FilteredList<reserva_aulas> filteredDataReservasAulas;

    private ObservableList<reserva_laboratorios> listaReservasLaboratorios;
    private FilteredList<reserva_laboratorios> filteredDataReservasLaboratorios;

    String nombreApellidoUsuario;
    String nombreApellidoUsuarioReservado;
    String correoUsuario = usuarioConectado.getInstance().getCorreoUsuarioConectado();
    String cedulaUsuario = usuarioConectado.getInstance().getCedulaUsuarioConectado();
    String tipoRolUsuario = usuarioConectado.getInstance().getTipoRolUsuarioConectado();
    enviar_correo enviarCorreo = new enviar_correo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            campo_seleccionar_fecha_aula.setValue(LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_aula.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_aula.setText(reservaSeleccionada.getCedula());
        }
    }

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
            campo_seleccionar_fecha_laboratorios.setValue(LocalDate.parse(reservaSeleccionada.getFecha()));
            campo_seleccionar_horario_laboratorio.setText(reservaSeleccionada.getHorario());
            campo_cedula_reserva_laboratorio.setText(reservaSeleccionada.getCedula());
        }
    }

    // Realizar reserva de laboratorios
    public void botonRealizarReservaLaboratorio() {
        campo_cedula_reserva_laboratorio.setText(cedulaUsuario);
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText(), tipoRolUsuario);

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al realizar reserva", "Por favor, llene todos los campos");
        } else if (campo_cedula_reserva_laboratorio.getText().length() != 10) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");
        } else if (!campoNumerico(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "Cédula inválida");
        } else if (!usuarioExiste(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");
        } else {
            // Buscar si ya existe una reserva para el mismo laboratorio, fecha y horario
            Optional<reserva_laboratorios> reservaExistente = listaReservasLaboratorios.stream()
                    .filter(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText())
                            && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString())
                            && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText()))
                    .findFirst();

            if (reservaExistente.isPresent()) {
                String cedulaReservada = reservaExistente.get().getCedula();
                nombreApellidoUsuarioReservado = mostrarNombreApellidoUsuario(cedulaReservada);
                mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuarioReservado + " ya ha reservado este laboratorio en la misma fecha y horario");

            } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText())
                    && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString())
                    && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText()))) {
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
                                .append("cedula", cedulaUsuario);
                        collection.insertOne(doc);

                        enviarCorreo.enviarCorreo(correoUsuario, "Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
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
    }

    // Actualizar reserva de laboratorios
    public void botonActualizarReservaLaboratorio() {
        campo_cedula_reserva_laboratorio.setText(cedulaUsuario);
        nombreApellidoUsuario = obtenerNombreApellidoUsuario(campo_cedula_reserva_laboratorio.getText(), tipoRolUsuario);

        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, seleccione una reserva a actualizar");
            return;
        }

        nombreApellidoUsuarioReservado = mostrarNombreApellidoUsuario(reservaSeleccionada.getCedula());

        boolean reservaEncontrada = listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText())
                && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString())
                && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())
                && reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()));

        if (campo_seleccionar_laboratorios.getText().isEmpty() || campo_seleccionar_fecha_laboratorios.getValue() == null || campo_seleccionar_horario_laboratorio.getText().isEmpty()) {
            mostrarAlerta("Error al actualizar reserva", "Por favor, llene todos los campos");

        } else if (campo_cedula_reserva_laboratorio.getText().length() != 10) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!campoNumerico(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al actualizar reserva", "Cédula inválida");

        } else if (!usuarioExiste(campo_cedula_reserva_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario no existe en el sistema");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getLaboratorio().equals(campo_seleccionar_laboratorios.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())) && !campo_seleccionar_laboratorios.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getLaboratorio())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuarioReservado + " ya ha reservado este laboratorio en la misma fecha y horario");

        } else if (listaReservasLaboratorios.stream().anyMatch(reserva -> reserva.getCedula().equals(campo_cedula_reserva_laboratorio.getText()) && reserva.getFecha().equals(campo_seleccionar_fecha_laboratorios.getValue().toString()) && reserva.getHorario().equals(campo_seleccionar_horario_laboratorio.getText())) && !campo_cedula_reserva_laboratorio.getText().equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un laboratorio en la misma fecha y horario");

        } else if (comprobarReservaAulas(campo_cedula_reserva_laboratorio.getText(), campo_seleccionar_fecha_laboratorios.getValue().toString(), campo_seleccionar_horario_laboratorio.getText())) {
            mostrarAlerta("Error al realizar reserva", "El usuario " + nombreApellidoUsuario + " ya ha reservado un aula en la misma fecha y horario");

        } else if (!cedulaUsuario.equals(tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem().getCedula())) {
            mostrarAlerta("Error al actualizar reserva", "No tiene permisos para actualizar la reserva de otro usuario");

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

                    enviarCorreo.enviarCorreo(correoUsuario, "Actualización de Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                            "Su reserva del laboratorio " + campo_seleccionar_laboratorios.getText() + " para el día " + campo_seleccionar_fecha_laboratorios.getValue().toString() + " en el horario de " + campo_seleccionar_horario_laboratorio.getText() + " ha sido actualizada exitosamente.\n\n" +
                            "Saludos cordiales,\n" +
                            "Gestión de Aulas y Laboratorios ESFOT");
                    mostrarConfirmacion("Reserva actualizada", "La reserva ha sido actualizada exitosamente");

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
        reserva_laboratorios reservaSeleccionada = tabla_reservas_laboratorios_mostrar.getSelectionModel().getSelectedItem();

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
            String collectionName = "Reservas_Laboratorios";

            try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                MongoCollection<Document> collection = database.getCollection(collectionName);

                Document query = new Document("laboratorio", reservaSeleccionada.getLaboratorio())
                        .append("fecha", reservaSeleccionada.getFecha())
                        .append("horario", reservaSeleccionada.getHorario())
                        .append("cedula", reservaSeleccionada.getCedula());

                collection.deleteOne(query);

                enviarCorreo.enviarCorreo(correoUsuario, "Eliminación de Reserva de Laboratorio", "Estimado/a " + nombreApellidoUsuario + ",\n\n" +
                        "Su reserva del laboratorio " + reservaSeleccionada.getLaboratorio() + " para el día " + reservaSeleccionada.getFecha() + " en el horario de " + reservaSeleccionada.getHorario() + " ha sido eliminada exitosamente.\n\n" +
                        "Saludos cordiales,\n" +
                        "Gestión de Aulas y Laboratorios ESFOT");

                mostrarConfirmacion("Reserva eliminada", "La reserva ha sido eliminada exitosamente");
                botonLimpiarCamposReservaLaboratorio();
                cargarReservasLaboratorios();
            } catch (Exception e) {
                mostrarAlerta("Error al eliminar reserva", "Error al eliminar reserva en la base de datos: " + e.getMessage());
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

    public void moduloBotonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void moduloBotonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_modulos.setVisible(false);
        dashboard_reservar_aulas.setVisible(false);
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonModulos() {
        dashboard_modulos.setVisible(true);
        dashboard_reservar_aulas.setVisible(false);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonAulas() {
        dashboard_reservar_aulas.setVisible(true);
        dashboard_reservar_laboratorios.setVisible(false);
        botonLimpiarCamposReservaAula();
        botonLimpiarCamposReservaLaboratorio();
    }

    public void botonLabs() {
        dashboard_reservar_laboratorios.setVisible(true);
        dashboard_reservar_aulas.setVisible(false);
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