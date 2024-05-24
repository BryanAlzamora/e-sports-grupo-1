package Modelo;

/**
 * Clase que representa un entrenador.
 */
public class Entrenador {
    private Integer idIntegrante; // Identificador único del entrenador
    private String nombre; // Nombre del entrenador
    private String apellido1; // Primer apellido del entrenador
    private String apellido2; // Segundo apellido del entrenador
    private Double sueldo; // Sueldo del entrenador
    private Equipo equipo; // Equipo al que pertenece el entrenador

    /**
     * Constructor de la clase Entrenador.
     * @param idIntegrante Identificador único del entrenador.
     * @param nombre Nombre del entrenador.
     * @param apellido1 Primer apellido del entrenador.
     * @param apellido2 Segundo apellido del entrenador.
     * @param sueldo Sueldo del entrenador.
     * @param equipo Equipo al que pertenece el entrenador.
     */
    public Entrenador(Integer idIntegrante, String nombre, String apellido1, String apellido2, Double sueldo, Equipo equipo) {
        this.idIntegrante = idIntegrante;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sueldo = sueldo;
        this.equipo = equipo;
    }

    /**
     * Constructor por defecto de la clase Entrenador.
     */
    public Entrenador() {}

    // Métodos getters y setters

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
