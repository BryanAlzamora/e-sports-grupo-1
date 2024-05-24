package Controlador.ControladorVista;

import Modelo.*;
import Vista.VentanaConsultas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * ControladorVConsultas controla la interacción entre la vista de consultas y el modelo de datos.
 */
public class ControladorVConsultas {
    private final ControladorVista cv;
    private VentanaConsultas vc;

    /**
     * Constructor de ControladorVConsultas.
     *
     * @param cv La instancia del controlador de la vista principal.
     */
    public ControladorVConsultas(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Crea y muestra la ventana de consultas.
     */
    public void crearMostrar() {
        vc = new VentanaConsultas();
        vc.getCbSelect().addFocusListener(new cbSeleccionar());
        vc.addbtSlir(new btSalirAL());
        vc.setVisible(true);
    }

    /**
     * ActionListener para el botón de salir.
     */
    public class btSalirAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vc.dispose();
        }
    }

    /**
     * FocusListener para el combo box de selección.
     */
    public class cbSeleccionar implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            // No hacer nada al ganar foco
        }

        @Override
        public void focusLost(FocusEvent e) {
            try {
                String tipo = (String) vc.getCbSelect().getSelectedItem();
                if ("Jugador".equals(tipo)){
                    List<Jugador> jugadores = cv.llenarJugadoresS(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Jugador jugador : jugadores) {
                        sb.append(jugador.getNombre()+" : "+ jugador.getApellido1() +" : "+ jugador.getApellido2()+" : "+ jugador.getEquipo()+" : "+jugador.getRol()+" : "+ jugador.getNickname()+" : "+ jugador.getNickname()+" : "+ jugador.getFechaNacimiento()+" : "+ jugador.getSueldo() + "\n" +"---------------------------------------------------------------------\n"); // Asegúrate de tener un método toString() adecuado en la clase Jugador
                    }
                    vc.getTxaResultado().setText(sb.toString());

                } else if ("Equipo".equals(tipo)){
                    List<Equipo> equipo = cv.llenarEquiposS(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Equipo equipos : equipo) {
                        sb.append(equipos.getNombre()+ " : "+ equipos.getFechaFundacion()+" : "+ "\n");
                    }
                    vc.getTxaResultado().setText(sb.toString());
                } else if ("Entrenador".equals(tipo)){
                    List<Entrenador> entrenador = cv.llenarEntrenador(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Entrenador entrenadores : entrenador) {
                        sb.append(entrenadores.getNombre()+ " : "+ entrenadores.getApellido1()+" : "+ entrenadores.getApellido2() +" : "+ entrenadores.getSueldo()+" : "+ entrenadores.getEquipo()+"\n"+"------------------------------------------------------------------------" + "\n");
                    }
                    vc.getTxaResultado().setText(sb.toString());
                } else if ("Asistente".equals(tipo)){
                    List<Asistente> asistente = cv.llenarAsistente(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Asistente asistentes : asistente) {
                        sb.append(asistentes.getNombre()+ " : "+ asistentes.getApellido1()+" : "+ asistentes.getApellido2() +" : "+ asistentes.getSueldo()+" : "+ asistentes.getEquipo()+"\n"+"------------------------------------------------------------------------" + "\n");
                    }
                    vc.getTxaResultado().setText(sb.toString());
                } else if ("Competicion".equals(tipo)){
                    List<Competicion> competicion = cv.llenarCompeticion(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Competicion competiciones : competicion) {
                        sb.append(competiciones.getNombre()+" : "+competiciones.getEstado()+" : "+ competiciones.getFechaInicio()+" : "+competiciones.getFechaFin()+"\n"+"------------------------------------------------------------------------" + "\n");
                    }
                    vc.getTxaResultado().setText(sb.toString());

                } else if ("Juego".equals(tipo)){
                    List<Juego> juegos = cv.llenarJuegos(tipo);
                    StringBuilder sb = new StringBuilder();
                    for (Juego juego : juegos) {
                        sb.append(juego.getNombre()+" : "+juego.getEmpresa()+" : "+ juego.getFechalanzamiento()+"\n"+"------------------------------------------------------------------------" + "\n");
                    }
                    vc.getTxaResultado().setText(sb.toString());

                }
            } catch (Exception eds) {
                JOptionPane.showMessageDialog(null,JOptionPane.ERROR_MESSAGE  );
            }
        }
    }
}
