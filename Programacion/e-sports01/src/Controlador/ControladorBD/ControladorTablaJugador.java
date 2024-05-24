package Controlador.ControladorBD;

import Modelo.Jugador;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la tabla de jugadores en la base de datos.
 */
public class ControladorTablaJugador {
    private Connection con;
    private ControladorBD cb;

    /**
     * Constructor del controlador de la tabla de jugadores.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaJugador(Connection con) {
        this.con = con;
    }

    /**
     * Llena una lista de jugadores basados en el ID de equipo.
     *
     * @param equipo El ID del equipo.
     * @return Una lista de objetos Jugador.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public List<Jugador> llenarJugadores(Integer equipo) throws Exception {
        List<Jugador> listaJugadores = new ArrayList<>();
        String plantilla5 = "SELECT id_integrante, nombre, apellido1, apellido2, sueldo, nacionalidad, fecha_nacimiento, nickname, rol, id_equipo FROM jugador WHERE id_equipo=?";

        PreparedStatement statement = con.prepareStatement(plantilla5);
        statement.setInt(1, equipo);
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
            listaJugadores.add(jugador);
        }

        statement.close();
        return listaJugadores;
    }

    /**
     * Crea un nuevo jugador en la base de datos.
     *
     * @param nombre          El nombre del jugador.
     * @param primerApellido  El primer apellido del jugador.
     * @param segundoApellido El segundo apellido del jugador.
     * @param sueldo          El sueldo del jugador.
     * @param nacionalidad    La nacionalidad del jugador.
     * @param fechaNacimiento La fecha de nacimiento del jugador.
     * @param nickname        El nickname del jugador.
     * @param rol             El rol del jugador.
     * @param equipo          El equipo al que pertenece el jugador.
     * @throws Exception Si ocurre un error durante la creación.
     */
    public void crearJugador(String nombre, String primerApellido, String segundoApellido, Integer sueldo, String nacionalidad, LocalDate fechaNacimiento, String nickname, String rol, String equipo) throws Exception {
        // Verificar si el jugador ya existe en la base de datos antes de insertarlo
        if (jugadorExiste(nombre, primerApellido, segundoApellido, fechaNacimiento)) {
            JOptionPane.showMessageDialog(null, "El jugador ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si el jugador no existe, proceder con la inserción
        String plantilla = "INSERT INTO JUGADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO, NACIONALIDAD, FECHA_NACIMIENTO, NICKNAME, ROL, ID_EQUIPO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement crearJugador = con.prepareStatement(plantilla);
        crearJugador.setString(1, nombre);
        crearJugador.setString(2, primerApellido);
        crearJugador.setString(3, segundoApellido);
        crearJugador.setInt(4, sueldo);
        crearJugador.setString(5, nacionalidad);
        crearJugador.setDate(6, Date.valueOf(fechaNacimiento));
        crearJugador.setString(7, nickname);
        crearJugador.setString(8, rol);
        crearJugador.setString(9, equipo);

        int filasAfectadas = crearJugador.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "El jugador " + nombre + " ha sido insertado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al insertar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        crearJugador.close();
    }

    /**
     * Verifica si un jugador ya existe en la base de datos.
     *
     * @param nombre          El nombre del jugador.
     * @param primerApellido  El primer apellido del jugador.
     * @param segundoApellido El segundo apellido del jugador.
     * @param fechaNacimiento La fecha de nacimiento del jugador.
     * @return true si el jugador existe, false en caso contrario.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    private boolean jugadorExiste(String nombre, String primerApellido, String segundoApellido, LocalDate fechaNacimiento) throws Exception {
        String consulta = "SELECT COUNT(*) FROM JUGADOR WHERE NOMBRE = ? AND APELLIDO1 = ? AND APELLIDO2 = ? AND FECHA_NACIMIENTO = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, nombre);
        stmt.setString(2, primerApellido);
        stmt.setString(3, segundoApellido);
        stmt.setDate(4, Date.valueOf(fechaNacimiento));

        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();

        return count > 0;
    }

    /**
     * Llena una lista de jugadores basados en el nombre del equipo.
     *
     * @param equipoSeleccionado El nombre del equipo seleccionado.
     * @return Una lista de objetos Jugador.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public List<Jugador> llenarJugadoresNombre(String equipoSeleccionado) throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String consulta = "SELECT j.nombre FROM JUGADOR j JOIN EQUIPO e ON j.ID_EQUIPO = e.ID_EQUIPO WHERE e.NOMBRE = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, equipoSeleccionado);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Jugador jugador = new Jugador();
            jugador.setNombre(rs.getString("nombre"));
            jugadores.add(jugador);
        }
        rs.close();
        stmt.close();
        return jugadores;
    }

    /**
     * Elimina un jugador basado en su nombre y equipo.
     *
     * @param nombre El nombre del jugador.
     * @param equipo El nombre del equipo.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarJugador(String nombre, String equipo) throws SQLException {
        String consulta = "DELETE FROM JUGADOR WHERE NOMBRE = ? AND ID_EQUIPO = (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, nombre);
        stmt.setString(2, equipo);
        int rowsAffected = stmt.executeUpdate();

        stmt.close();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró el jugador o el equipo especificado.");
        } else {
            JOptionPane.showMessageDialog(null, "El jugador se ha borrado exitosamente.");
        }
    }

    /**
     * Actualiza la información de un jugador.
     *
     * @param nombre El nombre del jugador.
     * @param equipo El nombre del equipo.
     * @return El objeto Jugador actualizado.
     * @throws Exception Si ocurre un error durante la actualización.
     */
    public Jugador actualizarJugador(String nombre, String equipo) throws Exception {
        Jugador jugador;
        String selectQuery = "SELECT J.NOMBRE, J.APELLIDO1, J.APELLIDO2, J.SUELDO, J.NACIONALIDAD, J.FECHA_NACIMIENTO, J.NICKNAME, J.ROL, E.nombre" +
                " FROM JUGADOR J\n" +
                " JOIN EQUIPO E ON J.ID_EQUIPO = E.ID_EQUIPO\n" +
                " WHERE J.NOMBRE = ? AND E.NOMBRE=?\n ";
        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
        selectStatement.setString(1, nombre);
        selectStatement.setString(2, equipo);
        ResultSet rs = selectStatement.executeQuery();

        if (rs.next()) {
            jugador = new Jugador();
            jugador.setNombre(rs.getString("nombre"));
            jugador.setApellido1(rs.getString("apellido1"));
            jugador.setApellido2(rs.getString("apellido2"));
            jugador.setSueldo((double) rs.getInt("sueldo"));
            jugador.setNacionalidad(rs.getString("nacionalidad"));
            jugador.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            jugador.setNickname(rs.getString("nickname"));
            jugador.setRol(rs.getString("rol"));
        } else {
            throw new Exception("No se encontró al jugador en la base de datos.");
        }

        return jugador;
    }

    /**
     * Edita la información de un jugador y la confirma.
     *
     * @param nombre         El nuevo nombre del jugador.
     * @param primerApellido El nuevo primer apellido del jugador.
     * @param segundoApellido El nuevo segundo apellido del jugador.
     * @param sueldo         El nuevo sueldo del jugador.
     * @param nacionalidad   La nueva nacionalidad del jugador.
     * @param fechaNacimiento La nueva fecha de nacimiento del jugador.
     * @param nickname       El nuevo nickname del jugador.
     * @param rol            El nuevo rol del jugador.
     * @param nuevoEquipo    El nuevo equipo del jugador.
     * @param nombreAntiguo  El nombre antiguo del jugador.
     * @param equipoAntiguo  El equipo antiguo del jugador.
     * @throws Exception Si ocurre un error durante la edición.
     */
    public void editarJugadorConfir(String nombre, String primerApellido, String segundoApellido, double sueldo, String nacionalidad, LocalDate fechaNacimiento, String nickname, String rol, String nuevoEquipo, String nombreAntiguo, String equipoAntiguo) throws Exception {
        String updateQuery = "UPDATE JUGADOR SET NOMBRE=?, APELLIDO1=?, APELLIDO2=?, SUELDO=?, NACIONALIDAD=?, FECHA_NACIMIENTO=?, NICKNAME=?, ROL=?, ID_EQUIPO=(SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE=?) WHERE NOMBRE=? AND ID_EQUIPO=(SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE=?)";

        // Preparar la declaración SQL
        PreparedStatement updateStatement = con.prepareStatement(updateQuery);

        // Establecer los valores de los parámetros en la consulta SQL
        updateStatement.setString(1, nombre);
        updateStatement.setString(2, primerApellido);
        updateStatement.setString(3, segundoApellido);
        updateStatement.setDouble(4, sueldo);
        updateStatement.setString(5, nacionalidad);
        updateStatement.setDate(6, Date.valueOf(fechaNacimiento));
        updateStatement.setString(7, nickname);
        updateStatement.setString(8, rol);
        updateStatement.setString(9, nuevoEquipo);
        updateStatement.setString(10, nombreAntiguo);
        updateStatement.setString(11, equipoAntiguo);

        // Ejecutar la consulta de actualización
        int filasAfectadas = updateStatement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "El jugador " + nombre + " ha sido editado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al editar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Cerrar la declaración y la conexión
        updateStatement.close();
    }
}


