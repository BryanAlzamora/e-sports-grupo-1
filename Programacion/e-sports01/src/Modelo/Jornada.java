package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa una jornada de competición.
 */
public class Jornada {

    private LocalDate fechaEnfrentamiento; // Fecha de los enfrentamientos de la jornada
    private Integer idJornada; // Identificador único de la jornada
    private List<Enfrentamiento> listaEnfrentamiento; // Lista de enfrentamientos de la jornada
    private Competicion competicion; // Competición a la que pertenece la jornada

    /**
     * Constructor de la clase Jornada.
     * @param idJornada Identificador único de la jornada.
     * @param fechaEnfrentamiento Fecha de los enfrentamientos de la jornada.
     * @param competicion Competición a la que pertenece la jornada.
     */
    public Jornada(Integer idJornada, LocalDate fechaEnfrentamiento, Competicion competicion) {
        this.idJornada = idJornada;
        this.fechaEnfrentamiento = LocalDate.parse(String.valueOf(fechaEnfrentamiento));
        this.competicion = competicion;
    }

    /**
     * Constructor vacío de la clase Jornada.
     */
    public Jornada() {

    }

    // Métodos getters y setters

    public LocalDate getFechaEnfrentamiento() {
        return fechaEnfrentamiento;
    }

    public void setFechaEnfrentamiento(LocalDate fechaEnfrentamiento) {
        this.fechaEnfrentamiento = fechaEnfrentamiento;
    }

    public Integer getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(String idJornada) {
        this.idJornada = Integer.valueOf(idJornada);
    }

    public List<Enfrentamiento> getListaEnfrentamiento() {
        return listaEnfrentamiento;
    }

    public void setListaEnfrentamiento(List<Enfrentamiento> listaEnfrentamiento) {
        this.listaEnfrentamiento = listaEnfrentamiento;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }
}
