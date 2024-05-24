package Controlador.ControladorVista;

import Modelo.Competicion;
import Modelo.Juego;
import Vista.VentanaCompeticiones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        llenarCombos();

    }

    /**
     * Llena los combos de la ventana de competiciones.
     */
    public void llenarCombos() {
        try {
            listaJuegos = cv.buscarJuegos();

            listaJuegos.forEach(o -> vCompeticiones.getCbJuego().addItem(o.getNombre()));

            listaNombreCometiciones = cv.buscarCompeticiones();
            for (String nombre : listaNombreCometiciones) {
                vCompeticiones.getCbCompeticion().addItem(nombre);
            }
            for (String nombre : listaNombreCometiciones) {
                vCompeticiones.getCbEditCompeticion().addItem(nombre);
            }
            vCompeticiones.getCbNuevoEstado().addItem("Abierto");
            vCompeticiones.getCbNuevoEstado().addItem("Cerrado");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ActionListener para el botón de volver.
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
     * ActionListener para el radio button de editar.
     */
    public class RbEditarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (vCompeticiones.getRbEditar().isSelected()) {
                vCompeticiones.limpiar();
                vCompeticiones.getpNuevo().setVisible(false);
                vCompeticiones.getpEditar().setVisible(true);
                vCompeticiones.getpEliminar().setVisible(false);
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
     * ActionListener para el botón de aceptar.
     */
    private class bAceptarAl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vCompeticiones.getRbNuevo().isSelected()) {
                    String nombre = vCompeticiones.getTfNombre().getText();
                    String fechaInicio = vCompeticiones.getTfFechaInicio().getText();
                    String fechaFin = vCompeticiones.getTfFechaFin().getText();

                    if (nombre.isEmpty()) {
                        throw new Exception("El nombre de la competicion no puede estar vacia");
                    }
                    if (fechaInicio.isEmpty()) {
                        throw new Exception("La fecha de inicio no puede estar vacia");
                    }
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaIni = LocalDate.parse(fechaInicio, formato);
                    if (fechaFin.isEmpty()) {
                        throw new Exception("La fecha de fin no puede estar vacia");
                    }
                    LocalDate fechaFinal = LocalDate.parse(fechaFin, formato);

                    String nombreJuego = vCompeticiones.getCbJuego().getSelectedItem().toString();
                    Juego j = cv.buscarJuego(nombreJuego);
                    Competicion c = new Competicion(nombre, fechaIni, fechaFinal, "Abierto", j);

                    cv.insertarCompeticion(c);
                }
                if (vCompeticiones.getRbEditar().isSelected()) {

                }
                if (vCompeticiones.getRbEliminar().isSelected()) {

                }
            } catch (Exception ex) {
                vCompeticiones.mostrar(ex.getMessage());
            }

        }
    }
}
