package Controlador.ControladorVista;

import Vista.VentanaJuegos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

/**
 * Controlador para la ventana de gestión de juegos.
 */
public class ControladorVJuego {

    private ControladorVista cv;
    private Connection con;
    private VentanaJuegos vJuegos;

    /**
     * Constructor de ControladorVJuego.
     * @param cv ControladorVista asociado.
     */
    public ControladorVJuego(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Método para crear y mostrar la ventana de juegos.
     */
    public void crearMostrar() {
        vJuegos = new VentanaJuegos();
        vJuegos.setVisible(true);
        vJuegos.addVolver(new BVolverAL());
        vJuegos.addInicio(new BInicioAL());

        vJuegos.addrbNuevoAL(new RbNuevoAL());
        vJuegos.addrbEditarAL(new RbEditarAL());
        vJuegos.addrbEliminarAL(new RbEliminarAL());
        vJuegos.limpiar();
        vJuegos.getpNuevo().setVisible(false);
        vJuegos.getpEditar().setVisible(false);
        vJuegos.getpEliminar().setVisible(false);
    }

    /**
     * ActionListener para el botón de volver.
     */
    public class BVolverAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarEditar();
            vJuegos.dispose();
        }
    }

    /**
     * ActionListener para el botón de inicio.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVP();
            vJuegos.dispose();
        }
    }

    /**
     * ActionListener para el radio button de nuevo.
     */
    public class RbNuevoAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vJuegos.getRbNuevo().isSelected()){
                vJuegos.limpiar();
                vJuegos.getpNuevo().setVisible(true);
                vJuegos.getpEditar().setVisible(false);
                vJuegos.getpEliminar().setVisible(false);
            }
        }
    }

    /**
     * ActionListener para el radio button de editar.
     */
    public class RbEditarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vJuegos.getRbEditar().isSelected()){
                vJuegos.limpiar();
                vJuegos.getpNuevo().setVisible(false);
                vJuegos.getpEditar().setVisible(true);
                vJuegos.getpEliminar().setVisible(false);
            }
        }
    }

    /**
     * ActionListener para el radio button de eliminar.
     */
    public class RbEliminarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vJuegos.getRbEliminar().isSelected()){
                vJuegos.limpiar();
                vJuegos.getpNuevo().setVisible(false);
                vJuegos.getpEditar().setVisible(false);
                vJuegos.getpEliminar().setVisible(true);
            }
        }
    }
}

