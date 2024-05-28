package Controlador.ControladorVista;

import Modelo.Competicion;
import Modelo.Juego;
import Vista.AccionRealizada;
import Vista.VentanaCompeticiones;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
/**
 * ControladorVCompeticion controla la interacción entre la vista de competiciones y el modelo de datos.
 */
public class ControladorVCompeticion {

    private ControladorVista cv;
    private Connection con;
    private VentanaCompeticiones vCompeticiones;
    private List<Juego> listaJuegos;
    private List<Competicion> listaCompeticiones;
    private List<String> listaNombreCometiciones;
    private AccionRealizada aRealizada;
    /**
     * Constructor de ControladorVCompeticion.
     *
     * @param cv La instancia del controlador de la vista principal.
     */
    public ControladorVCompeticion(ControladorVista cv) {
        this.cv = cv;
    }
    /**
     * Crea y muestra la ventana de competiciones.
     */
    public void crearMostrar() {
        vCompeticiones = new VentanaCompeticiones();
        aRealizada=new AccionRealizada();
        aRealizada.addRealizadaAL(new RealizadaAL());
        vCompeticiones.setVisible(true);
        vCompeticiones.addVolver(new BVolverAL());
        vCompeticiones.addInicio(new BInicioAL());

        vCompeticiones.addrbNuevoAL(new RbNuevoAL());
        vCompeticiones.addrbEditarAL(new RbEditarAL());
        vCompeticiones.addrbEliminarAL(new RbEliminarAL());
        vCompeticiones.limpiar();
        vCompeticiones.getpNuevo().setVisible(false);
        vCompeticiones.getpEditar().setVisible(false);
        vCompeticiones.getpEliminar().setVisible(false);
        vCompeticiones.addbAceptarAl(new bAceptarAl());
        vCompeticiones.addbAceptarEliminarAl(new bAceptarEliminarAl());
        vCompeticiones.addbAceptarEditarAl(new bAceptarEditarAl());
        vCompeticiones.addCbEditAL(new cbEditAL());
        llenarCombos();
    }
    /**
     * Llena los combos de la ventana de competiciones.
     */
    public void llenarCombos() {
        try {
            listaJuegos = cv.buscarJuegos();
            listaJuegos.forEach(o -> {
                vCompeticiones.getCbJuego().addItem(o.getNombre());
                System.out.println("Juego añadido al combo: " + o.getNombre());
            });


            listaNombreCometiciones = cv.buscarCompeticiones();
            for (String nombre : listaNombreCometiciones) {
                vCompeticiones.getCbCompeticion().addItem(nombre);
                vCompeticiones.getCbEditCompeticion().addItem(nombre);
                System.out.println("Competición añadida al combo: " + nombre);
            }
            vCompeticiones.getCbNuevoEstado().addItem("Abierto");
            vCompeticiones.getCbNuevoEstado().addItem("Cerrado");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listaJuegos.forEach(o -> vCompeticiones.getCbNuevoJuego().addItem(o.getNombre()));
    }
    /**
     * ActionListener para el botón de realizada.
     */
    public class RealizadaAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            aRealizada.dispose();
        }
    }
    /**
     * ActionListener para el botón de BVolverAL.
     */
    public class BVolverAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarEditar();
            vCompeticiones.dispose();
        }
    }
    /**
     * ActionListener para el botón de inicio.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVP();
            vCompeticiones.dispose();
        }
    }
    /**
     * ActionListener para el radio button de nuevo.
     */
    public class RbNuevoAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (vCompeticiones.getRbNuevo().isSelected()) {
                vCompeticiones.limpiar();
                vCompeticiones.getpNuevo().setVisible(true);
                vCompeticiones.getpEditar().setVisible(false);
                vCompeticiones.getpEliminar().setVisible(false);
            }
        }
    }
    /**
     * ActionListener para el radio button de RbEditarAL.
     */
    public class RbEditarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (vCompeticiones.getRbEditar().isSelected()) {
                vCompeticiones.limpiarEditar();
                vCompeticiones.getpNuevo().setVisible(false);
                vCompeticiones.getpEditar().setVisible(true);
                vCompeticiones.getpEliminar().setVisible(false);
                try {
                    listaJuegos = cv.buscarJuegos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                listaJuegos.forEach(o -> {
                    vCompeticiones.getCbNuevoJuego().addItem(o.getNombre());
                    System.out.println("Juego añadido al combo: " + o.getNombre());
                });
                vCompeticiones.getCbNuevoJuego().setSelectedIndex(-1);
            }
        }
    }
    /**
     * ActionListener para el radio button de eliminar.
     */
    public class RbEliminarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (vCompeticiones.getRbEliminar().isSelected()) {
                vCompeticiones.limpiar();
                vCompeticiones.getpNuevo().setVisible(false);
                vCompeticiones.getpEditar().setVisible(false);
                vCompeticiones.getpEliminar().setVisible(true);
            }
        }
    }
    /**
     * ActionListener para el radio button de aceptar.
     */
    private class bAceptarAl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vCompeticiones.getRbNuevo().isSelected()) {
                    String nombre = vCompeticiones.getTfNombre().getText();
                    String fechaInicio = vCompeticiones.getTfFechaInicio().getText();
                    String fechaFin = vCompeticiones.getTfFechaFin().getText();
                    System.out.println(fechaInicio);
                    System.out.println(fechaFin);

                    if (nombre.isEmpty()) {
                        throw new Exception("El nombre de la competicion no puede estar vacia");
                    }
                    if (fechaInicio.isEmpty()) {
                        throw new Exception("La fecha de inicio no puede estar vacia");
                    }

                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDate fechaIni = LocalDate.parse(fechaInicio, formato);


                    if (fechaFin.isEmpty()) {
                        throw new Exception("La fecha de fin no puede estar vacia");
                    }
                    LocalDate fechaFinal = LocalDate.parse(fechaFin, formato);
                    String nombreJuego = vCompeticiones.getCbJuego().getSelectedItem().toString();
                    Juego j = cv.buscarJuego(nombreJuego);

                    System.out.println(j.getIdJuego());
                    Competicion c = new Competicion(nombre, fechaIni, fechaFinal, "abierto",j);
                    System.out.println(c.getNombre());

                    cv.insertarCompeticion(c);
                    aRealizada.getEdicionTexto().setText("¡COMPETICIÓN INSERTADA!");
                    aRealizada.setVisible(true);
                }
            } catch (Exception ex) {
                vCompeticiones.mostrar(ex.getMessage());
            }
        }
    }
    /**
     * ActionListener para el radio button de eliminar.
     */
    private class bAceptarEliminarAl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vCompeticiones.getRbEliminar().isSelected()) {
                    Competicion c= cv.buscarCompeticion(vCompeticiones.getCbCompeticion().getSelectedItem().toString());

                    cv.eliminarCompeticion(c);
                    aRealizada.getEdicionTexto().setText("¡COMPETICIÓN ELIMINADA!");
                    aRealizada.setVisible(true);
                }
            } catch (Exception ex) {
                vCompeticiones.mostrar(ex.getMessage());
            }
        }
    }
    /**
     * ActionListener para el radio button de aceptar editar.
     */
    private class bAceptarEditarAl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vCompeticiones.getRbEditar().isSelected()) {
                    String nombreCompeticion = vCompeticiones.getCbEditCompeticion().getSelectedItem().toString();
                    Competicion competicion = cv.buscarCompeticion(nombreCompeticion);

                    // Obtener los nuevos valores de los campos de texto
                    String nuevoNombre = vCompeticiones.getTfNuevoNombre().getText();
                    String nuevaFechaInicio = vCompeticiones.getTfNuevaFechaIni().getText();
                    String nuevaFechaFin = vCompeticiones.getTfNuevaFechaFin().getText();

                    // Obtener los nuevos valores de las ComboBox
                    String nuevoEstado = vCompeticiones.getCbNuevoEstado().getSelectedItem().toString().toLowerCase();
                    String nuevoJuego = vCompeticiones.getCbNuevoJuego().getSelectedItem().toString();

                    // Verificar si los campos están vacíos
                    if (nuevoNombre.isEmpty() || nuevaFechaInicio.isEmpty() || nuevaFechaFin.isEmpty()) {
                        throw new Exception("Todos los campos son obligatorios");
                    }

                    // Convertir las fechas al formato adecuado
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fechaIni = LocalDate.parse(nuevaFechaInicio, formato);
                    LocalDate fechaFin = LocalDate.parse(nuevaFechaFin, formato);

                    // Obtener el ID del juego basado en el nombre del juego
                    int idJuego = cv.buscarIdJuegoPorNombre(nuevoJuego);

                    // Actualizar la competición usando el procedimiento almacenado
                    cv.modificarCompeticion(competicion.getIdCompeticion(), nuevoNombre, Date.valueOf(fechaIni), Date.valueOf(fechaFin), nuevoEstado, idJuego);

                    aRealizada.getEdicionTexto().setText("¡COMPETICIÓN EDITADA!");
                    aRealizada.setVisible(true);
                }
            } catch (Exception ex) {
                vCompeticiones.mostrar(ex.getMessage());
            }
        }
    }
    /**
     * ComboBox para cbEditAL.
     */
    public class cbEditAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vCompeticiones.getRbEditar().isSelected()) {
                    if (vCompeticiones.getCbEditCompeticion().getSelectedItem() != null) {
                        String nombreCompeticion = vCompeticiones.getCbEditCompeticion().getSelectedItem().toString();
                        System.out.println(nombreCompeticion);

                        Competicion competicion = cv.buscarCompeticion(nombreCompeticion);

                        // Rellenar los campos de texto con los detalles de la competición seleccionada
                        vCompeticiones.getTfNuevoNombre().setText(competicion.getNombre());
                        vCompeticiones.getTfNuevaFechaIni().setText(competicion.getFechaInicio().toString());
                        vCompeticiones.getTfNuevaFechaFin().setText(competicion.getFechaFin().toString());

                        // Obtener el estado de la competición
                        String estadoCompeticion = competicion.getEstado();
                        System.out.println("Estado de la competición: " + estadoCompeticion);
                        if (estadoCompeticion.equalsIgnoreCase("cerrado")) {
                            //vCompeticiones.getCbNuevoEstado().setSelectedItem(estadoCompeticion);
                            vCompeticiones.getCbNuevoEstado().setSelectedIndex(1);
                        }else{
                            vCompeticiones.getCbNuevoEstado().setSelectedIndex(0);
                        }
                        vCompeticiones.getCbNuevoJuego().setSelectedItem(competicion.getJuego().getNombre().toString());
                    }
                }
            } catch (Exception ex) {
                vCompeticiones.mostrar(ex.getMessage());
            }
        }
    }
}