package Controlador.ControladorBD;

import Controlador.ControladorPrincipal;

import Modelo.*;

import Modelo.Equipo;
import Modelo.Usuario;


import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
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
    }
*/

   /*public void abrirConexion() {
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
    }*/
    public void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1522:orcl";
            String user = "system";
            String passwd = "Oier2004";
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("conexion abierta");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("conexion erronea");
        } catch (SQLException e) {
        }
    }

    

/*
    public void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@a8150ad3dbd3.sn.mynetname.net:33150:xe";
            String user = "proyecto_inigo";
            String passwd = " BryanTeAmo24!";
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println("conexion abierta");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("conexion erronea");
        } catch (SQLException e) {
        }
    }

 */

/**
    *
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
        return ctequipo.buscarEquipoInt(equipo);
    }
    /**
     * Busca un patrocinador por su ID.
     *
     * @return El patrocinador encontrado.
     * @throws SQLException Si ocurre un error en la base de datos.
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
    public void eliminarCompeticion(Competicion c) throws Exception {
        ctcompeticion.eliminarCompeticion(c.getIdCompeticion());
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
    public void crearEquipo(String nombre, LocalDate fecha, Patrocinador patrocinador, Competicion competicion) throws Exception {
        ctequipo.crearEquipo(nombre, fecha, patrocinador,competicion);
    }
    public void editarEquipo(String nombreAntiguo,String nombreNuevo,LocalDate fechacambio,String VincularNuevo,String Desvincular) throws Exception {
        ctequipo.editarEquipo(nombreAntiguo,nombreNuevo,fechacambio,VincularNuevo,Desvincular);
    }





    public Competicion buscarCompeticion(String c) throws Exception {
        Competicion comp= ctcompeticion.buscarCompeticion(c);
        String nombreJuego= ctcompeticion.buscarJuegoCompeticion(c);
        Juego j = ctjuego.buscarJuegoID(nombreJuego);
        comp.setJuego(j);
        return comp;
    }
    public List<Enfrentamiento> buscarEnfrentamientos(Integer idJornada, Integer idCompeticion) throws Exception {

        List<Enfrentamiento> listaEnfrentamientos = new ArrayList<>();
        Integer idJorComp = ctjornada.buscarIdJorComp(idJornada, idCompeticion);
        System.out.println("Lista idJorComp: " + idJorComp);
        // Obtener la lista de enfrentamientos
        listaEnfrentamientos = ctenfrentamiento.buscarEnfrentamientos(idJorComp);
        System.out.println("Lista enfrentamientos: " + listaEnfrentamientos.size());

        for (Enfrentamiento enf : listaEnfrentamientos) {
            // Obtener y establecer el equipo local
            Integer idEquipoLocal = ctenfrentamiento.llenarLocal(enf.getIdEnfJor());
            System.out.println("Lista equipos locales: " + idEquipoLocal);
            Equipo equipoLocal = ctequipo.buscarEquipoInt(idEquipoLocal);
            //System.out.println("Nombre equipo local: " + equipoLocal.getNombre());
            if (equipoLocal != null) {
                System.out.println(equipoLocal.getNombre());
                enf.setEquipoUno(equipoLocal);
            } else {
                System.out.println("Equipo local no encontrado para id: " + idEquipoLocal);
            }

            // Obtener y establecer el equipo visitante
            Integer idEquipoVisitante = ctenfrentamiento.llenarVisitante(enf.getIdEnfJor());
            System.out.println("Lista equipos visitantes: " + idEquipoVisitante);
            Equipo equipoVisitante = ctequipo.buscarEquipoInt(idEquipoVisitante);
            if (equipoVisitante != null) {
                System.out.println(equipoVisitante.getNombre());
                enf.setEquipoDos(equipoVisitante);
            } else {
                System.out.println("Equipo visitante no encontrado para id: " + idEquipoVisitante);
            }
        }

        return listaEnfrentamientos;
    }
    public List<Integer> buscarJornadas(Integer j) throws Exception {
        return ctjornada.buscarJornadas(j);
    }

    public void guardarResultados(Integer idEnfJor, int resultLocal, int resultVisitante) throws Exception {
        ctenfrentamiento.guardarResultados(idEnfJor, resultLocal, resultVisitante);
    }

    public void actualizarTablaClasificacion(Enfrentamiento enfrentamiento, int resultLocal, int resultVisitante) throws Exception {

        Integer idCompeticion = ctjornada.obtenerIdCompeticionDesdeJornada(enfrentamiento.getIdJorComp());
        ctclasificacion.actualizarTablaClasificacion(enfrentamiento,idCompeticion, resultLocal, resultVisitante );


    }
    public void llenarResultados(JTextField resultadoLocal, JTextField resultadoVisitante, Integer idEnfJor) {
        ctenfrentamiento.llenarResultados(resultadoLocal, resultadoVisitante, idEnfJor);
    }
    public List<Clasificacion> obtenerClasificacionesPorCompeticion(Integer idCompeticion) throws Exception {
        return ctclasificacion.obtenerClasificacionesPorCompeticion(idCompeticion);
    }
    public Integer buscarUltimaJornada(Integer competicionID) throws Exception {
        return ctjornada.buscarUltimaJornada(competicionID);
    }
    public void updateEquipoJugador(String nombre, String patrocinador, String competicion, LocalDate fecha) throws Exception {
        ctequipo.updateEquipoJugador(nombre, patrocinador, competicion, fecha);
    }
    public Patrocinador buscarPatrocinadorNombre(String nombrePatrocinador) {
        return  ctpatrocinador.buscarPatrocinadorNombre(nombrePatrocinador);
    }
    public int buscarIdJuegoPorNombre(String nuevoJuego) throws Exception {
        return ctjuego.buscarIdJuegoPorNombre(nuevoJuego);
    }
    public void modificarCompeticion(Integer idCompeticion, String nuevoNombre, Date date, Date date1, String nuevoEstado, int idJuego) throws Exception {
        ctcompeticion.modificarCompeticion(idCompeticion,nuevoNombre, date, date1, nuevoEstado, idJuego);
    }
    public void modificarJuego(Juego j) throws Exception {
        ctjuego.modificarJuego(j);
    }

    public void eliminarJuego(Juego j) throws Exception {
        ctjuego.eliminarJuego(j);
    }

    public void insertarJuego(Juego j) throws Exception {
        ctjuego.insertarJuego(j);
    }
    public List<Competicion> buscarCompeticionesPorJuego(Integer idJuego) throws Exception {
        return ctcompeticion.buscarCompeticionesPorJuego(idJuego);
    }
    public void vincularJuegoACompeticion(Integer idJuego, Integer idCompeticion) throws Exception{
        ctcompeticion.vincularJuegoACompeticion(idJuego, idCompeticion);
    }

    public void desvincularJuegoDeCompeticion(Integer idJuego, Integer idCompeticion) throws Exception{
        ctcompeticion.desvincularJuegoDeCompeticion(idJuego, idCompeticion);
    }

    public void insertarPatrocinador(Patrocinador p) throws Exception {ctpatrocinador.insertarPatrocinadores(p);}
    public void borrarPatrocinador(String nombre) throws Exception{ctpatrocinador.borrarPatrocinador(nombre);}
    public void editarPatrocinador(String nombreNuevo) throws Exception{ctpatrocinador.editarPatrocinador(nombreNuevo);}
    public Patrocinador buscarPatrocinadorEliminar(String nombre) throws Exception{return ctpatrocinador.buscarPatrocinadorEliminar(nombre);}

    public void crearAsistente(String nombre,String apellido1,String apellido2,Integer sueldo,String tipo) throws Exception {
        ctasistente.crearAsistente(nombre, apellido1, apellido2, sueldo, tipo);
    }

    public void crearEntrenador(String nombre,String apellido1,String apellido2,Integer sueldo,String tipo) throws Exception {
        ctasistente.crearEntrenador(nombre, apellido1, apellido2, sueldo, tipo);
    }
    public ArrayList<String> obtenerAsistentesPorEquipo(String nombreEquipo, String equipo) throws Exception {
        return ctasistente.obtenerAsistentesPorEquipo(nombreEquipo);
    }
    public void borrarAsistente(String nombreAsistente,String nombreEquipo) throws Exception {
        ctasistente.borrarAsistente(nombreAsistente,nombreEquipo);
    }
}