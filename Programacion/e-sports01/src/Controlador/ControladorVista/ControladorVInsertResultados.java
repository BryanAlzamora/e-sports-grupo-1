package Controlador.ControladorVista;

import Modelo.Competicion;
import Modelo.Enfrentamiento;
import Vista.VentanaInsertarResultados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la inserción de resultados en la interfaz gráfica.
 */
public class ControladorVInsertResultados {
    private ControladorVista cv;
    private VentanaInsertarResultados vInsertResultados;
    private List<String> listaCompeticiones;
    private List<Integer> listaJornadas;

    /**
     * Constructor de ControladorVInsertResultados.
     * @param cv ControladorVista asociado.
     */
    public ControladorVInsertResultados(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Método para crear y mostrar la ventana de inserción de resultados.
     */
    public void crearMostrar() {
        vInsertResultados = new VentanaInsertarResultados();
        vInsertResultados.setVisible(true);
        vInsertResultados.addInicio(new BInicioAL());

        llenarComboCompeticiones();
        vInsertResultados.addCBCompeticion(new BCompeticionAL());

        listaCompeticiones = new ArrayList<>();
        listaJornadas = new ArrayList<>();
        vInsertResultados.getCbCompeticiones().setSelectedIndex(-1);
    }

    /**
     * Método para llenar el combo de competiciones.
     */
    public void llenarComboCompeticiones() {
        try {
            listaCompeticiones = cv.buscarCompeticiones();
            listaCompeticiones.forEach(o -> {
                vInsertResultados.getCbCompeticiones().addItem(o);
                System.out.println("Competicion añadida al combo: " + o);
            });
        } catch (Exception e) {
            vInsertResultados.mostrar(e.getMessage());
        }
    }

    /**
     * ActionListener para el botón de inicio.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vInsertResultados.getCbCompeticiones().removeAll();
            vInsertResultados.getCbJornadas().removeAll();
            cv.crearMostrarVP();
            vInsertResultados.dispose();
        }
    }

    /**
     * ActionListener para seleccionar una competición.
     */
    public class BCompeticionAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                vInsertResultados.getCbJornadas().removeAllItems();
                String nombreCompeticion = (String) vInsertResultados.getCbCompeticiones().getSelectedItem();
                if (nombreCompeticion != null) {
                    Competicion competicion = cv.buscarCompeticion(nombreCompeticion);
                    llenarComboJornadas(competicion);
                }
            } catch (Exception ex) {
                vInsertResultados.mostrar(ex.getMessage());
            }
        }
    }

    /**
     * Método para llenar el combo de jornadas.
     * @param competicion Competición seleccionada.
     */
    public void llenarComboJornadas(Competicion competicion) {
        try {
            Integer competicionID = competicion.getIdCompeticion();
            listaJornadas = cv.buscarJornadas(competicionID);

            if (!listaJornadas.isEmpty()) {
                listaJornadas.forEach(o -> {
                    vInsertResultados.getCbJornadas().addItem(o);
                    System.out.println("Jornada añadida al combo: " + o);
                });
            } else {
                System.out.println("No se encontraron jornadas para la competición.");
            }
            vInsertResultados.getCbJornadas().setSelectedIndex(-1);
        } catch (Exception ex) {
            vInsertResultados.mostrar(ex.getMessage());
        }
    }

    /**
     * FocusListener para el combo de jornadas.
     */
    public class BJornadaFL implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            // No hacemos nada cuando el foco se gana
        }

        @Override
        public void focusLost(FocusEvent e) {
            try {
                Integer idJornada = (Integer) vInsertResultados.getCbJornadas().getSelectedItem();
                if (idJornada != null) {
                    Competicion competicion = cv.buscarCompeticion((String) vInsertResultados.getCbCompeticiones().getSelectedItem());
                    Integer idCompeticion = competicion.getIdCompeticion();
                    List<Enfrentamiento> enfrentamientos = cv.buscarEnfrentamientos(idJornada, idCompeticion);

                    vInsertResultados.getPanelEnfrentamiento().removeAll();
                    vInsertResultados.getPanelEnfrentamiento().setLayout(new BoxLayout(vInsertResultados.getPanelEnfrentamiento(), BoxLayout.Y_AXIS));

                    if (!enfrentamientos.isEmpty()) {
                        for (Enfrentamiento enfrentamiento : enfrentamientos) {
                            JPanel panelEnfrentamiento = crearPanelEnfrentamiento(enfrentamiento);
                            vInsertResultados.agregarPanel(panelEnfrentamiento);
                        }
                    }

                    vInsertResultados.actualizarVista();
                }
            } catch (Exception ex) {
                vInsertResultados.mostrar(ex.getMessage());
            }
        }
    }

    /**
     * Método para crear un panel de enfrentamiento.
     * @param enfrentamiento Enfrentamiento.
     * @return Panel creado.
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

        Font font = new Font("Arial", Font.PLAIN, 14);
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
        resultadoLocal.setEnabled(true);
        resultadoVisitante.setEnabled(true);

        resultadoLocal.setEditable(true);
        resultadoVisitante.setEditable(true);

        panel.add(equipo1);
        panel.add(resultadoLocal);
        panel.add(guion);
        panel.add(resultadoVisitante);
        panel.add(equipo2);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setBackground(new Color(46, 47, 47));
        botonAceptar.setForeground(new Color(0, 255, 185));
        botonAceptar.setFont(new Font("Arial", Font.PLAIN, 12));

        botonAceptar.addActionListener(e -> {
            try {
                int resLocal = Integer.parseInt(resultadoLocal.getText());
                int resVisitante = Integer.parseInt(resultadoVisitante.getText());
                cv.guardarResultados(enfrentamiento.getIdEnfJor(), resLocal, resVisitante);
                cv.actualizarTablaClasificacion(enfrentamiento, resLocal, resVisitante);
            } catch (Exception ex) {
                vInsertResultados.mostrar(ex.getMessage());
            }
        });

        panel.add(botonAceptar);

        cv.llenarResultados(resultadoLocal, resultadoVisitante, enfrentamiento.getIdEnfJor());

        return panel;
    }
}
