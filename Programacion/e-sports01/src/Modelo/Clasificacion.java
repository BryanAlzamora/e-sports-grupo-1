package Modelo;

import java.util.List;

/**
 * Clase que representa la clasificación de equipos en una competición.
 */
public class Clasificacion {
    private Integer idClasificacion; // Identificador único de la clasificación
    private List<String> listaPuntos; // Lista de puntos de los equipos en la clasificación
    private Competicion competicion; // Competición a la que pertenece la clasificación
    private List<Equipo> listaEquipo; // Lista de equipos en la clasificación

    /**
     * Constructor de la clase Clasificacion.
     * @param idClasificacion Identificador único de la clasificación.
     * @param listaPuntos Lista de puntos de los equipos en la clasificación.
     * @param competicion Competición a la que pertenece la clasificación.
     * @param listaEquipo Lista de equipos en la clasificación.
     */
    public Clasificacion(Integer idClasificacion, List<String> listaPuntos, Competicion competicion, List<Equipo> listaEquipo) {
        this.idClasificacion = idClasificacion;
        this.listaPuntos = listaPuntos;
        this.competicion = competicion;
        this.listaEquipo = listaEquipo;
    }

    /**
     * Constructor de la clase Clasificacion.
     * @param idClasificacion Identificador único de la clasificación.
     * @param listaPuntos Lista de puntos de los equipos en la clasificación.
     * @param listaEquipo Lista de equipos en la clasificación.
     */
    public Clasificacion(Integer idClasificacion, List<String> listaPuntos, List<Equipo> listaEquipo) {
        this.idClasificacion = idClasificacion;
        this.listaPuntos = listaPuntos;
        this.listaEquipo = listaEquipo;
    }

    // Métodos getters y setters

    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public List<String> getListaPuntos() {
        return listaPuntos;
    }

    public void setListaPuntos(List<String> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public List<Equipo> getListaEquipo() {
        return listaEquipo;
    }

    public void setListaEquipo(List<Equipo> listaEquipo) {
        this.listaEquipo = listaEquipo;
    }
}
