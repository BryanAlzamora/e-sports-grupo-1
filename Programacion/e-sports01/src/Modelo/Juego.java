package Modelo;

import java.time.LocalDate;
import java.util.List;
/**
 * La clase Juego representa un videojuego asociado a competiciones deportivas.
 */
public class Juego {
    private  String nombre;
    private  String empresa;
    private LocalDate fechalanzamiento;
    private Integer idJuego;
    private List<Competicion> listaCompeticion;

    /**
     * Constructor de la clase Juego.
     *
     * @param nombre          El nombre del juego.
     * @param empresa         La empresa desarrolladora del juego.
     * @param fechalanzamiento La fecha de lanzamiento del juego.
     * @param idJuego         El identificador del juego.
     *
     */

    public Juego(String nombre, String empresa, LocalDate fechalanzamiento, Integer idJuego) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.fechalanzamiento = fechalanzamiento;
        this.idJuego = idJuego;
        this.listaCompeticion = listaCompeticion;
    }

    public Juego() {

    }



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