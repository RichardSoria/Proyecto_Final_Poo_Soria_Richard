package clases_sistema;

public class usuarioConectado {
    private static usuarioConectado instance;
    private String nombreUsuarioConectado;

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
}
