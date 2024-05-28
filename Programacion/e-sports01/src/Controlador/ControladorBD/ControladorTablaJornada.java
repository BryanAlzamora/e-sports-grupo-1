package Controlador.ControladorBD;

import Modelo.Jornada;
import Modelo.Juego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTablaJornada {
    private Connection con;

    public ControladorTablaJornada(Connection con) {
        this.con = con;
    }

    /**
     * Busca todas las jornadas de una competición.
     *
     * @param idCompeticion El ID de la competición.
     * @return Una lista de IDs de las jornadas.
     * @throws Exception Si ocurre un error durante la consulta a la base de datos.
     */

    public List<Integer> buscarJornadas(Integer idCompeticion) throws Exception {
        String sql = "SELECT id_jornada FROM jornada WHERE id_competicion = ?";
        List<Integer> listaJornadas = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idCompeticion);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            listaJornadas.add(rs.getInt("id_jornada"));
        }

        rs.close();
        ps.close();
        return listaJornadas;
    }

    /**
     * Busca el ID de la jornada-competición en base a los IDs de la jornada y la competición.
     *
     * @param idJornada El ID de la jornada.
     * @param idCompeticion El ID de la competición.
     * @return El ID de la jornada-competición, o null si no se encuentra.
     * @throws Exception Si ocurre un error durante la consulta a la base de datos.
     */

    public Integer buscarIdJorComp(Integer idJornada, Integer idCompeticion) throws Exception {
        Integer idJorComp=null;
        String sql = "SELECT id_jor_comp FROM jornada WHERE id_jornada = ? AND id_competicion = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        // Mensajes de depuración para verificar los valores de los parámetros
        System.out.println("idJornadaaaa: " + idJornada);
        System.out.println("idCompeticion: " + idCompeticion);

        ps.setInt(1, idJornada);
        ps.setInt(2, idCompeticion);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            idJorComp = rs.getInt("id_jor_comp");
            System.out.println("idJorCompInt: " + idJorComp);
        }

        rs.close();
        ps.close();

        // Mensaje de depuración para verificar el tamaño de la lista
        System.out.println("idJorCompList: " + idJorComp);

        return idJorComp;
    }

    /**
     * Obtiene el ID de la competición en base al ID de la jornada-competición.
     *
     * @param idJorComp El ID de la jornada-competición.
     * @return El ID de la competición.
     * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
     */

    public Integer obtenerIdCompeticionDesdeJornada(int idJorComp) throws SQLException {
        String query = "SELECT id_competicion FROM jornada WHERE id_jor_comp = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, idJorComp);
        ResultSet rs = statement.executeQuery();
        int idCompeticion = -1;
        if (rs.next()) {
            idCompeticion = rs.getInt("id_competicion");
        }
        rs.close();
        statement.close();
        return idCompeticion;
    }

    /**
     * Obtiene el número de jornadas jugadas por un equipo en una competición.
     *
     * @param idEquipo El ID del equipo.
     * @param idCompeticion El ID de la competición.
     * @return El número de jornadas jugadas.
     * @throws Exception Si ocurre un error durante la consulta a la base de datos.
     */

    public int obtenerJornadasJugadas(int idEquipo, int idCompeticion) throws Exception {
        String query = "SELECT COUNT(*) AS jornadas_jugadas " +
                "FROM enfrentamiento e " +
                "JOIN jornada j ON e.id_jor_comp = j.id_jor_comp " +
                "WHERE (e.id_equipo_uno = ? OR e.id_equipo_dos = ?) " +
                "AND j.id_competicion = ? " +
                "AND (e.resultado_local IS NOT NULL OR e.resultado_visitante IS NOT NULL)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, idEquipo);
        statement.setInt(2, idEquipo);
        statement.setInt(3, idCompeticion);
        ResultSet rs = statement.executeQuery();
        int jornadasJugadas = 0;
        if (rs.next()) {
            jornadasJugadas = rs.getInt("jornadas_jugadas");
        }
        rs.close();
        statement.close();
        return jornadasJugadas;
    }


    /**
     * Busca el número de la última jornada registrada para una competición en la base de datos.
     *
     * @param idCompeticion El ID de la competición para la cual se desea buscar la última jornada.
     * @return El número de la última jornada registrada para la competición especificada.
     * @throws Exception Si ocurre un error al ejecutar la consulta SQL o si no se encuentra ninguna jornada para la competición especificada.
     */
    public Integer buscarUltimaJornada(Integer idCompeticion) throws Exception {
        Integer ultimaJornada = null;
        String query = "SELECT MAX(id_jornada) AS ultima_jornada FROM jornada WHERE id_competicion = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, idCompeticion);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            ultimaJornada = rs.getInt("ultima_jornada");
        }
        rs.close();
        statement.close();
        return ultimaJornada;
    }
}
