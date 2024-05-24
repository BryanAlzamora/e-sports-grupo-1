package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa un juego en el sistema.
 */
public class Juego {
    private String nombre; // Nombre del juego
    private String empresa; // Empresa desarrolladora del juego
    private LocalDate fechalanzamiento; // Fecha de lanzamiento del juego
    private Integer idJuego; // Identificador único del juego
    private List<Competicion> listaCompeticion; // Lista de competiciones relacionadas con el juego

    /**
     * Constructor de la clase Juego.
     * @param nombre Nombre del juego.
     * @param empresa Empresa desarrolladora del juego.
     * @param fechalanzamiento Fecha de lanzamiento del juego.
     * @param idJuego Identificador único del juego.
     */
    public Juego(String nombre, String empresa, LocalDate fechalanzamiento, Integer idJuego) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.fechalanzamiento = fechalanzamiento;
        this.idJuego = idJuego;
        this.listaCompeticion = listaCompeticion;
    }

    /**
     * Constructor vacío de la clase Juego.
     */
    public Juego() {

    }

    // Métodos getters y setters

    public LocalDate getFechalanzamiento() {
        return fechalanzamiento;
    }

    public void setFechalanzamiento(LocalDate fechalanzamiento) {
        this.fechalanzamiento = fechalanzamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(Integer idJuego) {
        this.idJuego = idJuego;
    }
}
