package Controlador.ControladorBD;

import Modelo.Equipo;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase gestiona las operaciones relacionadas con la tabla de equipos en la base de datos.
 */
public class ControladorTablaEquipo {
    private Connection con;
    private ControladorBD cb;

    /**
     * Constructor de la clase ControladorTablaEquipo.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaEquipo(Connection con) {
        this.con = con;
    }

    /**
     * Obtiene el nombre de un equipo específico.
     *
     * @return El nombre del equipo.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public String llenarNombre() throws Exception {
        String nombre = null;

        String plantilla = "SELECT nombre FROM equipo WHERE id_equipo=1";

        PreparedStatement statement = con.prepareStatement(plantilla);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            nombre = rs.getString("nombre");
        }

        statement.close();

        return nombre;
    }

    /**
     * Obtiene la cantidad de equipos registrados en la base de datos.
     *
     * @return La cantidad de equipos.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public Integer cantidadEquipos() throws Exception {
        Integer cantidad = 0;

        String plantilla = "SELECT COUNT(*) AS CANTIDAD FROM EQUIPO";

        PreparedStatement statement = con.prepareStatement(plantilla);
        ResultSet rs = statement.executeQuery();

        // Verificar si hay algún resultado
        if (rs.next()) {
            cantidad = rs.getInt("CANTIDAD");
            System.out.println(rs.getString("CANTIDAD"));
        }

        statement.close();
        return cantidad;
    }

    /**
     * Obtiene la lista de equipos registrados en la base de datos.
     *
     * @return La lista de equipos.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public List<Equipo> llenarEquipos() throws Exception {
        List<Equipo> llenarEquipos = new ArrayList<>();

        String plantilla = "SELECT id_equipo, nombre, fecha_fundacion, id_patrocinador FROM EQUIPO";

        PreparedStatement statement = con.prepareStatement(plantilla);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setIdEquipo(rs.getInt("id_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            llenarEquipos.add(equipo);
            System.out.println(rs.getString("nombre"));
            System.out.println(rs.getString("id_equipo"));
        }

        statement.close();
        return llenarEquipos;
    }

    /**
     * Obtiene la lista de equipos registrados en la base de datos filtrados por su ID.
     *
     * @param idequipo El ID del equipo a buscar.
     * @return La lista de equipos que coinciden con el ID proporcionado.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public List<Equipo> llenarEquiposporID(String idequipo) throws Exception {
        List<Equipo> llenarEquipos = new ArrayList<>();

        String plantilla = "SELECT id_equipo, nombre, fecha_fundacion, id_patrocinador FROM EQUIPO WHERE id_equipo=?";

        PreparedStatement statement = con.prepareStatement(plantilla);
        statement.setString(1, idequipo);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            Equipo equipo = new Equipo();
            equipo.setIdEquipo(rs.getInt("id_equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            llenarEquipos.add(equipo);
            System.out.println(rs.getString("nombre"));
            System.out.println(rs.getString("id_equipo"));
        }

        statement.close();
        return llenarEquipos;
    }

    /**
     * Busca un equipo por su ID.
     *
     * @param idequipo El ID del equipo a buscar.
     * @return El equipo encontrado.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public Equipo buscarEquipoInt(Integer idequipo) throws Exception {
        Equipo equipo = new Equipo();

        String plantilla = "SELECT id_equipo, nombre, fecha_fundacion, id_patrocinador FROM EQUIPO WHERE id_equipo=?";

        PreparedStatement statement = con.prepareStatement(plantilla);
        statement.setInt(1, idequipo);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            equipo.setIdEquipo(rs.getInt("id_Equipo"));
            equipo.setNombre(rs.getString("nombre"));
            equipo.setFechaFundacion(rs.getDate("fecha_fundacion").toLocalDate());
            //Integer id_patrocinador = rs.getInt("id_patrocinadpr");
            //equipo.setPatrocinador(cb.buscarPatrocinador(id_patrocinador));
        }

        statement.close();
        return equipo;
    }

    /**
     * Obtiene una lista de nombres de equipos.
     *
     * @param nombre El nombre del equipo.
     * @return Una lista de nombres de equipos.
     * @throws Exception Si ocurre un error durante la consulta.
     */
    public ArrayList selectEquipo(String nombre) throws Exception {
        ArrayList<Equipo> equipos = new ArrayList<>();

        String plantilla = "SELECT nombre FROM equipo";
        PreparedStatement selectEquipos = con.prepareStatement(plantilla);
        ResultSet rs = selectEquipos.executeQuery();

        while (rs.next()) {
            Equipo equipo = new Equipo(); // Crear un nuevo objeto Equipo en cada iteración
            equipo.setNombre(rs.getString("nombre"));
            equipos.add(equipo);

        }
        rs.close();
        selectEquipos.close();

        return equipos;

    }

