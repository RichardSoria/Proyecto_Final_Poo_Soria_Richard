package clases_sistema;

public class reserva_laboratorios {
    String laboratorio;
    String fecha;
    String horario;
    String cedula;

    public reserva_laboratorios() {}

    public reserva_laboratorios(String laboratorio, String fecha, String horario, String cedula) {
        this.laboratorio = laboratorio;
        this.fecha = fecha;
        this.horario = horario;
        this.cedula = cedula;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
