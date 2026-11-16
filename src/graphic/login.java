/*
 * click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package graphic;

import java.util.HashSet;
import java.util.Set;
import Persistencia.Usuario;
import logic.Album;
import logic.Fachada;

/**
 * ventana de inicio de sesion.
 *
 * @author gerrz
 */
public class login extends javax.swing.JFrame {
    private Usuario usuarioActual;
    private static final java.util.logging.Logger registrador = java.util.logging.Logger.getLogger(login.class.getName());

    /**
     * crea la ventana de login.
     */
    public login() {
        initComponents();
    }

    /**
     * inicializa los componentes del formulario.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTitulo = new javax.swing.JLabel();
        campoUsuario = new javax.swing.JTextField();
        campoContraseña = new javax.swing.JTextField();
        botonIniciarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        etiquetaTitulo.setFont(new java.awt.Font("Fredoka SemiCondensed", 0, 48)); // NOI18N
        etiquetaTitulo.setText("Inicio de sesión");

        campoUsuario.setText("Ing Usuario");
        campoUsuario.addActionListener(this::usuariotxtActionPerformed);

        campoContraseña.setText("Ing Contr.");
        campoContraseña.addActionListener(this::contraseñatxtActionPerformed);

        botonIniciarSesion.setText("VE!");
        botonIniciarSesion.addActionListener(this::IniciaSesionActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(etiquetaTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(campoContraseña, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonIniciarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(etiquetaTitulo)
                .addGap(18, 18, 18)
                .addComponent(campoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(campoContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonIniciarSesion)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * accion del campo de usuario (sin uso).
     *
     * @param evento evento de accion
     */
    private void usuariotxtActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_usuariotxtActionPerformed

    }//GEN-LAST:event_usuariotxtActionPerformed

    /**
     * accion del campo de contraseña (sin uso).
     *
     * @param evento evento de accion
     */
    private void contraseñatxtActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_contraseñatxtActionPerformed
        // pendiente
    }//GEN-LAST:event_contraseñatxtActionPerformed

    /**
     * intenta iniciar sesion y abre el lobby.
     *
     * @param evento evento de accion
     */
    private void IniciaSesionActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_IniciaSesionActionPerformed
        String usuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText();

        try {
            Fachada fachada = new Fachada();

            if (fachada.login(usuario, contraseña)) {

                int monedas = fachada.getMonedas();

                System.out.println("bienvenido: " + usuario);
                System.out.println("monedas: " + monedas);

                boolean[] configuracion = fachada.cargarConfiguracion();
                Preferencias.modoOscuro = configuracion[0];
                Preferencias.sonido = configuracion[1];

                // aca se abre el lobby, agarra los datos de la bd y los mete en el album
                Album album = fachada.getAlbum();

                new Lobby(fachada).setVisible(true);
                dispose();

            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "contraseña incorrecta."
                );
            }

        } catch (Exception excepcion) {
            System.err.println("error al iniciar sesion: " + excepcion.getMessage());
        }

    }//GEN-LAST:event_IniciaSesionActionPerformed

    /**
     * punto de entrada de prueba.
     *
     * @param argumentos argumentos de linea de comandos
     */
    public static void main(String argumentos[]) {
        /* setea el look and feel nimbus */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* si nimbus no esta disponible, usa el look and feel por defecto */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo informacion : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(informacion.getName())) {
                    javax.swing.UIManager.setLookAndFeel(informacion.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException excepcion) {
            registrador.log(java.util.logging.Level.SEVERE, null, excepcion);
        }
        //</editor-fold>

        /* crea y muestra el formulario */
        java.awt.EventQueue.invokeLater(() -> new login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIniciarSesion;
    private javax.swing.JTextField campoContraseña;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JTextField campoUsuario;
    // End of variables declaration//GEN-END:variables
}