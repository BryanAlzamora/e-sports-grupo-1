package Modelo;

/**
 * Clase que representa un enfrentamiento entre dos equipos en una jornada de competición.
 */
public class Enfrentamiento {
    private Equipo equipoUno; // Primer equipo que participa en el enfrentamiento
    private Equipo equipoDos; // Segundo equipo que participa en el enfrentamiento
    private Integer idEnfrentamiento; // Identificador único del enfrentamiento
    private String hora; // Hora del enfrentamiento
    private Integer resultadoLocal; // Resultado del equipo local
    private Integer resultadoVisitante; // Resultado del equipo visitante
    private Jornada jornada; // Jornada a la que pertenece el enfrentamiento
    private Integer idEnfJor; // Identificador único del enfrentamiento en la jornada
    private Integer idJorComp; // Identificador único de la jornada de competición

    /**
     * Constructor de la clase Enfrentamiento.
     * @param equipoUno Primer equipo que participa en el enfrentamiento.
     * @param equipoDos Segundo equipo que participa en el enfrentamiento.
     * @param idEnfrentamiento Identificador único del enfrentamiento.
     * @param hora Hora del enfrentamiento.
     * @param resultadoLocal Resultado del equipo local.
     * @param resultadoVisitante Resultado del equipo visitante.
     * @param jornada Jornada a la que pertenece el enfrentamiento.
     */
    public Enfrentamiento(Equipo equipoUno, Equipo equipoDos, Integer idEnfrentamiento, String hora, Integer resultadoLocal, Integer resultadoVisitante, Jornada jornada) {
        this.equipoUno = equipoUno;
        this.equipoDos = equipoDos;
        this.idEnfrentamiento = idEnfrentamiento;
        this.hora = hora;
        this.resultadoLocal = resultadoLocal;
        this.resultadoVisitante = resultadoVisitante;
        this.jornada = jornada;
    }

    /**
     * Constructor por defecto de la clase Enfrentamiento.
     */
    public Enfrentamiento() {}

    // Métodos getters y setters

    public Equipo getEquipoUno() {
        return equipoUno;
    }

    public void setEquipoUno(Equipo equipoUno) {
        this.equipoUno = equipoUno;
    }

    public Equipo getEquipoDos() {
        return equipoDos;
    }

    public void setEquipoDos(Equipo equipoDos) {
        this.equipoDos = equipoDos;
    }

    public Integer getIdJorComp() {
        return idJorComp;
    }

    public void setIdJorComp(Integer idJorComp) {
        this.idJorComp = idJorComp;
    }

    public Integer getIdEnfrentamiento() {
        return idEnfrentamiento;
    }

    public void setIdEnfrentamiento(Integer idEnfrentamiento) {
        this.idEnfrentamiento = idEnfrentamiento;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getIdEnfJor() {
        return idEnfJor;
    }

    public void setIdEnfJor(Integer idEnfJor) {
        this.idEnfJor = idEnfJor;
    }

    public Integer getResultadoLocal() {
        return resultadoLocal;
    }

    public void setResultadoLocal(Integer resultadoLocal) {
        this.resultadoLocal = resultadoLocal;
    }

    public Integer getResultadoVisitante() {
        return resultadoVisitante;
    }

    public void setResultadoVisitante(Integer resultadoVisitante) {
        this.resultadoVisitante = resultadoVisitante;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
