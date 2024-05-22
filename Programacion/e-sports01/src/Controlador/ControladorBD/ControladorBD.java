package Controlador.ControladorBD;

import Controlador.ControladorPrincipal;

import Modelo.*;

import Modelo.Equipo;
import Modelo.Usuario;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
/**
 * ControladorBD es una clase que gestiona la conexión a la base de datos y
 * proporciona métodos para interactuar con diferentes tablas de la base de datos.
 */
public class ControladorBD {
    private ControladorTablaAsistente ctasistente;
    private ControladorTablaClasificacion ctclasificacion;
    private ControladorTablaCompeticion ctcompeticion;
    private ControladorTablaEnfrentamiento ctenfrentamiento;
    private ControladorTablaEntrenador ctentrenador;
   private ControladorConsultas cc;
    private ControladorTablaEquipo ctequipo;
    private ControladorTablaJornada ctjornada;
    private ControladorTablaJuego ctjuego;
    private ControladorTablaJugador ctjugador;
    private ControladorTablaPatrocinador ctpatrocinador;
    private ControladorTablaUsuario ctUsuario;
    private ControladorPrincipal cp;
    private Connection con;
    /**
     * Constructor que recibe un ControladorPrincipal y abre la conexión a la base de datos.
     *
     * @param cp Controlador principal de la aplicación.
     */
    public ControladorBD(ControladorPrincipal cp) {
        abrirConexion();
        this.cp = cp;
        ctUsuario = new ControladorTablaUsuario(con);
        ctpatrocinador = new ControladorTablaPatrocinador(con);
        ctjugador = new ControladorTablaJugador(con);
        ctjuego = new ControladorTablaJuego(con);
        ctjornada = new ControladorTablaJornada(con);
        ctequipo = new ControladorTablaEquipo(con);
        ctentrenador = new ControladorTablaEntrenador(con);
        ctenfrentamiento = new ControladorTablaEnfrentamiento(con);
        ctcompeticion = new ControladorTablaCompeticion(con);
        ctclasificacion = new ControladorTablaClasificacion(con);
        ctasistente = new ControladorTablaAsistente(con);
        cc=new ControladorConsultas(con);
    }

