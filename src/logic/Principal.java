package logic;

import graphic.login;

/**
 * punto de entrada de la aplicacion.
 *
 * @author gerrz
 */
public class Principal {

    /**
     * inicia la aplicacion mostrando la ventana de login.
     *
     * @param argumentos argumentos de linea de comandos (no se usan)
     */
    public static void main(String[] argumentos) {
        new login().setVisible(true);
    }
}