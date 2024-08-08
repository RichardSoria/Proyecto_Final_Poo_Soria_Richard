package clases_sistema;

/**
 * La clase nuevo_usuario permite crear un objeto con los datos de un nuevo usuario.
 * @autor Richard Soria
 * */

public class nuevo_usuario {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String numero_celular;
    private String tipo_rol;

    public nuevo_usuario() {}

    public nuevo_usuario(String cedula, String nombre, String apellido, String correo, String contrasena, String numero_celular, String tipo_rol) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.numero_celular = numero_celular;
        this.tipo_rol = tipo_rol;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getTipo_rol() {
        return tipo_rol;
    }

    public void setTipo_rol(String tipo_rol) {
        this.tipo_rol = tipo_rol;
    }
}
