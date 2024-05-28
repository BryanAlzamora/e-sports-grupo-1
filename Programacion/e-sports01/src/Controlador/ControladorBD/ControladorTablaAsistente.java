package Controlador.ControladorBD;

import Modelo.Asistente;
import Modelo.Equipo;
import Modelo.Jugador;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * La clase ControladorTablaAsistente gestiona las operaciones relacionadas con la tabla de asistentes en la base de datos.
 */
public class ControladorTablaAsistente {

    private Connection con;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaAsistente(Connection con) {
        this.con = con;
    }

    /**
     * Crea un nuevo asistente en la base de datos.
     *
     * @param nombre    El nombre del asistente.
     * @param apellido1 El primer apellido del asistente.
     * @param apellido2 El segundo apellido del asistente.
     * @param sueldo    El sueldo del asistente.
     * @param tipo      El tipo de asistente.
     * @throws Exception Si ocurre un error durante la operación.
     */

    public void crearAsistente(String nombre,String apellido1,String apellido2,Integer sueldo,String tipo) throws Exception {
        PreparedStatement pstmt = null;

        try {
            // Crear la consulta de inserción
            String queryInsert = "INSERT INTO ASISTENTE (NOMBRE, APELLIDO1, APELLIDO2, SUELDO) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(queryInsert);

            // Establecer los parámetros
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido1);
            pstmt.setString(3, apellido2);
            pstmt.setInt(4, sueldo);

            // Ejecutar la consulta
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al crear el asistente", e);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
        }
    }

    /**
     * Crea un nuevo entrenador en la base de datos.
     *
     * @param nombre    El nombre del entrenador.
     * @param apellido1 El primer apellido del entrenador.
     * @param apellido2 El segundo apellido del entrenador.
     * @param sueldo    El sueldo del entrenador.
     * @param tipo      El tipo de entrenador.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public void crearEntrenador(String nombre,String apellido1,String apellido2,Integer sueldo,String tipo) throws Exception {
        PreparedStatement pstmt = null;

        try {
            // Crear la consulta de inserción
            String queryInsert = "INSERT INTO ENTRENADOR (NOMBRE, APELLIDO1, APELLIDO2, SUELDO) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(queryInsert);

            // Establecer los parámetros
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido1);
            pstmt.setString(3, apellido2);
            pstmt.setInt(4, sueldo);

            // Ejecutar la consulta
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al crear el entrenador", e);
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
        }
    }

    /**
     * Obtiene una lista de asistentes y entrenadores asociados a un equipo.
     *
     * @param nombreEquipo El nombre del equipo.
     * @return Una lista de nombres de asistentes y entrenadores.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public ArrayList<String> obtenerAsistentesPorEquipo(String nombreEquipo) throws Exception {
        ArrayList<String> asistentes = new ArrayList<>();

        String selectQuery = "SELECT A.NOMBRE " +
                "FROM ASISTENTE A " +
                "JOIN EQUIPO E ON A.ID_EQUIPO = E.ID_EQUIPO " +
                "WHERE E.NOMBRE = ? " +
                "UNION " +
                "SELECT T.NOMBRE " +
                "FROM ENTRENADOR T " +
                "JOIN EQUIPO E ON T.ID_EQUIPO = E.ID_EQUIPO " +
                "WHERE E.NOMBRE = ?";
        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
        selectStatement.setString(1, nombreEquipo);
        selectStatement.setString(2, nombreEquipo);
        ResultSet rs = selectStatement.executeQuery();

        while (rs.next()) {
            asistentes.add(rs.getString("NOMBRE"));
        }

        rs.close();
        selectStatement.close();

        return asistentes;
    }

    /**
     * Borra un asistente o entrenador asociado a un equipo de la base de datos.
     *
     * @param nombreAsistente El nombre del asistente o entrenador.
     * @param nombreEquipo    El nombre del equipo.
     * @throws Exception Si ocurre un error durante la operación o si no se encuentra al asistente o entrenador.
     */

    public void borrarAsistente(String nombreAsistente,String nombreEquipo) throws Exception {
        // Verificar si el nombre pertenece a un asistente
        String selectAsistenteQuery = "SELECT NOMBRE FROM ASISTENTE WHERE NOMBRE = ? AND ID_EQUIPO IN (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
        PreparedStatement selectAsistenteStatement = con.prepareStatement(selectAsistenteQuery);
        selectAsistenteStatement.setString(1, nombreAsistente);
        selectAsistenteStatement.setString(2, nombreEquipo);
        ResultSet asistenteResultSet = selectAsistenteStatement.executeQuery();

        // Verificar si el nombre pertenece a un entrenador
        String selectEntrenadorQuery = "SELECT NOMBRE FROM ENTRENADOR WHERE NOMBRE = ? AND ID_EQUIPO IN (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
        PreparedStatement selectEntrenadorStatement = con.prepareStatement(selectEntrenadorQuery);
        selectEntrenadorStatement.setString(1, nombreAsistente);
        selectEntrenadorStatement.setString(2, nombreEquipo);
        ResultSet entrenadorResultSet = selectEntrenadorStatement.executeQuery();

        if (asistenteResultSet.next()) {
            // Es un asistente, proceder con la eliminación
            String deleteAsistenteQuery = "DELETE FROM ASISTENTE WHERE NOMBRE = ? AND ID_EQUIPO IN (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
            PreparedStatement deleteAsistenteStatement = con.prepareStatement(deleteAsistenteQuery);
            deleteAsistenteStatement.setString(1, nombreAsistente);
            deleteAsistenteStatement.setString(2, nombreEquipo);
            int rowsDeleted = deleteAsistenteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null,"\"El asistente \" + nombreAsistente + \" ha sido eliminado correctamente.");
            }
        } else if (entrenadorResultSet.next()) {
            // Es un entrenador, proceder con la eliminación
            String deleteEntrenadorQuery = "DELETE FROM ENTRENADOR WHERE NOMBRE = ? AND ID_EQUIPO IN (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
            PreparedStatement deleteEntrenadorStatement = con.prepareStatement(deleteEntrenadorQuery);
            deleteEntrenadorStatement.setString(1, nombreAsistente);
            deleteEntrenadorStatement.setString(2, nombreEquipo);
            int rowsDeleted = deleteEntrenadorStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null,"El entrenador " + nombreAsistente + " ha sido eliminado correctamente.");
            }
        } else {
            // No se encontró el nombre en ninguna de las tablas
            throw new Exception("No se encontró al usuario en la base de datos.");
        }
    }
}