    /**
     * Crea un nuevo equipo en la base de datos.
     *
     * @param nombre      El nombre del equipo.
     * @param fecha       La fecha de fundación del equipo.
     * @param patrocinador El nombre del patrocinador del equipo.
     * @param competicion La competición asociada al equipo.
     * @throws Exception Si ocurre un error durante la creación del equipo.
     */
    public void crearEquipo(String nombre, LocalDate fecha, String patrocinador, String competicion) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            // Verificar si el equipo ya existe
            String queryCheck = "SELECT COUNT(*) FROM EQUIPO WHERE NOMBRE = ?";
            pstmt = con.prepareStatement(queryCheck);
            pstmt.setString(1, nombre);
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                JOptionPane.showMessageDialog(null, "el equipo ya existe");
            }

            // Obtener ID del patrocinador
            String queryPatrocinador = "SELECT ID_PATROCINADOR FROM PATROCINADOR WHERE NOMBRE = ?";
            pstmt = con.prepareStatement(queryPatrocinador);
            pstmt.setString(1, patrocinador);
            rs = pstmt.executeQuery();

            int idPatrocinador = -1;
            if (rs.next()) {
                idPatrocinador = rs.getInt("ID_PATROCINADOR");
            } else {
                throw new Exception("Patrocinador no encontrado");
            }

            // Obtener ID de la competición
            String queryCompeticion = "SELECT ID_COMPETICION FROM COMPETICION WHERE NOMBRE = ?";
            pstmt = con.prepareStatement(queryCompeticion);
            pstmt.setString(1, competicion);
            rs = pstmt.executeQuery();

            int idCompeticion = -1;
            if (rs.next()) {
                idCompeticion = rs.getInt("ID_COMPETICION");
            } else {
                throw new Exception("Competición no encontrada");
            }

            // Insertar el nuevo equipo
            String queryInsert = "INSERT INTO EQUIPO (NOMBRE, FECHA_FUNDACION, ID_PATROCINADOR) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(queryInsert);
            pstmt.setString(1, nombre);
            pstmt.setDate(2, java.sql.Date.valueOf(fecha)); // convertir LocalDate a java.sql.Date
            pstmt.setInt(3, idPatrocinador);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eel equipo ha sido creado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al crear el equipo");
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ignore) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ignore) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Borra un equipo de la base de datos.
     *
     * @param nombre El nombre del equipo a borrar.
     * @throws SQLException Si ocurre un error durante el borrado del equipo.
     */
    public void borrarEquipo(String nombre) throws SQLException {
        String query = "DELETE FROM EQUIPO WHERE NOMBRE = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, nombre);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró un equipo con el nombre especificado.");
            } else {
                JOptionPane.showMessageDialog(null, "el equipo se borro correctamente ");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al borrar el equipo: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un equipo por su nombre en la base de datos.
     *
     * @param nombre El nombre del equipo a buscar.
     * @return El equipo encontrado.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public Equipo buscarEquipo(String nombre) throws SQLException {
        String query = "SELECT E.*, P.NOMBRE AS NOMBRE_PATROCINADOR " +
                "FROM EQUIPO E " +
                "JOIN PATROCINADOR P ON E.ID_PATROCINADOR = P.ID_PATROCINADOR " +
                "WHERE E.NOMBRE = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, nombre);
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setNombre(rs.getString("NOMBRE"));
                equipo.setFechaFundacion(rs.getDate("FECHA_FUNDACION").toLocalDate());
                //equipo.setPatrocinador(rs.getString("NOMBRE"));
                return equipo;
            } else {
                return null; // No se encontró el equipo con el nombre dado
            }
        }
    }

    /**
     * Edita un equipo en la base de datos.
     *
     * @param nombreAntiguo  El nombre antiguo del equipo.
     * @param nombreNuevo    El nuevo nombre del equipo.
     * @param fechaCambio     La nueva fecha de fundación del equipo.
     * @param vincularNuevo  El nombre de la competición a la que se desea vincular el equipo.
     * @param desvincular    El nombre de la competición de la que se desea desvincular el equipo.
     * @throws Exception Si ocurre un error durante la edición del equipo.
     */
    public void editarEquipo(String nombreAntiguo, String nombreNuevo, LocalDate fechaCambio, String vincularNuevo, String desvincular) throws Exception {
        PreparedStatement stmt = null;

        try {
            // Actualizar nombre y fecha de fundación del equipo
            String updateEquipoSQL = "UPDATE EQUIPO SET NOMBRE = ?, FECHA_FUNDACION = ? WHERE NOMBRE = ?";
            stmt = con.prepareStatement(updateEquipoSQL);
            stmt.setString(1, nombreNuevo);
            stmt.setDate(2, java.sql.Date.valueOf(fechaCambio));
            stmt.setString(3, nombreAntiguo);
            stmt.executeUpdate();
            stmt.close();

            // Vincular a una nueva competición si se especifica
            if (vincularNuevo != null && !vincularNuevo.isEmpty()) {
                String selectCompeticionSQL = "SELECT ID_COMPETICION FROM COMPETICION WHERE NOMBRE = ?";
                stmt = con.prepareStatement(selectCompeticionSQL);
                stmt.setString(1, vincularNuevo);
                ResultSet rs = stmt.executeQuery();
                System.out.println("llega");
                if (rs.next()) {
                    int idCompeticion = rs.getInt("ID_COMPETICION");
                    rs.close();
                    stmt.close();

                    String selectEquipoSQL = "SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?";
                    stmt = con.prepareStatement(selectEquipoSQL);
                    stmt.setString(1, nombreNuevo);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        int idEquipo = rs.getInt("ID_EQUIPO");
                        rs.close();
                        stmt.close();

                        String insertClasificacionSQL = "INSERT INTO CLASIFICACION (ID_COMPETICION, ID_EQUIPO) VALUES (?, ?)";
                        stmt = con.prepareStatement(insertClasificacionSQL);
                        stmt.setInt(1, idCompeticion);
                        stmt.setInt(2, idEquipo);
                        stmt.executeUpdate();
                    }
                    rs.close();
                    stmt.close();
                }
            }

            // Desvincular de una competición si se especifica
            if (desvincular != null && !desvincular.isEmpty()) {
                String selectCompeticionSQL = "SELECT ID_COMPETICION FROM COMPETICION WHERE NOMBRE = ?";
                stmt = con.prepareStatement(selectCompeticionSQL);
                stmt.setString(1, desvincular);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int idCompeticion = rs.getInt("ID_COMPETICION");
                    rs.close();
                    stmt.close();

                    String selectEquipoSQL = "SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?";
                    stmt = con.prepareStatement(selectEquipoSQL);
                    stmt.setString(1, nombreNuevo);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        int idEquipo = rs.getInt("ID_EQUIPO");
                        rs.close();
                        stmt.close();

                        String deleteClasificacionSQL = "DELETE FROM CLASIFICACION WHERE ID_COMPETICION = ? AND ID_EQUIPO = ?";
                        stmt = con.prepareStatement(deleteClasificacionSQL);
                        stmt.setInt(1, idCompeticion);
                        stmt.setInt(2, idEquipo);
                        stmt.executeUpdate();

                    }
                    rs.close();
                    stmt.close();
                    JOptionPane.showMessageDialog(null, "El equipo se modifico exitosamente");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al editar el equipo: " + e.getMessage(), e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
