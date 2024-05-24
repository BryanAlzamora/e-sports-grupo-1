package Controlador.ControladorBD;

import Modelo.Patrocinador;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la tabla de patrocinadores en la base de datos.
 */
public class ControladorTablaPatrocinador {
    private Connection con;

    /**
     * Constructor del controlador de la tabla de patrocinadores.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaPatrocinador(Connection con) {
        this.con = con;
    }

    /**
     * Busca todos los patrocinadores en la base de datos.
     *
     * @return Una lista de nombres de patrocinadores.
     * @throws SQLException Si ocurre un error durante la búsqueda.
     */
    public List<String> buscarPatrocinador() throws SQLException {
        List<String> nombresPatrocinadores = new ArrayList<>();
        String query = "SELECT NOMBRE FROM PATROCINADOR";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            nombresPatrocinadores.add(rs.getString("NOMBRE"));
        }

        if (rs.next()) {
            Patrocinador patrocinador = new Patrocinador();
            patrocinador.setIdPatrocinador(rs.getInt("id_patrocinador"));
            patrocinador.setNombre(rs.getString("nombre"));
        }

        return nombresPatrocinadores;
    }

    /**
     * Crea un nuevo patrocinador en la base de datos.
     *
     * @param idPatrocinador El ID del patrocinador.
     * @param nombre         El nombre del patrocinador.
     * @throws SQLException Si ocurre un error durante la creación.
     */
    public void crearPatrocinador(Integer idPatrocinador, String nombre) throws SQLException {
        if (patrocinadorExiste(idPatrocinador, nombre)) {
            JOptionPane.showMessageDialog(null, "El patrocinador ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String plantilla = "INSERT INTO patrocinador (id_patrocinador, nombre) VALUES (?, ?)";
        PreparedStatement crearPatrocinador = con.prepareStatement(plantilla);
        crearPatrocinador.setInt(1, idPatrocinador);
        crearPatrocinador.setString(2, nombre);

        int filasAfectadas = crearPatrocinador.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "El patrocinador " + nombre + " ha sido insertado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al insertar el patrocinador.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        crearPatrocinador.close();
    }

    /**
     * Verifica si un patrocinador ya existe en la base de datos.
     *
     * @param idPatrocinador El ID del patrocinador.
     * @param nombre         El nombre del patrocinador.
     * @return true si el patrocinador existe, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la verificación.
     */
    private boolean patrocinadorExiste(Integer idPatrocinador, String nombre) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM patrocinador WHERE id_patrocinador = ? AND nombre = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setInt(1, idPatrocinador);
        stmt.setString(2, nombre);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();

        return count > 0;
    }

    /**
     * Llena una lista de patrocinadores basados en el nombre del equipo.
     *
     * @param equipoSeleccionado El nombre del equipo seleccionado.
     * @return Una lista de patrocinadores.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public List<Patrocinador> llenarPatrocinadorNombre(String equipoSeleccionado) throws SQLException {
        List<Patrocinador> patrocinadores = new ArrayList<>();
        String consulta = "SELECT p.nombre FROM patrocinador p JOIN equipo e ON p.id_patrocinador = e.id_patrocinador WHERE e.nombre = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, equipoSeleccionado);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Patrocinador patrocinador = new Patrocinador();
            patrocinador.setNombre(rs.getString("nombre"));
            patrocinadores.add(patrocinador);
        }
        rs.close();
        stmt.close();
        return patrocinadores;
    }

    /**
     * Elimina un patrocinador basado en su nombre y equipo.
     *
     * @param nombre El nombre del patrocinador.
     * @param equipo El nombre del equipo.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarPatrocinador(String nombre, String equipo) throws SQLException {
        String consulta = "DELETE FROM patrocinador WHERE nombre = ? AND id_patrocinador = (SELECT id_patrocinador FROM equipo WHERE nombre = ?)";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, nombre);
        stmt.setString(2, equipo);
        int rowsAffected = stmt.executeUpdate();

        stmt.close();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró el patrocinador o el equipo especificado.");
        } else {
            JOptionPane.showMessageDialog(null, "El patrocinador se ha borrado exitosamente.");
        }
    }

    /**
     * Actualiza la información de un patrocinador.
     *
     * @param nombre El nombre del patrocinador.
     * @param equipo El nombre del equipo.
     * @return El objeto Patrocinador actualizado.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public Patrocinador actualizarPatrocinador(String nombre, String equipo) throws SQLException {
        Patrocinador patrocinador;
        String selectQuery = "SELECT p.nombre, p.id_patrocinador FROM patrocinador p JOIN equipo e ON p.id_patrocinador = e.id_patrocinador WHERE p.nombre = ? AND e.nombre = ?";
        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
        selectStatement.setString(1, nombre);
        selectStatement.setString(2, equipo);
        ResultSet rs = selectStatement.executeQuery();

        if (rs.next()) {
            patrocinador = new Patrocinador();
            patrocinador.setNombre(rs.getString("nombre"));
            patrocinador.setIdPatrocinador(rs.getInt("id_patrocinador"));
        } else {
            throw new SQLException("No se encontró al patrocinador en la base de datos.");
        }

        rs.close();
        selectStatement.close();
        return patrocinador;
    }

    /**
     * Edita la información de un patrocinador y la confirma.
     *
     * @param nombre        El nuevo nombre del patrocinador.
     * @param nuevoEquipo   El nuevo equipo del patrocinador.
     * @param nombreAntiguo El nombre antiguo del patrocinador.
     * @param equipoAntiguo El equipo antiguo del patrocinador.
     * @throws SQLException Si ocurre un error durante la edición.
     */
    public void editarPatrocinadorConfir(String nombre, String nuevoEquipo, String nombreAntiguo, String equipoAntiguo) throws SQLException {
        String updateQuery = "UPDATE patrocinador SET nombre = ?, id_patrocinador = (SELECT id_patrocinador FROM equipo WHERE nombre = ?) WHERE nombre = ? AND id_patrocinador = (SELECT id_patrocinador FROM equipo WHERE nombre = ?)";
        PreparedStatement updateStatement = con.prepareStatement(updateQuery);

        updateStatement.setString(1, nombre);
        updateStatement.setString(2, nuevoEquipo);
        updateStatement.setString(3, nombreAntiguo);
        updateStatement.setString(4, equipoAntiguo);

        int filasAfectadas = updateStatement.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "El patrocinador " + nombre + " ha sido editado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al editar el patrocinador.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        updateStatement.close();
    }
}
