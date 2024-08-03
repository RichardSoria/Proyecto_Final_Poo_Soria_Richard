package clases_sistema;

public class usuarioConectado {
    private static usuarioConectado instance;
    private String nombreUsuarioConectado;
    private String correoUsuarioConectado;
    private String cedulaUsuarioConectado;
    private String tipoRolUsuarioConectado;

    private usuarioConectado() {}

    public static usuarioConectado getInstance() {
        if (instance == null) {
            instance = new usuarioConectado();
        }
        return instance;
    }

    public String getNombreUsuarioConectado() {
        return nombreUsuarioConectado;
    }

    public void setNombreUsuarioConectado(String nombreUsuarioConectado) {
        this.nombreUsuarioConectado = nombreUsuarioConectado;
    }

    public String getCorreoUsuarioConectado() {
        return correoUsuarioConectado;
    }

    public void setCorreoUsuarioConectado(String correoUsuarioConectado) {
        this.correoUsuarioConectado = correoUsuarioConectado;
    }

    public String getCedulaUsuarioConectado() {
        return cedulaUsuarioConectado;
    }

    public void setCedulaUsuarioConectado(String cedulaUsuarioConectado) {
        this.cedulaUsuarioConectado = cedulaUsuarioConectado;
    }

    public String getTipoRolUsuarioConectado() {
        return tipoRolUsuarioConectado;
    }

    public void setTipoRolUsuarioConectado(String tipoRolUsuarioConectado) {
        this.tipoRolUsuarioConectado = tipoRolUsuarioConectado;
    }

    public void cerrarSesion() {
        instance = null;
    }
}
