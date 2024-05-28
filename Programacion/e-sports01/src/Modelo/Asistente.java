package Modelo;

/**
 * Asitente representa al integrante del grupo que ayuda al entrenador.
 * @author Grupo1
 */
public class Asistente {
    private Integer idAsistente;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Double sueldo;
    private Equipo equipo;

    public Asistente(Integer idIntegrante, String nombre, String apellido1, String apellido2, Double sueldo, Equipo equipo) {
        this.idAsistente = idIntegrante;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sueldo = sueldo;
        this.equipo = equipo;
    }

    public Asistente() {

    }

    public Integer getIdIntegrante() {
        return idAsistente;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idAsistente = idIntegrante;
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
