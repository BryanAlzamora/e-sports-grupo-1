package Controlador.ControladorBD;

import Modelo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * La clase ControladorConsultas gestiona las operaciones relacionadas con la tabla de conuslta en la base de datos.
 */
public class ControladorConsultas {
    private Connection con;
    /**
     * Constructor de la clase ControladorConsultas.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorConsultas(Connection con) {
        this.con = con;
    }

    /**
     * Llena una lista de jugadores desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Jugador.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public List<Jugador> llenarJugadoresS(String tipo) throws SQLException {
        List<Jugador> listaJugadores = new ArrayList<>();
        String plantilla = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement statement = con.prepareStatement(plantilla);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Jugador jugador = new Jugador();
            jugador.setIdIntegrante(rs.getInt("id_equipo"));
            jugador.setNombre(rs.getString("nombre"));
            jugador.setApellido1(rs.getString("apellido1"));
            jugador.setApellido2(rs.getString("apellido2"));
            jugador.setSueldo(rs.getDouble("sueldo"));
            jugador.setNacionalidad(rs.getString("nacionalidad"));
            jugador.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            jugador.setNickname(rs.getString("nickname"));
            jugador.setRol(rs.getString("rol"));
            Integer id_Equipo = rs.getInt("id_equipo");
            //jugador.setEquipo(cb.buscarEquipo(id_Equipo));
            listaJugadores.add(jugador);
        }

        statement.close();
        return listaJugadores;
    }

    /**
     * Llena una lista de equipos desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Equipo.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public List<Equipo> llenarEquiposS(String tipo) throws SQLException {
        List<Equipo> listaEquipo = new ArrayList<>();
        String plantilla = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement statement = con.prepareStatement(plantilla);
        ResultSet rs = statement.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(LocalDate.parse(rs.getString("fecha_fundacion"), formatter));

            //jugador.setEquipo(cb.buscarEquipo(id_Equipo));
            listaEquipo.add(equipo);

        }
        statement.close();
        return listaEquipo;
    }
    /**
     * Llena una lista de entrenadores desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Entrenador.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */

    public List<Entrenador> llenarEntrenador(String tipo) throws SQLException {
        List<Entrenador> listaEntrenador = new ArrayList<>();
        String plantilla2 = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement Entrenador = con.prepareStatement(plantilla2);
        ResultSet rss = Entrenador.executeQuery();

        while (rss.next()) {
            Entrenador entrenador = new Entrenador();
            entrenador.setNombre(rss.getString("nombre"));
            entrenador.setApellido1(rss.getString("apellido1"));
            entrenador.setApellido2(rss.getString("apellido2"));
            entrenador.setSueldo(rss.getDouble("sueldo"));

            listaEntrenador.add(entrenador);
        }

        Entrenador.close();
        return listaEntrenador;
    }
    /**
     * Llena una lista de asistentes desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Asistente.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public List<Asistente> llenarAsistente(String tipo) throws SQLException {
        List<Asistente> listaAsistente = new ArrayList<>();
        String plantilla2 = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement Asistente = con.prepareStatement(plantilla2);
        ResultSet rss = Asistente.executeQuery();

        while (rss.next()) {
            Asistente asistente = new Asistente();
            asistente.setNombre(rss.getString("nombre"));
            asistente.setApellido1(rss.getString("apellido1"));
            asistente.setApellido2(rss.getString("apellido2"));
            asistente.setSueldo(rss.getDouble("sueldo"));

            listaAsistente.add(asistente);
        }

        Asistente.close();
        return listaAsistente;
    }
    /**
     * Llena una lista de competiciones desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Competicion.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public List<Competicion> llenarCompeticion(String tipo) throws SQLException {
        List<Competicion> listaCompeticion = new ArrayList<>();
        String plantilla2 = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement Competicion = con.prepareStatement(plantilla2);
        ResultSet rss = Competicion.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        while (rss.next()) {
            Competicion competicion = new Competicion();
            competicion.setNombre(rss.getString("nombre"));
            competicion.setFechaInicio(LocalDate.parse(rss.getString("fecha_inicio"), formatter));
            competicion.setFechaFin(LocalDate.parse(rss.getString("fecha_fin"), formatter));
            competicion.setEstado(rss.getString("estado"));




            listaCompeticion.add(competicion);
        }

        Competicion.close();
        return listaCompeticion;
    }
    /**
     * Llena una lista de juegos desde la base de datos.
     *
     * @param tipo El tipo de tabla de la cual se obtendrán los datos.
     * @return Una lista de objetos Juego.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */

    public List<Juego>  llenarJuegos(String tipo) throws SQLException {
        List<Juego> listaJuego = new ArrayList<>();
        String plantilla2 = "SELECT * FROM " + tipo; // Construyendo la consulta SQL
        PreparedStatement Juego = con.prepareStatement(plantilla2);
        ResultSet rss = Juego.executeQuery();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        while (rss.next()) {
            Juego juego = new Juego();
            juego.setNombre(rss.getString("nombre"));
            juego.setEmpresa(rss.getString("empresa"));
            juego.setFechalanzamiento(LocalDate.parse(rss.getString("fecha_lanzamiento"), formatter));


            listaJuego.add(juego);

        }

        Juego.close();
        return listaJuego;
    }
}
