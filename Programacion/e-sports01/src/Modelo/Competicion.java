package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa una competición entre varios equipos.
 */
public class Competicion {
    private LocalDate fechaInicio; // Fecha de inicio de la competición
    private LocalDate fechaFin; // Fecha de finalización de la competición
    private String estado; // Estado actual de la competición
    private Integer idCompeticion; // Identificador único de la competición
    private String nombre; // Nombre de la competición
    private List<Jornada> listaJornada; // Lista de jornadas de la competición
    private Juego juego; // Juego asociado a la competición

    /**
     * Constructor de la clase Competicion.
     * @param idCompeticion Identificador único de la competición.
     * @param nombre Nombre de la competición.
     * @param fechaInicio Fecha de inicio de la competición.
     * @param fechaFin Fecha de finalización de la competición.
     * @param estado Estado actual de la competición.
     */
    public Competicion(Integer idCompeticion, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.idCompeticion = idCompeticion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    /**
     * Constructor de la clase Competicion.
     * @param nombre Nombre de la competición.
     * @param fechaIni Fecha de inicio de la competición.
     * @param fechaFinal Fecha de finalización de la competición.
     * @param estado Estado actual de la competición.
     * @param juego Juego asociado a la competición.
     */
    public Competicion(String nombre, LocalDate fechaIni, LocalDate fechaFinal, String estado, Juego juego) {
        this.nombre = nombre;
        this.fechaInicio = fechaIni;
        this.fechaFin = fechaFinal;
        this.estado = estado;
        this.juego = juego;
    }

    // Métodos getters y setters

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdCompeticion() {
        return idCompeticion;
    }

    public void setIdCompeticion(Integer idCompeticion) {
        this.idCompeticion = idCompeticion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Jornada> getListaJornada() {
        return listaJornada;
    }

    public void setListaJornada(List<Jornada> listaJornada) {
        this.listaJornada = listaJornada;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}
