package Controlador.ControladorBD;

import Modelo.Clasificacion;
import Modelo.Enfrentamiento;
import Modelo.Equipo;
import Modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Esta clase gestiona las operaciones relacionadas con la tabla de clasificación en la base de datos.
 * Permite obtener los equipos que participan en una competición específica.
 *
 * @author Grupo1
 */
public class ControladorTablaClasificacion {
    private Connection con;

    /**
     * Constructor de la clase ControladorTablaClasificacion.
     *
     * @param con La conexión a la base de datos.
     */
    public ControladorTablaClasificacion(Connection con) {
        this.con = con;
    }

    /**
     * Llena una lista con los IDs de los equipos que participan en una competición específica.
     *
     * @param competicion El ID de la competición.
     * @return Una lista de IDs de equipos que participan en la competición.
     * @throws Exception Si ocurre un error durante la ejecución de la consulta.
     */
    public List<String> llenarEquiposCompeticion(Integer competicion) throws Exception {
        List<String> listaEquipos = new ArrayList<>();
        String plantilla5 = "SELECT id_equipo FROM clasificacion WHERE id_competicion=?";

        PreparedStatement statement = con.prepareStatement(plantilla5);
        statement.setInt(1, competicion);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            listaEquipos.add(rs.getString("id_equipo"));
            System.out.println(rs.getString("id_equipo"));
        }

        statement.close();
        return listaEquipos;
    }

    /**
     * Actualiza la tabla de clasificación con el resultado de un enfrentamiento.
     *
     * @param enfrentamiento     El enfrentamiento que contiene los equipos participantes.
     * @param idCompeticion      El identificador de la competición.
     * @param resultadoLocal     El resultado del equipo local.
     * @param resultadoVisitante El resultado del equipo visitante.
     * @throws Exception Si ocurre un error durante la actualización de la tabla de clasificación.
     */

    public void actualizarTablaClasificacion(Enfrentamiento enfrentamiento, int idCompeticion, int resultadoLocal, int resultadoVisitante) throws Exception {
        int puntosLocal = 0;
        int puntosVisitante = 0;

        if (resultadoLocal > resultadoVisitante) {
            puntosLocal = 3;
        } else if (resultadoLocal < resultadoVisitante) {
            puntosVisitante = 3;
        } else {
            puntosLocal = 1;
            puntosVisitante = 1;
        }

        // Actualizar puntos del equipo local
        String plantillaLocal = "UPDATE clasificacion SET puntos = puntos + ? WHERE id_equipo = ? AND id_competicion = ?";
        PreparedStatement statementLocal = con.prepareStatement(plantillaLocal);
        statementLocal.setInt(1, puntosLocal);
        statementLocal.setInt(2, enfrentamiento.getEquipoUno().getIdEquipo());
        statementLocal.setInt(3, idCompeticion);
        statementLocal.executeUpdate();
        statementLocal.close();

        // Actualizar puntos del equipo visitante
        String plantillaVisitante = "UPDATE clasificacion SET puntos = puntos + ? WHERE id_equipo = ? AND id_competicion = ?";
        PreparedStatement statementVisitante = con.prepareStatement(plantillaVisitante);
        statementVisitante.setInt(1, puntosVisitante);
        statementVisitante.setInt(2, enfrentamiento.getEquipoDos().getIdEquipo());
        statementVisitante.setInt(3, idCompeticion);
        statementVisitante.executeUpdate();
        statementVisitante.close();
    }
    /*
        // Método para obtener la clasificación de una competición específica
        public Clasificacion obtenerClasificacion(Integer idCompeticion) throws Exception {
            String query = "SELECT c.id_equipo, e.nombre, c.puntos " +
                    "FROM clasificacion c " +
                    "JOIN equipo e ON c.id_equipo = e.id_equipo " +
                    "WHERE c.id_competicion = ? " +
                    "ORDER BY c.puntos DESC";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, idCompeticion);
            ResultSet rs = statement.executeQuery();

            List<Clasificacion> listaClasificaciones = new ArrayList<>();
            List<Equipo> listaEquipo = new ArrayList<>();
            List<String> listaPuntos = new ArrayList<>();

            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(rs.getInt("id_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                listaEquipo.add(equipo);
                listaPuntos.add(String.valueOf(rs.getInt("puntos")));
            }
            rs.close();
            statement.close();

            //Competicion competicion = obtenerCompeticionPorId(idCompeticion);
            Clasificacion clasificacion = new Clasificacion(null, listaPuntos, null, listaEquipo);
            listaClasificaciones.add(clasificacion);

            return listaClasificaciones;
        }

     */
    /*
    public List<Integer> obtenerClasificacionID(Integer idCompeticion) throws Exception {
        List<Integer> listIds=null;
        String fila = "SELECT id_clasificacion FROM clasificacion WHERE id_competicion =? ORDER BY puntos DESC";

        PreparedStatement statement = con.prepareStatement(fila);
        statement.setInt(1, idCompeticion);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            listIds.add(rs.getInt("id_clasificacion"));
            System.out.print(rs.getInt("id_clasificacion") + " el id clasificacion");
        }
        rs.close();
        statement.close();

        return listIds;
    }
    public List<Integer> obtenerClasificacionIDequipo(Integer idCompeticion) throws Exception {
        List<Integer> listIds=null;
        String fila = "SELECT id_equipo FROM clasificacion WHERE id_competicion =? ORDER BY puntos DESC";

        PreparedStatement statement = con.prepareStatement(fila);
        statement.setInt(1, idCompeticion);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            listIds.add(rs.getInt("id_equipo"));
            System.out.print(rs.getInt("id_equipo") + " el id equipo");
        }
        rs.close();
        statement.close();

        return listIds;
    }
    public List<Integer> obtenerClasificacionPuntos(Integer idCompeticion) throws Exception {
        List<Integer> listIds=null;
        String fila = "SELECT puntos FROM clasificacion WHERE id_competicion =? ORDER BY puntos DESC";

        PreparedStatement statement = con.prepareStatement(fila);
        statement.setInt(1, idCompeticion);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            listIds.add(rs.getInt("puntos"));
            System.out.print(rs.getInt("puntos") + " los puntos");
        }
        rs.close();
        statement.close();

        return listIds;
    }
     */

    /**
     * Obtiene la lista de clasificaciones para una competición específica.
     *
     * @param idCompeticion El identificador de la competición.
     * @return Una lista de objetos Clasificacion que contienen el nombre del equipo y los puntos.
     * @throws Exception Si ocurre un error durante la consulta de la base de datos.
     */

    public List<Clasificacion> obtenerClasificacionesPorCompeticion(Integer idCompeticion) throws Exception {
        String query = "SELECT e.nombre, c.puntos FROM clasificacion c " +
                "JOIN equipo e ON e.id_equipo = c.id_equipo " +
                "WHERE c.id_competicion = ? " +
                "ORDER BY c.puntos DESC";

        List<Clasificacion> listaClasificaciones = new ArrayList<>();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, idCompeticion);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String nombreEquipo = rs.getString("nombre");
            int puntos = rs.getInt("puntos");
            Equipo equipo = new Equipo();
            equipo.setNombre(nombreEquipo);

            Clasificacion clasificacion = new Clasificacion(null, Collections.singletonList(String.valueOf(puntos)), null, Collections.singletonList(equipo));
            listaClasificaciones.add(clasificacion);
        }

        rs.close();
        statement.close();

        return listaClasificaciones;
    }
}
