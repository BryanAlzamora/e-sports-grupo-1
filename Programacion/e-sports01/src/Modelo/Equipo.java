package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa un equipo.
 */
public class Equipo {
    private Integer idEquipo; // Identificador único del equipo
    private String nombre; // Nombre del equipo
    private LocalDate fechaFundacion; // Fecha de fundación del equipo
    private List<Jugador> listaJugador; // Lista de jugadores del equipo
    private Patrocinador patrocinador; // Patrocinador del equipo
    private Asistente asistente; // Asistente del equipo
    private List<Entrenador> listaEntrenador; // Lista de entrenadores del equipo
    private List<Clasificacion> listaClasificacion; // Lista de clasificaciones del equipo

    /**
     * Constructor de la clase Equipo.
     * @param idEquipo Identificador único del equipo.
     * @param nombre Nombre del equipo.
     * @param fechaFundacion Fecha de fundación del equipo.
     * @param patrocinador Patrocinador del equipo.
     */
    public Equipo(Integer idEquipo, String nombre, LocalDate fechaFundacion, Patrocinador patrocinador) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.patrocinador = patrocinador;
    }

    /**
     * Constructor de la clase Equipo.
     * @param idEquipo Identificador único del equipo.
     * @param nombre Nombre del equipo.
     * @param fechaFundacion Fecha de fundación del equipo.
     */
    public Equipo(Integer idEquipo, String nombre, LocalDate fechaFundacion ) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
    }

    /**
     * Constructor de la clase Equipo.
     * @param idEquipo Identificador único del equipo.
     * @param nombre Nombre del equipo.
     * @param fechaFundacion Fecha de fundación del equipo.
     * @param listaJugador Lista de jugadores del equipo.
     * @param patrocinador Patrocinador del equipo.
     * @param asistente Asistente del equipo.
     * @param listaEntrenador Lista de entrenadores del equipo.
     * @param listaClasificacion Lista de clasificaciones del equipo.
     */
    public Equipo(Integer idEquipo, String nombre, LocalDate fechaFundacion, List<Jugador> listaJugador, Patrocinador patrocinador, Asistente asistente, List<Entrenador> listaEntrenador, List<Clasificacion> listaClasificacion) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.listaJugador = listaJugador;
        this.patrocinador = patrocinador;
        this.asistente = asistente;
        this.listaEntrenador = listaEntrenador;
        this.listaClasificacion = listaClasificacion;
    }

    // Métodos getters y setters

    public Asistente getAsistente() {
        return asistente;
    }

    public void setAsistente(Asistente asistente) {
        this.asistente = asistente;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public List<Jugador> getListaJugador() {
        return listaJugador;
    }

    public void setListaJugador(List<Jugador> listaJugador) {
        this.listaJugador = listaJugador;
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    public List<Entrenador> getListaEntrenador() {
        return listaEntrenador;
    }

    public void setListaEntrenador(List<Entrenador> listaEntrenador) {
        this.listaEntrenador = listaEntrenador;
    }

    public List<Clasificacion> getListaClasificacion() {
        return listaClasificacion;
    }

    public void setListaClasificacion(List<Clasificacion> listaClasificacion) {
        this.listaClasificacion = listaClasificacion;
    }
}