    public ControladorBD() {

    }
    /**
     * Abre la conexión a la base de datos.
     */
    /*public void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "userproyecto";
            String passwd = "userproyecto";
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("conexion abierta");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("conexion erronea");
        } catch (SQLException e) {
        }
    }*/
   public void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
            String user = "equipo16";
            String passwd = "equipo16";
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("conexion abierta");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("conexion erronea");
        } catch (SQLException e) {
        }
    }


    /**
     * Obtiene la cantidad de equipos.
     *
     * @return Número de equipos.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public Integer cantidadEquipos() throws Exception {
        return ctequipo.cantidadEquipos();
    }
    /**
     * Llena una lista de equipos.
     *
     * @return Lista de equipos.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public List<Equipo> llenarEquipos() throws Exception {
        return ctequipo.llenarEquipos();
    }
    /**
     * Busca un equipo por su ID.
     *
     * @param equipo ID del equipo.
     * @return El equipo encontrado.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public Equipo buscarEquipo(Integer equipo) throws Exception {
        return ctequipo.buscarEquipo(equipo);
    }
    /**
     * Busca un patrocinador por su ID.
     *
     * @return El patrocinador encontrado.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public List<String> buscarPatrocinador() throws SQLException {
        return ctpatrocinador.buscarPatrocinador();
    }
    /**
     * Llena una lista de jugadores de un equipo.
     *
     * @param x ID del equipo.
     * @return Lista de jugadores.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public List<Jugador> llenarJugadores(Integer x) throws Exception {
        return ctjugador.llenarJugadores(x);
    }
    /**
     * Llena una lista de competiciones.
     *
     * @return Lista de competiciones.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public List<Competicion> llenarCompeticiones() throws Exception {
        return ctcompeticion.llenarCompeticiones();
    }
    /**
     * Llena una lista de equipos de una competición.
     *
     * @param competicion ID de la competición.
     * @return Lista de equipos.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public List<Equipo> llenarEquiposCompeticion(Integer competicion) throws Exception {
        List<String> idEquipos = new ArrayList<>();
        idEquipos = ctclasificacion.llenarEquiposCompeticion(competicion);
        List<Equipo> listaEquipos = new ArrayList<>();
        for (int x = 0; x < idEquipos.size(); x++) {
            listaEquipos = ctequipo.llenarEquiposporID(idEquipos.get(x));
        }
        return listaEquipos;
    }
    /**
     * Genera el calendario de competiciones.
     *
     * @throws Exception Si ocurre un error al generar el calendario.
     */
    public void generarCalendario() throws Exception {
        try {
            java.sql.CallableStatement stmt = con.prepareCall("{call generar_calendario}");
            stmt.execute();
            System.out.println("Calendario generado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al generar el calendario: " + ex.getMessage());
        }
    }
    public void crearPatrocinador(Integer idPatrocinador, String nombre) throws Exception {
        ctpatrocinador.crearPatrocinador(idPatrocinador, nombre);

    }
    public void crearJugador(String nombre, String primerApellido, String segundoApellido, Integer sueldo, String nacionalidad, LocalDate fechaNacimiento, String nickname, String rol, String equipo) throws Exception {
        ctjugador.crearJugador(nombre, primerApellido, segundoApellido, sueldo, nacionalidad, fechaNacimiento, nickname, rol, equipo);

    }
        /**
     * Crea un nuevo jugador.
     *
     * @param nombre          Nombre del jugador.
     * @param primerApellido  Primer apellido del jugador.
     * @param segundoApellido Segundo apellido del jugador.
     * @param sueldo          Sueldo del jugador.
     * @param nacionalidad    Nacionalidad del jugador.
     * @param fechaNacimiento Fecha de nacimiento del jugador.
     * @param nickname        Nickname del jugador.
     * @param rol             Rol del jugador.
     * @param equipo          Equipo del jugador.
     * @return El jugador creado.
     * @throws Exception Si ocurre un error en la base de datos.
     */

    /**
     * Selecciona un equipo por su nombre.
     *
     * @param nombre Nombre del equipo.
     * @return Lista de equipos que coinciden con el nombre.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public ArrayList selectEquipo(String nombre) throws Exception {
        return ctequipo.selectEquipo(nombre);
    }
    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param user Nombre de usuario.
     * @return El usuario encontrado.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public Usuario buscarUsuario(String user,String password) throws Exception {
        return ctUsuario.buscarUsuario(user,password);
    }
    public Equipo buscarEquipo(String nombre) throws Exception {
        return ctequipo.buscarEquipo(nombre);
    }
    /**
     * Busca todos los juegos.
     *
     * @return Lista de juegos.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Juego> buscarJuegos() throws SQLException {
        return ctjuego.buscarJuegos();
    }
    /**
     * Busca un juego por su nombre.
     *
     * @param nombre Nombre del juego.
     * @return El juego encontrado.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public Juego buscarJuego(String nombre) throws Exception {
        return ctjuego.buscarJuego(nombre);
    }
    /**
     * Inserta una nueva competición.
     *
     * @param c Competición a insertar.
     * @throws Exception Si ocurre un error en la base de datos.
     */
    public void insertarCompeticion(Competicion c) throws Exception {
        ctcompeticion.insertarCompeticion(c);
    }
    /**
     * Busca todas las competiciones.
     *
     * @return Lista de nombres de competiciones.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<String> buscarCompeticiones() throws SQLException {

        return ctcompeticion.buscarCompeticiones();
    }
    public List<Patrocinador> llenarPatrocinadorNombre(String equiposeleccionado) throws SQLException
    {
        return ctpatrocinador.llenarPatrocinadorNombre(equiposeleccionado);
    }
    public void eliminarPatrocinador(String nombre,String equipo) throws Exception {
        ctpatrocinador.eliminarPatrocinador(nombre,equipo);
    }
    public Patrocinador actualizarPatrocinador(String nombre, String equipo) throws Exception {
        ctpatrocinador.actualizarPatrocinador( nombre, equipo);
        Patrocinador buscarDatos = ctpatrocinador.actualizarPatrocinador(nombre, equipo);

        return buscarDatos;
    }
    public List<Jugador> llenarJugadoresNombre(String equiposelecionado) throws SQLException
    {
        return  ctjugador.llenarJugadoresNombre(equiposelecionado);
    }
    public void eliminarJugador(String nombre,String equipo) throws Exception {
         ctjugador.eliminarJugador(nombre,equipo);
    }
    public Jugador actualizarJugador(String nombre, String equipo) throws Exception {
        ctjugador.actualizarJugador( nombre, equipo);
        Jugador buscarDatos = ctjugador.actualizarJugador(nombre, equipo);

        return buscarDatos;
    }
    public Usuario crearUsuario(String nombre,String contrasena,String tipoUsuario) throws Exception {
        ctUsuario.crearUsuario(nombre,contrasena,tipoUsuario);
        return null;
    }

    public void editarJugadorConfir(String nombre,String primerApellido,String segundoApellido,double sueldo,String nacionalidad,LocalDate fechaNacimiento,String nickname,String rol,String nuevoEquipo,String nombreAntiguo,String equipoAntiguo) throws Exception {


        ctjugador.editarJugadorConfir(nombre, primerApellido, segundoApellido, sueldo, nacionalidad, fechaNacimiento, nickname, rol, nuevoEquipo,nombreAntiguo,equipoAntiguo);
    }public void editarPatrocinadorConfir(String nombre,String nuevoEquipo,String nombreAntiguo,String equipoAntiguo) throws Exception {


        ctpatrocinador.editarPatrocinadorConfir(nombre,nuevoEquipo,nombreAntiguo,equipoAntiguo);
    }
    public List<Jugador> llenarJugadoresS(String tipo) throws SQLException {
        cc.llenarJugadoresS(tipo);
        List<Jugador> listaJugadores = cc.llenarJugadoresS(tipo);
        return listaJugadores;
    }
    public void borrarEquipo(String nombre) throws SQLException {
        ctequipo.borrarEquipo(nombre);
    }
    public List<Equipo> llenarEquiposS(String tipo) throws Exception {
        cc.llenarEquiposS(tipo);
        List<Equipo> listaEquipo = cc.llenarEquiposS(tipo);
        return listaEquipo;
    }
    public List<Entrenador> llenarEntrenador(String tipo) throws Exception {
        cc.llenarEntrenador(tipo);
        List<Entrenador> listaEntrenador = cc.llenarEntrenador(tipo);
        return listaEntrenador;
    }
    public List<Asistente> llenarAsistente(String tipo) throws Exception {
        cc.llenarAsistente(tipo);
        List<Asistente> listaAsistente = cc.llenarAsistente(tipo);
        return listaAsistente;
    }
    public List<Competicion> llenarCompeticion(String tipo) throws Exception {
        cc.llenarCompeticion(tipo);
        List<Competicion> listaCompeticion = cc.llenarCompeticion(tipo);
        return listaCompeticion;
    }
    public List<Juego> llenarJuegos(String tipo) throws Exception {
        cc.llenarJuegos(tipo);
        List<Juego> listaJuego = cc.llenarJuegos(tipo);
        return listaJuego;
    }
    public void crearEquipo(String nombre, LocalDate fecha, String patrocinador, String competicion) throws Exception {
        ctequipo.crearEquipo(nombre, fecha, patrocinador,competicion);
    }
    public void editarEquipo(String nombreAntiguo,String nombreNuevo,LocalDate fechacambio,String VincularNuevo,String Desvincular) throws Exception {
        ctequipo.editarEquipo(nombreAntiguo,nombreNuevo,fechacambio,VincularNuevo,Desvincular);
    }
}