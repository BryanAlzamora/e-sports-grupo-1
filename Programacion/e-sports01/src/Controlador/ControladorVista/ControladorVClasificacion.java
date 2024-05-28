package Controlador.ControladorVista;

import Controlador.ControladorBD.ControladorTablaEquipo;
import Modelo.Clasificacion;
import Modelo.Competicion;
import Modelo.Equipo;
import Vista.VentanaClasificacion;
import Vista.VentanaEquipos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * Controlador para la vista de clasificación.
 * @author Grupo 1
 * @version 1.0
 */
public class  ControladorVClasificacion {
    private ControladorVista cv;
    private VentanaEquipos veq;
    private ControladorTablaEquipo ctequipo;

    private VentanaClasificacion vClasificacion;
    private List<String> listaCompeticiones;
    /**
     * Constructor de la clase ControladorVClasificacion.
     *
     * @param cv Controlador de la vista principal.
     */
    public ControladorVClasificacion(ControladorVista cv) {
        this.cv = cv;
    }
    /**
     * Método para crear y mostrar la vista de clasificación.
     */
    public void crearMostrar() {
        vClasificacion = new VentanaClasificacion();
        vClasificacion.setVisible(true);
        vClasificacion.addInicio(new BInicioAL());
        listaCompeticiones=new ArrayList<>();
        llenarComboCompeticiones();
        vClasificacion.addCBCompeticion(new BCompeticionAL());
    }
    /**
     * Método para llenar el combo de competiciones.
     */
    public void llenarComboCompeticiones() {
        try {
            listaCompeticiones = cv.buscarCompeticiones();
            listaCompeticiones.forEach(o -> {
                vClasificacion.getCbCompeticion().addItem(o);
                System.out.println("Competicion añadida al combo: " + o);
            });
        } catch (Exception e) {
            vClasificacion.mostrar(e.getMessage());
        }
    }
    /**
     * Clase interna que implementa ActionListener para el botón de BInicioAL.
     */
    public class BInicioAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vClasificacion.getCbCompeticion().removeAll();
            cv.crearMostrarVP();
            vClasificacion.dispose();
        }
    }
    /**
     * Clase interna que implementa ActionListener para el botón de BCompeticionAL.
     */
    public class BCompeticionAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombreCompeticion = (String) vClasificacion.getCbCompeticion().getSelectedItem();
                if (nombreCompeticion != null) {
                    Competicion competicion = cv.buscarCompeticion(nombreCompeticion);
                    Integer idCompeticion = competicion.getIdCompeticion();
                    List<Clasificacion> clasificaciones = cv.obtenerClasificacionesPorCompeticion(idCompeticion);
                    mostrarClasificacionesEnTabla(clasificaciones);
                }
            } catch (Exception ex) {
                vClasificacion.mostrar(ex.getMessage());
            }
        }
    }
    /**
     * Método para mostrar las clasificaciones en una tabla.
     *
     * @param clasificaciones Lista de clasificaciones.
     */
    public void mostrarClasificacionesEnTabla(List<Clasificacion> clasificaciones) {
        // Definir las columnas de la tabla
        String[] columnNames = {"Posición", "Equipo", "Puntos"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Añadir las filas a la tabla
        int posicion = 1;
        for (Clasificacion clasificacion : clasificaciones) {
            for (int i = 0; i < clasificacion.getListaEquipo().size(); i++) {
                Equipo equipo = clasificacion.getListaEquipo().get(i);
                String puntos = clasificacion.getListaPuntos().get(i);
                Object[] row = {posicion, equipo.getNombre(), puntos};
                tableModel.addRow(row);
                posicion++;
            }
        }

        // Crear el JTable con el modelo de tabla
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Michroma", Font.PLAIN, 14));
        table.setForeground(new Color(46, 47, 47));
        table.setBackground(new Color(0, 255, 185));
        table.setRowHeight(60); // Altura de la fila

        // Alinear texto al centro en todas las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Michroma", Font.PLAIN, 22));
        header.setBackground(new Color(46, 47, 47));
        header.setForeground(new Color(154, 255, 185));

        // Crear un JScrollPane para contener el JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón de cierre
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Michroma", Font.PLAIN, 22));
        btnCerrar.setBackground(new Color(46, 47, 47));
        btnCerrar.setForeground(new Color(0, 255, 185));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btnCerrar);
                frame.dispose();
            }
        });

        // Crear un JPanel para contener la tabla y el botón
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnCerrar, BorderLayout.SOUTH);

        // Agregar márgenes al JPanel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear un JPanel para establecer el color de fondo
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(0, 255, 185));
        backgroundPanel.add(panel, BorderLayout.CENTER);

        // Crear un JFrame para mostrar la tabla
        JFrame frame = new JFrame("Clasificación");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.add(backgroundPanel);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla
        frame.setVisible(true);
    }
/*
    public void llenarClasificacion(ClasificacionCompleta clasificacion) {
        try {
            vClasificacion.getPanelClasificacion().removeAll();
            vClasificacion.getPanelClasificacion().setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas: Posición, Nombre, Puntos

            // Encabezados de la tabla
            vClasificacion.getPanelClasificacion().add(new JLabel("Posición"));
            vClasificacion.getPanelClasificacion().add(new JLabel("Equipo"));
            vClasificacion.getPanelClasificacion().add(new JLabel("Puntos"));

            // Contenido de la tabla
            int posicion = 1;
            for (int i = 0; i < clasificacion.getListaEquipos().size(); i++) {
                Equipo equipo = clasificacion.getListaEquipos().get(i);
                String puntos = String.valueOf(clasificacion.getListaPuntos().get(i));

                vClasificacion.getPanelClasificacion().add(new JLabel(String.valueOf(posicion)));
                vClasificacion.getPanelClasificacion().add(new JLabel(equipo.getNombre()));
                vClasificacion.getPanelClasificacion().add(new JLabel(puntos));
                posicion++;
            }

            vClasificacion.getPanelClasificacion().revalidate();
            vClasificacion.getPanelClasificacion().repaint();
        }catch (Exception ex) {
            vClasificacion.mostrar(ex.getMessage());
        }
    }

 */



}
