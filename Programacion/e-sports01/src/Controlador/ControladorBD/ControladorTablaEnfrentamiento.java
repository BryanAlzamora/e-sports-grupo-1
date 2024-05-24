package Controlador.ControladorBD;

import Modelo.Enfrentamiento;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase ControladorTablaEnfrentamiento proporciona métodos para interactuar con la tabla "enfrentamiento" en la base de datos.
 */
public class ControladorTablaEnfrentamiento {
    private Connection con;

    /**
     * Constructor de la clase ControladorTablaEnfrentamiento.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaEnfrentamiento(Connection con) {
        this.con = con;
    }

    /**
     * Busca los enfrentamientos asociados a una jornada de competición.
     *
     * @param idJorComp El ID de la jornada de competición.
     * @return Una lista de objetos Enfrentamiento.
     * @throws Exception Si ocurre un error durante la búsqueda de los enfrentamientos.
     */
    public List<Enfrentamiento> buscarEnfrentamientos(Integer idJorComp) throws Exception {
        List<Enfrentamiento> enfrentamientos = new ArrayList<>();
        String sql = "SELECT * FROM enfrentamiento WHERE id_jor_comp = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idJorComp);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Enfrentamiento enfrentamiento = new Enfrentamiento();
            enfrentamiento.setIdEnfrentamiento(rs.getInt("id_enfrentamiento"));
            enfrentamiento.setIdEnfJor(rs.getInt("id_enf_jor"));
            enfrentamiento.setIdJorComp(rs.getInt("id_jor_comp"));
            enfrentamiento.setHora(rs.getString("hora"));
            enfrentamientos.add(enfrentamiento);
        }
        rs.close();
        ps.close();

        return enfrentamientos;
    }

    /**
     * Obtiene el ID del equipo local asociado a un enfrentamiento.
     *
     * @param id_enf_jor El ID del enfrentamiento.
     * @return El ID del equipo local.
     * @throws Exception Si ocurre un error durante la búsqueda del equipo local.
     */
    public Integer llenarLocal(Integer id_enf_jor) throws Exception {
        Integer id_equipo = null;
        String plantilla = "SELECT id_local FROM enfrentamiento WHERE id_enf_jor = ?";
        PreparedStatement statement = con.prepareStatement(plantilla);
        statement.setInt(1, id_enf_jor);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            id_equipo = rs.getInt("id_local");
        }

        rs.close();
        statement.close();
        return id_equipo;
    }

    /**
     * Obtiene el ID del equipo visitante asociado a un enfrentamiento.
     *
     * @param id_enf_jor El ID del enfrentamiento.
     * @return El ID del equipo visitante.
     * @throws Exception Si ocurre un error durante la búsqueda del equipo visitante.
     */
    public Integer llenarVisitante(Integer id_enf_jor) throws Exception {
        Integer id_equipo = null;

        String plantilla = "SELECT id_visitante FROM enfrentamiento WHERE id_enf_jor = ?";
        PreparedStatement statement = con.prepareStatement(plantilla);
        statement.setInt(1, id_enf_jor);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            id_equipo = rs.getInt("id_visitante");
        }

        rs.close();
        statement.close();
        return id_equipo;
    }

    /**
     * Guarda los resultados de un enfrentamiento en la base de datos.
     *
     * @param idEnfJor         El ID del enfrentamiento.
     * @param resultadoLocal    El resultado del equipo local.
     * @param resultadoVisitante El resultado del equipo visitante.
     * @throws Exception Si ocurre un error durante la actualización de los resultados.
     */
    public void guardarResultados(int idEnfJor, int resultadoLocal, int resultadoVisitante) throws Exception {
        String plantilla = "UPDATE enfrentamiento SET resultado_local = ?, resultado_visitante = ? WHERE id_enf_jor = ?";
        PreparedStatement statement = con.prepareStatement(plantilla);
        statement.setInt(1, resultadoLocal);
        statement.setInt(2, resultadoVisitante);
        statement.setInt(3, idEnfJor);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * Llena los campos de resultados de un enfrentamiento en un formulario.
     *
     * @param resultadoLocal     Campo de texto para el resultado local.
     * @param resultadoVisitante Campo de texto para el resultado visitante.
     * @param idEnfJor           El ID del enfrentamiento.
     */
    public void llenarResultados(JTextField resultadoLocal, JTextField resultadoVisitante, int idEnfJor) {
        try {
            String plantilla = "SELECT resultado_local, resultado_visitante FROM enfrentamiento WHERE id_enf_jor = ?";
            PreparedStatement statement = con.prepareStatement(plantilla);
            statement.setInt(1, idEnfJor);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int resLocal = rs.getInt("resultado_local");
                int resVisitante = rs.getInt("resultado_visitante");

                // Si los resultados no están definidos, mostramos "?"
                if (rs.wasNull()) {
                    resultadoLocal.setText("?");
                    resultadoVisitante.setText("?");
                } else {
                    resultadoLocal.setText(String.valueOf(resLocal));
                    resultadoVisitante.setText(String.valueOf(resVisitante));
                }
            } else {
                resultadoLocal.setText("?");
                resultadoVisitante.setText("?");
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
