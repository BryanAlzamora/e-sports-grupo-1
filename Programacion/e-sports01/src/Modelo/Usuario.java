package Modelo;

/**
 * Clase que representa un usuario en el sistema.
 */
public class Usuario {
    private Integer idUsuario; // Identificador único del usuario
    private String nombre; // Nombre del usuario
    private String contrasena; // Contraseña del usuario
    private String tipo; // Tipo de usuario (por ejemplo: usuario o administrador)

    /**
     * Constructor de la clase Usuario.
     * @param idUsuario Identificador único del usuario.
     * @param nombre Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * @param tipo Tipo de usuario.
     */
    public Usuario(Integer idUsuario, String nombre, String contrasena, String tipo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    /**
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {

    }

    /**
     * Obtiene el identificador único del usuario.
     * @return Identificador único del usuario.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador único del usuario.
     * @param idUsuario Identificador único del usuario.
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el tipo de usuario.
     * @return Tipo de usuario.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de usuario.
     * @param tipo Tipo de usuario.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
