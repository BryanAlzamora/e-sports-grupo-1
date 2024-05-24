/**
 * Controlador para la visualización de la última jornada de una competición.
 */
package Controlador.ControladorVista;

import Modelo.Competicion;
import Modelo.Enfrentamiento;
import Vista.VentanaUltJornada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControladorVUltJornada {
    private ControladorVista cv;
    private VentanaUltJornada vUltJornada;
    private List<String> listaCompeticiones;

    /**
     * Constructor de ControladorVUltJornada.
     * @param cv ControladorVista asociado.
     */
    public ControladorVUltJornada(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Crea y muestra la ventana para visualizar la última jornada de una competición.
     */
    public void crearMostrar() {
        vUltJornada = new VentanaUltJornada();
        vUltJornada.setVisible(true);
        vUltJornada.addInicio(new BInicioAL());

        llenarComboCompeticiones();
        vUltJornada.addCBCompeticion(new BCompeticionAL());

        listaCompeticiones = new ArrayList<>();
        vUltJornada.getCbCompeticiones().setSelectedIndex(-1);
    }

    /**
     * Llena el combo de competiciones con las disponibles.
     */
    public void llenarComboCompeticiones() {
        try {
            listaCompeticiones = cv.buscarCompeticiones();
            listaCompeticiones.forEach(o -> {
                vUltJornada.getCbCompeticiones().addItem(o);
                System.out.println("Competicion añadida al combo: " + o);
            });
        } catch (Exception e) {
            vUltJornada.mostrar(e.getMessage());
        }
    }

    /**
     * ActionListener para el botón de inicio.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vUltJornada.getCbCompeticiones().removeAllItems();
            cv.crearMostrarVP();
            vUltJornada.dispose();
        }
    }

    /**
     * ActionListener para el combo de competiciones.
     */
    public class BCompeticionAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreCompeticion = (String) vUltJornada.getCbCompeticiones().getSelectedItem();
                if (nombreCompeticion != null) {
                    Competicion competicion = cv.buscarCompeticion(nombreCompeticion);
                    mostrarEnfrentamientosUltimaJornada(competicion);
                }
            } catch (Exception ex) {
                vUltJornada.mostrar(ex.getMessage());
            }
        }
    }

    /**
     * Muestra los enfrentamientos de la última jornada de una competición.
     * @param competicion Competición de la que se desea mostrar la última jornada.
     */
    public void mostrarEnfrentamientosUltimaJornada(Competicion competicion) {
        try {
            Integer competicionID = competicion.getIdCompeticion();
            Integer ultimaJornada = cv.buscarUltimaJornada(competicionID);
            if (ultimaJornada != null) {
                List<Enfrentamiento> enfrentamientos = cv.buscarEnfrentamientos(ultimaJornada, competicionID);

                vUltJornada.getPanelEnfrentamiento().removeAll();
                vUltJornada.getPanelEnfrentamiento().setLayout(new BoxLayout(vUltJornada.getPanelEnfrentamiento(), BoxLayout.Y_AXIS));

                if (!enfrentamientos.isEmpty()) {
                    for (Enfrentamiento enfrentamiento : enfrentamientos) {
                        JPanel panelEnfrentamiento = crearPanelEnfrentamiento(enfrentamiento);
                        vUltJornada.agregarPanel(panelEnfrentamiento);
                    }
                } else {
                    System.out.println("No se encontraron enfrentamientos para la última jornada.");
                }

                vUltJornada.actualizarVista();
            } else {
                System.out.println("No se encontró la última jornada para la competición.");
            }
        } catch (Exception ex) {
            vUltJornada.mostrar(ex.getMessage());
        }
    }

    /**
     * Crea un panel para visualizar un enfrentamiento.
     * @param enfrentamiento Enfrentamiento a visualizar.
     * @return Panel de visualización del enfrentamiento.
     */
    private JPanel crearPanelEnfrentamiento(Enfrentamiento enfrentamiento) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 50));
        panel.setBackground(new Color(0, 255, 185));

        JLabel equipo1 = new JLabel(enfrentamiento.getEquipoUno().getNombre());
        JLabel equipo2 = new JLabel(enfrentamiento.getEquipoDos().getNombre());
        JTextField resultadoLocal = new JTextField(2);
        JTextField resultadoVisitante = new JTextField(2);
        JLabel guion = new JLabel("-");

        Font font = new Font("Michroma", Font.PLAIN, 22);
        Color colorFuente = new Color(46, 47, 47);
        equipo1.setFont(font);
        equipo2.setFont(font);
        resultadoLocal.setFont(font);
        resultadoVisitante.setFont(font);
        guion.setFont(font);

        equipo1.setForeground(colorFuente);
        equipo2.setForeground(colorFuente);
        guion.setForeground(colorFuente);

        resultadoLocal.setBackground(new Color(0, 255, 185));
        resultadoVisitante.setBackground(new Color(0, 255, 185));
        resultadoLocal.setForeground(colorFuente);
        resultadoVisitante.setForeground(colorFuente);
        resultadoLocal.setEditable(false);
        resultadoLocal.setEnabled(false);
        resultadoLocal.setBackground(new Color(46, 47, 47));

        resultadoVisitante.setEditable(false);
        resultadoVisitante.setEnabled(false);
        resultadoVisitante.setBackground(new Color(46, 47, 47));

        panel.add(equipo1);
        panel.add(resultadoLocal);
        panel.add(guion);
        panel.add(resultadoVisitante);
        panel.add(equipo2);

        cv.llenarResultados(resultadoLocal, resultadoVisitante, enfrentamiento.getIdEnfJor());

        return panel;
    }

}
