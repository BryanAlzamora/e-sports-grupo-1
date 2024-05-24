/**
 * Controlador para la ventana principal.
 */
package Controlador.ControladorVista;

import Modelo.Competicion;
import Modelo.Equipo;
import Modelo.Usuario;
import Vista.AccionRealizada;
import Vista.VentanaInicioSesion;
import Vista.VentanaPrincipal;
import Vista.VistaPerfil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorVP {
    private ControladorVista cv;
    private VentanaPrincipal vp;
    private ControladorVLogin cvl;
    private VistaPerfil vper;
    private VentanaInicioSesion vsesion;
    private AccionRealizada aRealizada;

    /**
     * Constructor de ControladorVP.
     * @param cv ControladorVista asociado.
     * @throws Exception Si ocurre un error al crear la ventana de inicio de sesión.
     */
    public ControladorVP(ControladorVista cv) throws Exception {
        this.cv = cv;
        this.vsesion = new VentanaInicioSesion();
    }

    /**
     * Crea y muestra la ventana principal según el tipo de usuario.
     * @param user Usuario que ha iniciado sesión.
     */
    public void crearMostrar(Usuario user) {
        if (user.getTipo().equals("administrador")) {
            vp = new VentanaPrincipal();
            aRealizada=new AccionRealizada();
            aRealizada.addRealizadaAL(new RealizadaAL());
            vp.setVisible(true);
            vp.addeditar(new BEditarAL());

            vp.addUsuarios(new BusuarioAL());
            vp.addcerrarInsc(new BCerrarInscAL());
            vp.addConsultas(new bConsultasAL());
            vp.addBSalirAL(new BSalirAl());
            vp.addCerrarSesionAL(new CerrarSesionAl());
            vp.addClasificacion(new BClasificacionAL());
            vp.addInsertResultados(new BInsertResultadosAL());
            vp.addUltJornada(new BUltJornadaAL());

            vp.getTxNombre().setText(user.getNombre() + " !");
        } else {
            vp = new VentanaPrincipal();
            vp.setVisible(true);

            aRealizada=new AccionRealizada();
            aRealizada.addRealizadaAL(new RealizadaAL());
            vp.getpEditar().setVisible(false);
            vp.getpCerrarInsc().setVisible(false);
            vp.getpConsulta().setVisible(false);
            vp.getpInsertResultados().setVisible(false);

            vp.addUsuarios(new BusuarioAL());
            vp.addBSalirAL(new BSalirAl());
            vp.addCerrarSesionAL(new CerrarSesionAl());
            vp.addClasificacion(new BClasificacionAL());
            vp.addUltJornada(new BUltJornadaAL());
            vp.getJlTipo().setText("Usuario");

            vp.getTxNombre().setText(user.getNombre() + " !");
        }
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
     * ActionListener para el botón de usuarios.
     */
    public class BusuarioAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarUsuario();
            vp.dispose();
        }
    }

    /**
     * ActionListener para el botón de clasificación.
     */
    public class BClasificacionAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarClasificacion();
            vp.dispose();
        }
    }

    /**
     * ActionListener para el botón de insertar resultados.
     */
    public class BInsertResultadosAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarInsertResultados();
            vp.dispose();
        }
    }

    /**
     * ActionListener para el botón de última jornada.
     */
    public class BUltJornadaAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarUltJornada();
            vp.dispose();
        }
    }

    /**
     * ActionListener para el botón de consultas.
     */
    public class bConsultasAL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearConsultas();
        }
    }

    /**
     * ActionListener para el botón de editar.
     */
    public class BEditarAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarEditar();
            vp.dispose();
        }
    }

    /**
     * ActionListener para el botón de cerrar inscripciones.
     */
    public class BCerrarInscAL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                boolean cerrarInsc = false;
                List<Competicion> listaCompeticiones = cv.llenarCompeticiones();
                boolean par=true;
                for (Competicion competicion : listaCompeticiones) {
                    int x=0;
                    List<Equipo> listaEquiposCompeticion = cv.llenarEquiposCompeticion(x);
                    int numEquipos = listaEquiposCompeticion.size();

                    if (numEquipos % 2 == 0) {
                        System.out.println("El número de equipos en la competición " + competicion.getNombre() + " es par.");
                    } else {
                        par=false;
                        System.out.println("El número de equipos en la competición " + competicion.getNombre() + " es par.");
                        throw new Exception("El número de equipos en la competición " + competicion.getNombre() + " es impar.");
                    }
                    if(par=true){
                        cerrarInsc=true;
                    }
                    x=x+1;
                }
                Integer comprobar = cv.buscarUltimaJornada(1);
                System.out.println(comprobar);
                if (cerrarInsc){
                    if (comprobar==0){
                        cv.generarCalendario();
                        aRealizada.getEdicionTexto().setText("¡ Calendario generado !");
                        aRealizada.setVisible(true);
                    }else{
                        aRealizada.getEdicionTexto().setText("El calendario ya estaba generado.");
                        aRealizada.setVisible(true);
                    }
                }

            } catch (Exception ex) {
                vp.mostrarMensaje(ex.getMessage());
            }
        }
    }

    /**
     * ActionListener para el botón de salir.
     */
    public class BSalirAl implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

    /**
     * ActionListener para el botón de cerrar sesión.
     */
    public class CerrarSesionAl implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVinicioSesion();
            vp.dispose();
        }
    }
}
