/*
 * click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package graphic;

import java.awt.Color;
import java.util.ArrayList;
import logic.Album;
import logic.Fachada;
import logic.Figurita;
import logic.Sobres;
import Persistencia.Usuario;

/**
 * ventana principal del juego (lobby).
 *
 * @author gerrz
 */
public class Lobby extends javax.swing.JFrame {

    private static final java.util.logging.Logger registrador = java.util.logging.Logger.getLogger(Lobby.class.getName());

    private Fachada fachada;
    private Usuario usuarioActual;
    private Album album;
    private javax.swing.JLabel textoSobresGratis;

    Color colorNormal = new Color(51, 51, 255);

    /**
     * crea el lobby con la fachada dada.
     *
     * @param fachada fachada del juego
     */
    public Lobby(Fachada fachada) {
        this.fachada = fachada;
        this.usuarioActual = fachada.getUsuario();
        this.album = fachada.getAlbum();
        initComponents();

        // fuera de la region generada para que la regeneracion del .form no lo borre
        botonSobres.addActionListener(this::sobresbtnActionPerformed);

        textoSobresGratis = new javax.swing.JLabel();

        textoSobresGratis.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18));

        getContentPane().add(textoSobresGratis, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 250, -1));

        actualizarMonedasLabel();
        actualizarSobresLabel();

        // Campo para canjear codigos de recompensa
        javax.swing.JTextField codigoField = new javax.swing.JTextField();
        codigoField.setFont(new java.awt.Font("Fredoka SemiCondensed", 0, 16));
        codigoField.setBounds(356, 530, 190, 30);
        getContentPane().add(codigoField);

        javax.swing.JButton codigoBtn = new javax.swing.JButton("Canjear codigo");
        codigoBtn.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 14));
        codigoBtn.setBounds(356, 565, 190, 30);
        codigoBtn.addActionListener(e -> {
            String code = codigoField.getText().trim();
            if (code.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ingresa un codigo", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                boolean ok = fachada.canjearCodigo(code);
                if (ok) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Codigo canjeado correctamente", "OK", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    codigoField.setText("");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Codigo invalido o ya usado", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                actualizarMonedasLabel();
                actualizarSobresLabel();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo canjear: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
        getContentPane().add(codigoBtn);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evento) {
                fachada.cerrarSesionTiempo();
            }
        });

        javax.swing.Timer temporizador = new javax.swing.Timer(60000, evento -> {
            fachada.reclamarSobres();
            actualizarMonedasLabel();
            actualizarSobresLabel();
        });
        temporizador.setRepeats(true);
        temporizador.start();
    }

    /**
     * inicializa los componentes del formulario.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoMonedas = new javax.swing.JLabel();
        botonConfiguracion = new javax.swing.JButton();
        etiquetaLogo = new javax.swing.JLabel();
        botonSobres = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        botonIndex = new javax.swing.JButton();
        botonRepetidas = new javax.swing.JButton();
        etiquetaFondo = new javax.swing.JLabel();
        etiqueta2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textoMonedas.setFont(new java.awt.Font("Fredoka Condensed", 0, 36)); // NOI18N
        textoMonedas.setForeground(new java.awt.Color(255, 255, 51));
        textoMonedas.setText("Monedas: 0");
        getContentPane().add(textoMonedas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 476, 250, 60));

        botonConfiguracion.setBackground(new java.awt.Color(0, 0, 255));
        botonConfiguracion.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18)); // NOI18N
        botonConfiguracion.setText("Configuracion");
        botonConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                configbtnMouseEntered(evento);
            }
            public void mouseExited(java.awt.event.MouseEvent evento) {
                configbtnMouseExited(evento);
            }
        });
        botonConfiguracion.addActionListener(this::configbtnActionPerformed);
        getContentPane().add(botonConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 410, 190, -1));

        etiquetaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesParaDiseño/Logo.png"))); // NOI18N
        getContentPane().add(etiquetaLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 383, 316));

        botonSobres.setBackground(new java.awt.Color(0, 0, 255));
        botonSobres.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18)); // NOI18N
        botonSobres.setText("Abrir un sobre");
        botonSobres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                sobresbtnMouseEntered(evento);
            }
            public void mouseExited(java.awt.event.MouseEvent evento) {
                sobresbtnMouseExited(evento);
            }
        });
        botonSobres.addActionListener(this::sobresbtnActionPerformed);
        getContentPane().add(botonSobres, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 370, 190, -1));

        botonSalir.setBackground(new java.awt.Color(0, 0, 255));
        botonSalir.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18)); // NOI18N
        botonSalir.setText("Salir");
        botonSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                SalirMouseEntered(evento);
            }
            public void mouseExited(java.awt.event.MouseEvent evento) {
                SalirMouseExited(evento);
            }
        });
        botonSalir.addActionListener(this::SalirActionPerformed);
        getContentPane().add(botonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 450, 190, -1));

        botonIndex.setBackground(new java.awt.Color(51, 51, 255));
        botonIndex.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18)); // NOI18N
        botonIndex.setText("Index");
        botonIndex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                IndexbtnMouseEntered(evento);
            }
            public void mouseExited(java.awt.event.MouseEvent evento) {
                IndexbtnMouseExited(evento);
            }
        });
        botonIndex.addActionListener(this::IndexbtnActionPerformed);
        getContentPane().add(botonIndex, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 330, 190, -1));

        botonRepetidas.setBackground(new java.awt.Color(51, 51, 255));
        botonRepetidas.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 18)); // NOI18N
        botonRepetidas.setText("Mazo repetidas");
        botonRepetidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evento) {
                repetidasbtnMouseEntered(evento);
            }
            public void mouseExited(java.awt.event.MouseEvent evento) {
                repetidasbtnMouseExited(evento);
            }
        });
        botonRepetidas.addActionListener(this::repetidasbtnActionPerformed);
        getContentPane().add(botonRepetidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 490, 190, -1));

        etiquetaFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagenesParaDiseño/lobbyBackground.png"))); // NOI18N
        etiquetaFondo.setText("jLabel3");
        getContentPane().add(etiquetaFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 0, 1000, 630));

        etiqueta2.setText("jLabel2");
        getContentPane().add(etiqueta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * abre la ventana del index.
     *
     * @param evento evento de accion
     */
    private void IndexbtnActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_IndexbtnActionPerformed
        new Index(album).setVisible(true);

    }//GEN-LAST:event_IndexbtnActionPerformed

    /**
     * efecto hover al entrar en index.
     *
     * @param evento evento de mouse
     */
    private void IndexbtnMouseEntered(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_IndexbtnMouseEntered

        botonIndex.setBackground(Color.BLACK);
        botonIndex.setForeground(Color.YELLOW);

        botonIndex.setSize(
        botonIndex.getWidth() + 10,
        botonIndex.getHeight() + 10
    );


    }//GEN-LAST:event_IndexbtnMouseEntered

    /**
     * efecto hover al salir de index.
     *
     * @param evento evento de mouse
     */
    private void IndexbtnMouseExited(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_IndexbtnMouseExited

        botonIndex.setBackground(colorNormal);
        botonIndex.setForeground(Color.BLACK);
        botonIndex.setSize(
        botonIndex.getWidth() - 10,
        botonIndex.getHeight() - 10
    );
    }//GEN-LAST:event_IndexbtnMouseExited

    /**
     * abre el mazo de repetidas.
     *
     * @param evento evento de accion
     */
    private void repetidasbtnActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_repetidasbtnActionPerformed
        new MazoRepetidas(album).setVisible(true);

    }//GEN-LAST:event_repetidasbtnActionPerformed

    /**
     * efecto hover al entrar en repetidas.
     *
     * @param evento evento de mouse
     */
    private void repetidasbtnMouseEntered(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_repetidasbtnMouseEntered

        botonRepetidas.setBackground(Color.BLACK);
        botonRepetidas.setForeground(Color.YELLOW);

        botonRepetidas.setSize(
        botonRepetidas.getWidth() + 10,
        botonRepetidas.getHeight() + 10
    );

    }//GEN-LAST:event_repetidasbtnMouseEntered

    /**
     * efecto hover al salir de repetidas.
     *
     * @param evento evento de mouse
     */
    private void repetidasbtnMouseExited(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_repetidasbtnMouseExited

        botonRepetidas.setBackground(colorNormal);
        botonRepetidas.setForeground(Color.BLACK);
        botonRepetidas.setSize(
        botonRepetidas.getWidth() - 10,
        botonRepetidas.getHeight() - 10
    );
    }//GEN-LAST:event_repetidasbtnMouseExited

    /**
     * cierra sesion y cierra la ventana.
     *
     * @param evento evento de accion
     */
    private void SalirActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_SalirActionPerformed
        fachada.cerrarSesionTiempo();
        dispose();

    }//GEN-LAST:event_SalirActionPerformed

    /**
     * efecto hover al entrar en salir.
     *
     * @param evento evento de mouse
     */
    private void SalirMouseEntered(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_SalirMouseEntered

        botonSalir.setBackground(Color.BLACK);
        botonSalir.setForeground(Color.YELLOW);

        botonSalir.setSize(
        botonSalir.getWidth() + 10,
        botonSalir.getHeight() + 10
    );

    }//GEN-LAST:event_SalirMouseEntered

    /**
     * abre un sobre y muestra las figuritas.
     *
     * @param evento evento de accion
     */
    private void sobresbtnActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_sobresbtnActionPerformed
        try {
            ArrayList<Figurita> nuevas;
            if (fachada.getSobresGratis() > 0) {
                fachada.usarSobreGratis();
                nuevas = fachada.abrirSobreGratis();
            } else {
                nuevas = fachada.abrirSobre();
            }

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 5, 10, 10));
            for (Figurita figurita : nuevas) {
                javax.swing.JPanel tarjeta = new javax.swing.JPanel(new java.awt.BorderLayout());
                javax.swing.JLabel etiquetaImagen = new javax.swing.JLabel();
                etiquetaImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                String ruta = figurita.getRutaImagen();
                java.net.URL enlace = Lobby.class.getResource(ruta);
                if (enlace != null) {
                    javax.swing.ImageIcon icono = new javax.swing.ImageIcon(enlace);
                    java.awt.Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    etiquetaImagen.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                }
                javax.swing.JLabel texto = new javax.swing.JLabel("#" + figurita.getNumero() + " - " + figurita.getJugador() + " (" + figurita.getRareza() + ")", javax.swing.SwingConstants.CENTER);
                tarjeta.add(etiquetaImagen, java.awt.BorderLayout.CENTER);
                tarjeta.add(texto, java.awt.BorderLayout.SOUTH);
                panel.add(tarjeta);
            }

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    panel,
                    "Abrir sobres",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            actualizarMonedasLabel();
            actualizarSobresLabel();

        } catch (Exception excepcion) {
            System.err.println("error al abrir el sobre: " + excepcion.getMessage());
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se pudo abrir el sobre: " + excepcion.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_sobresbtnActionPerformed

    /**
     * efecto hover al entrar en configuracion.
     *
     * @param evento evento de mouse
     */
    private void configbtnMouseEntered(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_configbtnMouseEntered

        botonConfiguracion.setBackground(Color.BLACK);
        botonConfiguracion.setForeground(Color.YELLOW);

        botonConfiguracion.setSize(
        botonConfiguracion.getWidth() + 10,
        botonConfiguracion.getHeight() + 10
    );

    }//GEN-LAST:event_configbtnMouseEntered

    /**
     * efecto hover al entrar en sobres.
     *
     * @param evento evento de mouse
     */
    private void sobresbtnMouseEntered(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_sobresbtnMouseEntered

        botonSobres.setBackground(Color.BLACK);
        botonSobres.setForeground(Color.YELLOW);

        botonSobres.setSize(
        botonSobres.getWidth() + 10,
        botonSobres.getHeight() + 10
    );
     }//GEN-LAST:event_sobresbtnMouseEntered

    /**
     * efecto hover al salir de sobres.
     *
     * @param evento evento de mouse
     */
    private void sobresbtnMouseExited(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_sobresbtnMouseExited

        botonSobres.setBackground(colorNormal);
        botonSobres.setForeground(Color.BLACK);
        botonSobres.setSize(
        botonSobres.getWidth() - 10,
        botonSobres.getHeight() - 10
    );
    }//GEN-LAST:event_sobresbtnMouseExited

    /**
     * efecto hover al salir de configuracion.
     *
     * @param evento evento de mouse
     */
    private void configbtnMouseExited(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_configbtnMouseExited

        botonConfiguracion.setBackground(colorNormal);
        botonConfiguracion.setForeground(Color.BLACK);
        botonConfiguracion.setSize(
        botonConfiguracion.getWidth() - 10,
        botonConfiguracion.getHeight() - 10
    );
    }//GEN-LAST:event_configbtnMouseExited

    /**
     * efecto hover al salir de salir.
     *
     * @param evento evento de mouse
     */
    private void SalirMouseExited(java.awt.event.MouseEvent evento) {//GEN-FIRST:event_SalirMouseExited

        botonSalir.setBackground(colorNormal);
        botonSalir.setForeground(Color.BLACK);
        botonSalir.setSize(
        botonSalir.getWidth() - 10,
        botonSalir.getHeight() - 10
    );
    }//GEN-LAST:event_SalirMouseExited

    /**
     * abre la ventana de configuracion.
     *
     * @param evento evento de accion
     */
    private void configbtnActionPerformed(java.awt.event.ActionEvent evento) {//GEN-FIRST:event_configbtnActionPerformed
        new Configuracion(fachada).setVisible(true);
    }//GEN-LAST:event_configbtnActionPerformed

    /**
     * actualiza el label de monedas.
     */
    private void actualizarMonedasLabel() {
        try {
            // el label monedastext muestra las monedas actuales del usuario
            textoMonedas.setText("Monedas: " + fachada.getMonedas());
        } catch (Exception excepcion) {
            System.err.println("error al actualizar monedas: " + excepcion.getMessage());
        }
    }

    /**
     * actualiza el label de sobres.
     */
    private void actualizarSobresLabel() {
        int gratis = fachada.getSobresGratis();
        if (gratis > 0) {
            textoSobresGratis.setText("Sobres gratis: " + gratis);
        } else {
            textoSobresGratis.setText("Precio: " + logic.Sobres.COSTO_SOBRE + " monedas");
        }
    }

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
    private javax.swing.JButton botonIndex;
    private javax.swing.JButton botonRepetidas;
    private javax.swing.JButton botonConfiguracion;
    private javax.swing.JButton botonSalir;
    private javax.swing.JButton botonSobres;
    private javax.swing.JLabel etiqueta2;
    private javax.swing.JLabel etiquetaFondo;
    private javax.swing.JLabel etiquetaLogo;
    private javax.swing.JLabel textoMonedas;
    // End of variables declaration//GEN-END:variables
}