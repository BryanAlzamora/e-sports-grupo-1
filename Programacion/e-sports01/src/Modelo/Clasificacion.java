package Modelo;

import javax.swing.*;
import java.util.List;
/**
 * La clase Clasificacion representa la clasificación de una competición deportiva,
 * donde se almacenan los puntos de cada equipo.
 */
public class Clasificacion {
    private Integer idClasificacion;
    private List<String> listaPuntos;
    private Competicion competicion;
    private List<Equipo> listaEquipo;
    /**
     * Constructor de la clase Clasificacion que inicializa todos los campos.
     *
     * @param idClasificacion El identificador de la clasificación.
     * @param listaPuntos     La lista de puntos de los equipos en la clasificación.
     * @param competicion     La competición a la que pertenece la clasificación.
     * @param listaEquipo     La lista de equipos que participan en la clasificación.
     */
    public Clasificacion(Integer idClasificacion, List<String> listaPuntos, Competicion competicion, List<Equipo> listaEquipo) {
        this.idClasificacion = idClasificacion;
        this.listaPuntos = listaPuntos;
        this.competicion = competicion;
        this.listaEquipo = listaEquipo;
    }
    /**
     * Constructor de la clase Clasificacion que inicializa todos los campos excepto la competición.
     *
     * @param idClasificacion El identificador de la clasificación.
     * @param listaPuntos     La lista de puntos de los equipos en la clasificación.
     * @param listaEquipo     La lista de equipos que participan en la clasificación.
     */
    public Clasificacion(Integer idClasificacion, List<String> listaPuntos, List<Equipo> listaEquipo) {
        this.idClasificacion = idClasificacion;
        this.listaPuntos = listaPuntos;
        this.competicion = competicion;
        this.listaEquipo = listaEquipo;
    }
    /**
     * Obtiene el identificador de la clasificación.
     *
     * @return El identificador de la clasificación.
     */
    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }
    /**
     * Obtiene la lista de puntos de los equipos en la clasificación.
     *
     * @return La lista de puntos de los equipos.
     */
    public List<String> getListaPuntos() {
        return listaPuntos;
    }

    public void setListaPuntos(List<String> listaPuntos) {
        this.listaPuntos = listaPuntos;
    }

    public Competicion getCompeticion() {
        return competicion;
    }
    /**
     * Obtiene la lista de equipos que participan en la clasificación.
     * @param competicion objeto competicion
     */
    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public List<Equipo> getListaEquipo() {
        return listaEquipo;
    }
    /**
     * Establece la lista de equipos que participan en la clasificación.
     *
     * @param listaEquipo La lista de equipos.
     */
    public void setListaEquipo(List<Equipo> listaEquipo) {
        this.listaEquipo = listaEquipo;
    }
}
