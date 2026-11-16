/*
 * click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package graphic;

import javax.swing.JFrame;
import java.awt.Color;
import logic.Fachada;

/**
 * ventana de configuracion de la aplicacion.
 *
 * @author gerrz
 */
public class Configuracion extends javax.swing.JFrame {

    private static final java.util.logging.Logger registrador = java.util.logging.Logger.getLogger(Configuracion.class.getName());
    private Fachada fachada;

    /**
     * crea la ventana sin fachada.
     */
    public Configuracion() {
        initComponents();
        // inicializa el estado del interruptor segun la preferencia guardada
        casillaOscuro.setSelected(Preferencias.modoOscuro);
    }

    /**
     * crea la ventana con fachada y carga la configuracion.
     *
     * @param fachada fachada del juego
     */
    public Configuracion(Fachada fachada) {
        this.fachada = fachada;
        initComponents();
        try {
            boolean[] configuracion = fachada.cargarConfiguracion();
            Preferencias.modoOscuro = configuracion[0];
            Preferencias.sonido = configuracion[1];
        } catch (Exception excepcion) {
            System.err.println("error al cargar configuracion: " + excepcion.getMessage());
        }
        casillaOscuro.setSelected(Preferencias.modoOscuro);
    }

    /**
     * inicializa los componentes del formulario.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotones1 = new javax.swing.ButtonGroup();
        etiquetaTitulo = new javax.swing.JLabel();
        casillaOscuro = new javax.swing.JRadioButton();
        etiqueta2 = new javax.swing.JLabel();
        etiqueta3 = new javax.swing.JLabel();
        botonRadio2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        etiquetaTitulo.setFont(new java.awt.Font("Fredoka Condensed", 0, 48)); // NOI18N
        etiquetaTitulo.setText("Configuraciòn");

        casillaOscuro.setIconTextGap(32);
        casillaOscuro.addActionListener(this::checkboxoscuroActionPerformed);

        etiqueta2.setFont(new java.awt.Font("Fredoka Condensed", 0, 24)); // NOI18N
        etiqueta2.setText("Modo oscuro");

        etiqueta3.setFont(new java.awt.Font("Fredoka Condensed", 0, 24)); // NOI18N
        etiqueta3.setText("Musica");

        botonRadio2.setIconTextGap(32);
        botonRadio2.addActionListener(this::jRadioButton2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(etiquetaTitulo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(etiqueta3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonRadio2)
                                .addGap(40, 40, 40))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(etiqueta2)
                                .addGap(74, 74, 74)
                                .addComponent(casillaOscuro)))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etiqueta2)
                    .addComponent(casillaOscuro))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(botonRadio2))
                    .addComponent(etiqueta3))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * guarda la preferencia de modo oscuro.
     *
     * @param evento evento de accion
     */
    private void checkboxoscuroActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_checkboxoscuroActionPerformed
        boolean seleccionado = casillaOscuro.isSelected();
        Preferencias.modoOscuro = seleccionado;
        if (fachada != null) {
            try {
                fachada.guardarConfiguracion(seleccionado, Preferencias.sonido);
            } catch (Exception excepcion) {
                System.err.println("error al guardar configuracion: " + excepcion.getMessage());
            }
        }
    }//GEN-LAST:event_checkboxoscuroActionPerformed

    /**
     * accion del boton de musica (pendiente).
     *
     * @param evento evento de accion
     */
    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // pendiente
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    /**
     * punto de entrada de prueba.
     *
     * @param argumentos argumentos de linea de comandos
     */
    public static void main(String argumentos[]) {
        /* setea el look and feel nimbus */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* si nimbus no esta disponible, usa el look and feel por defecto.
         * para mas detalles ver la documentacion de swing
         */
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
        java.awt.EventQueue.invokeLater(() -> new Configuracion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoBotones1;
    private javax.swing.JRadioButton casillaOscuro;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiqueta2;
    private javax.swing.JLabel etiqueta3;
    private javax.swing.JRadioButton botonRadio2;
    // End of variables declaration//GEN-END:variables
}