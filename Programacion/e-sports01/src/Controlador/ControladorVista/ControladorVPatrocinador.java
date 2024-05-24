/**
 * Controlador para la gestión de patrocinadores.
 */
package Controlador.ControladorVista;

import Modelo.Equipo;
import Modelo.Patrocinador;
import Vista.VentanaPatrocinadores;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class ControladorVPatrocinador {

    private ControladorVista cv;
    private VentanaPatrocinadores vPatrocinadores;

    /**
     * Constructor de ControladorVPatrocinador.
     * @param cv ControladorVista asociado.
     */
    public ControladorVPatrocinador(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Crea y muestra la ventana de gestión de patrocinadores.
     */
    public void crearMostrar() {
        vPatrocinadores = new VentanaPatrocinadores();
        vPatrocinadores.setVisible(true);
        vPatrocinadores.addVolver(new BVolverAL());
        vPatrocinadores.addInicio(new BInicioAL());

        vPatrocinadores.getCbDesvincular().addFocusListener(new ComboPatrocinadorElimFocusListener());
        vPatrocinadores.getCbVincular().addFocusListener(new ComboVincularFocusListener());

        vPatrocinadores.addrbNuevoAL(new RbNuevoAL());
        vPatrocinadores.addrbEditarAL(new RbEditarAL());
        vPatrocinadores.addrbEliminarAL(new RbEliminarAL());
        vPatrocinadores.limpiar();
        vPatrocinadores.getpNuevo().setVisible(false);
        vPatrocinadores.getpEditar().setVisible(false);
        vPatrocinadores.getpEliminar().setVisible(false);

        llenarComboEquipo();
    }

    /**
     * ActionListener para el botón de volver.
     */
    public class BVolverAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarEditar();
            vPatrocinadores.dispose();
        }
    }

    /**
     * ActionListener para el botón de inicio.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVP();
            vPatrocinadores.dispose();
        }
    }

    /**
     * ActionListener para el botón de nuevo patrocinador.
     */
    public class RbNuevoAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vPatrocinadores.getRbNuevo().isSelected()){
                vPatrocinadores.limpiar();
                vPatrocinadores.getpNuevo().setVisible(true);
                vPatrocinadores.getpEditar().setVisible(false);
                vPatrocinadores.getpEliminar().setVisible(false);
            }
        }
    }

    /**
     * ActionListener para el botón de editar patrocinador.
     */
    public class RbEditarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vPatrocinadores.getRbEditar().isSelected()){
                vPatrocinadores.limpiar();
                vPatrocinadores.getpNuevo().setVisible(false);
                vPatrocinadores.getpEditar().setVisible(true);
                vPatrocinadores.getpEliminar().setVisible(false);
            }
        }
    }

    /**
     * ActionListener para el botón de eliminar patrocinador.
     */
    public class RbEliminarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(vPatrocinadores.getRbEliminar().isSelected()){
                vPatrocinadores.limpiar();
                vPatrocinadores.getpNuevo().setVisible(false);
                vPatrocinadores.getpEditar().setVisible(false);
                vPatrocinadores.getpEliminar().setVisible(true);
            }
        }
    }

    /**
     * ActionListener para el botón de aceptar.
     */
    public class AceptarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (vPatrocinadores.getRbNuevo().isSelected()) {
                    String nombre = vPatrocinadores.getTfNombre().getText();
                    String equipo = (String) vPatrocinadores.getCbVincular().getSelectedItem();
                } else if (vPatrocinadores.getRbEliminar().isSelected()) {
                    String nombre = (String) vPatrocinadores.getCbEPatrocinador().getSelectedItem();
                    String equipo = (String) vPatrocinadores.getCbDesvincular().getSelectedItem();
                    cv.eliminarPatrocinador(nombre, equipo);
                } else if (vPatrocinadores.getRbEditar().isSelected()) {
                    String nombreNuevo = vPatrocinadores.getTfNuevoNombre().getText();
                    String nuevoEquipo = (String) vPatrocinadores.getCbVincular().getSelectedItem();
                    String nombreAntiguo = (String) vPatrocinadores.getCbEPatrocinador().getSelectedItem();
                    String equipoAntiguo = (String) vPatrocinadores.getCbEDPatrocinadores().getSelectedItem();
                    cv.editarPatrocinadorConfir(nombreNuevo, nuevoEquipo, nombreAntiguo, equipoAntiguo);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vPatrocinadores, "Error al realizar la operación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * FocusListener para el combo de patrocinador a eliminar.
     */
    public class ComboPatrocinadorElimFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            // No action on focus gain
        }

        @Override
        public void focusLost(FocusEvent e) {
            try {
                String equiposeleccionado = (String) vPatrocinadores.getCbDesvincular().getSelectedItem();
                if (equiposeleccionado != null && !equiposeleccionado.isEmpty()) {
                    vPatrocinadores.getCbEPatrocinador().removeAllItems();
                    List<Patrocinador> patrocinadores = cv.llenarPatrocinadorNombre(equiposeleccionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un equipo válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vPatrocinadores, "Error al cargar patrocinadores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * FocusListener para el combo de equipo a vincular.
     */
    public class ComboVincularFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            // No action on focus gain
        }

        @Override
        public void focusLost(FocusEvent e) {
            try {
                String equiposeleccionado = (String) vPatrocinadores.getCbVincular().getSelectedItem();
                if (equiposeleccionado != null && !equiposeleccionado.isEmpty()) {
                    // Actualizar lógica para vincular
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un equipo válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vPatrocinadores, "Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * FocusListener para el combo de nombre de patrocinador a editar.
     */
    public class ComboNombrePatrocinadorEditar implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            // No action on focus gain
        }

        @Override
        public void focusLost(FocusEvent e) {

            try {
                vPatrocinadores.getCbEDPatrocinadores().removeAllItems();
                String equiposelecionado = (String) vPatrocinadores.getCbEDPatrocinadores().getSelectedItem();
                if (equiposelecionado != null && !equiposelecionado.isEmpty()) {
                    List<Patrocinador> patrocinadores = cv.llenarPatrocinadorNombre(equiposelecionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un equipo válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vPatrocinadores, "Error al cargar jugadores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Llena los combos con los equipos disponibles.
     */
    public void llenarComboEquipo() {
        try {
            List<Equipo> listaEquipos = cv.selectEquipo(null);
            listaEquipos.forEach(equipo -> {
            });
        } catch (Exception ex) {
            throw new RuntimeException("Error al llenar combo de equipos", ex);
        }
    }
}
