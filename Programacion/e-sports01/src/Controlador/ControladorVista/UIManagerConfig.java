/**
 * Clase de configuración para el UIManager.
 */
package Controlador.ControladorVista;

import javax.swing.*;
import java.awt.*;

public class UIManagerConfig {

    /**
     * Configura el color de fondo de los JOptionPane y JPanel.
     * @param color Color de fondo a establecer.
     */
    public static void setOptionPaneBackground(Color color) {
        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);
    }
}
