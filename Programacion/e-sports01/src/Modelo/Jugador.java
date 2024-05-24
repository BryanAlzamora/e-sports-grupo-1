package Modelo;

import java.time.LocalDate;

/**
 * Clase que representa un jugador en el sistema.
 */
public class Jugador {
    private Integer idIntegrante; // Identificador único del jugador
    private String nombre; // Nombre del jugador
    private String apellido1; // Primer apellido del jugador
    private String apellido2; // Segundo apellido del jugador
    private Double sueldo; // Sueldo del jugador
    private String nacionalidad; // Nacionalidad del jugador
    private LocalDate fechaNacimiento; // Fecha de nacimiento del jugador
    private String nickname; // Nickname del jugador
    private String rol; // Rol del jugador
    private String equipo; // Equipo al que pertenece el jugador

    /**
     * Constructor de la clase Jugador.
     * @param idIntegrante Identificador único del jugador.
     * @param nombre Nombre del jugador.
     * @param apellido1 Primer apellido del jugador.
     * @param apellido2 Segundo apellido del jugador.
     * @param sueldo Sueldo del jugador.
     * @param nacionalidad Nacionalidad del jugador.
     * @param fechaNacimiento Fecha de nacimiento del jugador.
     * @param nickname Nickname del jugador.
     * @param rol Rol del jugador.
     * @param equipo Equipo al que pertenece el jugador.
     */
    public Jugador(Integer idIntegrante, String nombre, String apellido1, String apellido2, Double sueldo, String nacionalidad, LocalDate fechaNacimiento, String nickname, String rol, String equipo) {
        this.idIntegrante = idIntegrante;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sueldo = sueldo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.rol = rol;
        this.equipo = equipo;
    }

    /**
     * Constructor vacío de la clase Jugador.
     */
    public Jugador() {

    }

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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
}
