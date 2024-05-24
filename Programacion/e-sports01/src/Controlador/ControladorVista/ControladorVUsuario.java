/**
 * Controlador para la gestión de usuarios en la vista del perfil.
 */
package Controlador.ControladorVista;

import Modelo.Usuario;
import Vista.VistaPerfil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorVUsuario {
    private VistaPerfil vpr;
    private ControladorVista cv;

    /**
     * Constructor de ControladorVUsuario.
     * @param cv ControladorVista asociado.
     */
    public ControladorVUsuario(ControladorVista cv) {
        this.cv = cv;
    }

    /**
     * Crea y muestra la ventana de gestión de usuario.
     */
    public void crearMostrarUsuario() {
        vpr = new VistaPerfil();
        vpr.setVisible(true);
        vpr.addBCancelar(new botonCancelar());
        vpr.addCrearUsuario(new crearUsuario());
    }

    /**
     * ActionListener para el botón de cancelar.
     */
    public class botonCancelar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cv.crearMostrarVP();
            vpr.dispose();
        }
    }

    /**
     * ActionListener para el botón de crear usuario.
     */
    public class crearUsuario implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vpr.getTxtNombre().getText();
                String contrasena = vpr.getTxtContrasena().getText();

                if (nombre.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Los datos de entrada son obligatorios");
                    return;
                }

                String expresionRegular = "^[A-Za-z]{3,}$";
                Pattern patron = Pattern.compile(expresionRegular);
                Matcher matcher = patron.matcher(nombre);
                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "El nombre no tiene el formato adecuado");
                    return;
                }

                expresionRegular = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&+=!@#$%^])(?=\\S+$).{7,}$";
                patron = Pattern.compile(expresionRegular);
                matcher = patron.matcher(contrasena);
                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "La contraseña no tiene el formato adecuado" +
                            " \n recuerda que tiene que tener al menos 7 caracteres" +
                            " \n una mayuscula" +
                            "\n un numero " +
                            "\n y un caracter especial");
                    return;
                } else {
                    String tipoUsuario = null;

                    if (vpr.getRdbUsuario().isSelected()) {
                        tipoUsuario = "usuario";
                    } else if (vpr.getRdbAdmin().isSelected()) {
                        tipoUsuario = "administrador";
                    }
                    Usuario user = cv.crearUsuario(nombre, contrasena, tipoUsuario);
                }
            } catch (Exception ex) {
            }
        }
    }
}







