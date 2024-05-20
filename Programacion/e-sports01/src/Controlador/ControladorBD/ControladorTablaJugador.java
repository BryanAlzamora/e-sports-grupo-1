package Controlador.ControladorBD;

import Modelo.Jugador;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;

public class ControladorTablaJugador {
    private Connection con;
    private ControladorBD cb;

    public ControladorTablaJugador(Connection con) {
        this.con = con;
    }


    public List<Jugador> llenarJugadores(Integer equipo) throws Exception {

        List<Jugador> listaJugadores=new ArrayList<>();
        String plantilla5 = "SELECT id_integrante, nombre, apellido1, apellido2, sueldo, nacionalidad, fecha_nacimiento, nickname, rol, id_equipo FROM jugador WHERE id_equipo=?";

        PreparedStatement statement = con.prepareStatement(plantilla5);
        statement.setInt(1, equipo);
        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            Jugador jugador=new Jugador();
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
            System.out.println(rs.getString("nombre"));
            System.out.println(rs.getString("id_equipo"));
        }

        statement.close();
        return listaJugadores;
    }


public void crearJugador(String nombre, String primerApellido, String segundoApellido, Integer sueldo, String nacionalidad, LocalDate fechaNacimiento, String nickname, String rol , String equipo) throws Exception {
    System.out.println(nombre + primerApellido + segundoApellido + sueldo + nacionalidad + fechaNacimiento + nickname + rol + equipo );

    // Verificar si el jugador ya existe en la base de datos antes de insertarlo
    if (jugadorExiste(nombre, primerApellido, segundoApellido, fechaNacimiento)) {
        JOptionPane.showMessageDialog(null, "El jugador ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);

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

// Método para verificar si un jugador ya existe en la base de datos
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

    public List<Jugador> llenarJugadoresNombre(String equiposelecionado) throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String consulta = "SELECT j.nombre FROM JUGADOR j JOIN EQUIPO e ON j.ID_EQUIPO = e.ID_EQUIPO WHERE e.NOMBRE = ?";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, equiposelecionado);
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
    public  void eliminarJugador(String nombre, String equipo) throws SQLException {
        String consulta = "DELETE FROM JUGADOR WHERE NOMBRE = ? AND ID_EQUIPO = (SELECT ID_EQUIPO FROM EQUIPO WHERE NOMBRE = ?)";
        PreparedStatement stmt = con.prepareStatement(consulta);
        stmt.setString(1, nombre);
        stmt.setString(2, equipo);
        int rowsAffected = stmt.executeUpdate();

        stmt.close();
        if (rowsAffected == 0) {
            throw new SQLException("No se encontró el jugador o el equipo especificado.");
        }
        else {
            JOptionPane.showMessageDialog(null,"el usuario se ha borrado exitosamente");
            }
    }

    public Jugador actualizarJugador(String nombre , String equipo) throws Exception {
        Jugador jugador ;
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
            //jugador2.setEquipo(rs.getString("equipo"));
        } else {
            throw new Exception("No se encontró al jugador en la base de datos.");
        }

        return jugador;
    }

}
