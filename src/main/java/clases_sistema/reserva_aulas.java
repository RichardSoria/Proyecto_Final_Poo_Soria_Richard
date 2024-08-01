package clases_sistema;

public class reserva_aulas {
    String aula;
    String fecha;
    String horario;
    String cedula;

    public reserva_aulas() {}

    public reserva_aulas(String aula, String fecha, String horario, String cedula) {
        this.aula = aula;
        this.fecha = fecha;
        this.horario = horario;
        this.cedula = cedula;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
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
