package Controlador.ControladorVista;

import Controlador.ControladorBD.ControladorTablaEquipo;
import Vista.VentanaEditar;
import Vista.VentanaEquipos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * ControladorVEditar gestiona la interacción entre la vista de edición y el resto de la aplicación.
 */
public class ControladorVEditar {
    private ControladorVista cv;
    private VentanaEquipos veq;
    private ControladorTablaEquipo ctequipo;
    private Connection con;
    private VentanaEditar vEditar;

    /**
     * Constructor de ControladorVEditar.
     *
     * @param cv La instancia del controlador de la vista principal.
     */
    public ControladorVEditar(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Crea y muestra la ventana de edición.
     */
    public void crearMostrar() {
        vEditar = new VentanaEditar();
        vEditar.setVisible(true);
        vEditar.addPatrocinadores(new BPatrocinadoresAL());
        vEditar.addEquipos(new BEquiposAL());
        vEditar.addJugadores(new BJugadoresAL());
        vEditar.addJuegos(new BJuegosAL());
        vEditar.addStaff(new BStaffAL());
        vEditar.addCompeticiones(new BCompeticionesAL());

        vEditar.addVolver(new BVolverAL());
    }

    /**
     * ActionListener para el botón de volver.
     */
    public class BVolverAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVP();
            vEditar.dispose();
        }
    }

    /**
     * ActionListener para el botón de patrocinadores.
     */
    public class BPatrocinadoresAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarPatrocinador();
            vEditar.dispose();
        }
    }

    /**
     * ActionListener para el botón de equipos.
     */
    public class BEquiposAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                cv.crearMostrarEquipos();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * ActionListener para el botón de jugadores.
     */
    public class BJugadoresAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarJugadores();
            vEditar.dispose();
        }
    }

    /**
     * ActionListener para el botón de juegos.
     */
    public class BJuegosAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarJuegos();
            vEditar.dispose();
        }
    }

    /**
     * ActionListener para el botón de staff.
     */
    public class BStaffAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarStaff();
            vEditar.dispose();
        }
    }

    /**
     * ActionListener para el botón de competiciones.
     */
    public class BCompeticionesAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarCompeticiones();
            vEditar.dispose();
        }
    }
}
