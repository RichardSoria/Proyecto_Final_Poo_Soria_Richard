package clases_sistema;

/**
 * La clase usuarioConectado permite crear un objeto con los datos del usuario que se encuentra conectado.
 * @autor Richard Soria
 * */

public class usuarioConectado {
    private static usuarioConectado instance;
    private String nombreUsuarioConectado;
    private String correoUsuarioConectado;
    private String cedulaUsuarioConectado;
    private String tipoRolUsuarioConectado;

    private usuarioConectado() {}

    /**
     * Utiliza el patrón Singleton para garantizar que solo exista una instancia de usuarioConectado.
     * @return instance
     * */
    public static usuarioConectado getInstance() {
        if (instance == null) {
            instance = new usuarioConectado();
        }
        return instance;
    }

    /**
     * Obtiene y establece los datos del usuario conectado.
     * Permitiendo obtener los datos del usuario conectado.
     * Logrando utilizar los datos del usuario conectado en cualquier parte del sistema.
     * */
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
