/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import logic.Album;
import logic.Figurita;

/**
 * window that shows the user's duplicate figuritas.
 * @author gerrz
 */
public class MazoRepetidas extends JFrame {

    private static final java.util.logging.Logger registrador = java.util.logging.Logger.getLogger(MazoRepetidas.class.getName());

    private Album album;
    private final boolean oscuro;

    /**
     * builds the window with the given album.
     */
    public MazoRepetidas(Album album) {
        this.album = album;
        this.oscuro = Preferencias.modoOscuro;
        initComponents();

        // Fuera de la region generada para que la regeneracion del .form no lo borre
        Color fondoPanel;
        if (oscuro) {
            fondoPanel = new Color(30, 30, 36);
        } else {
            fondoPanel = new Color(245, 245, 245);
        }
        getContentPane().setBackground(fondoPanel);
        desplazamiento1.setBorder(null);
        desplazamiento1.getViewport().setBackground(fondoPanel);
        desplazamiento1.getVerticalScrollBar().setUnitIncrement(16);
        desplazamiento1.getHorizontalScrollBar().setUnitIncrement(16);
        if (oscuro) {
            etiquetaContador.setForeground(new Color(235, 235, 235));
        } else {
            etiquetaContador.setForeground(Color.BLACK);
        }
        if (oscuro) {
            etiquetaVacio.setForeground(new Color(185, 190, 215));
        } else {
            etiquetaVacio.setForeground(new Color(120, 120, 120));
        }
        if (oscuro) {
            etiquetaTitulo.setForeground(new Color(255, 255, 51));
        } else {
            etiquetaTitulo.setForeground(new Color(51, 51, 255));
        }

        construirMazo();
        setLocationRelativeTo(null);
    }

    /**
     * initializes the form components. generated code, do not edit.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTitulo = new javax.swing.JLabel();
        etiquetaContador = new javax.swing.JLabel();
        etiquetaVacio = new javax.swing.JLabel();
        desplazamiento1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mazo de repetidas");
        setResizable(false);
        setSize(new java.awt.Dimension(950, 300));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiquetaTitulo.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 28)); // NOI18N
        etiquetaTitulo.setForeground(new java.awt.Color(255, 255, 51));
        etiquetaTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTitulo.setText("Mazo de repetidas");
        getContentPane().add(etiquetaTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 10, 1000, 42));

        etiquetaContador.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 16)); // NOI18N
        etiquetaContador.setForeground(new java.awt.Color(255, 255, 255));
        etiquetaContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaContador.setText("Tenes 0 repetidas");
        getContentPane().add(etiquetaContador, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 60, 1000, 26));

        etiquetaVacio.setFont(new java.awt.Font("Fredoka SemiCondensed", 1, 20)); // NOI18N
        etiquetaVacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaVacio.setText("No tenes figuritas repetidas todavia");
        getContentPane().add(etiquetaVacio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 285, 1000, 40));

        desplazamiento1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(desplazamiento1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 950, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * builds the grid of duplicate cards from the album.
     */
    private void construirMazo() {
        Color fondoPanel;
        if (oscuro) {
            fondoPanel = new Color(30, 30, 36);
        } else {
            fondoPanel = new Color(245, 245, 245);
        }

        JPanel panelCuadricula = new JPanel(new GridLayout(0, 4, 20, 20));
        panelCuadricula.setBackground(fondoPanel);
        panelCuadricula.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        int extras = 0;

        if (album != null) {
            // Cuantas veces tiene el usuario cada figurita
            Map<Integer, Integer> conteo = new HashMap<>();
            for (int identificador : album.figuritas) {
                conteo.merge(identificador, 1, Integer::sum);
            }

            // Las cartas salen ordenadas por numero de figurita
            List<Figurita> catalogoOrdenado = new ArrayList<>(album.catalogo);
            catalogoOrdenado.sort((primera, segunda) -> Integer.compare(primera.getNumero(), segunda.getNumero()));

            for (Figurita figurita : catalogoOrdenado) {
                int cantidad = conteo.getOrDefault(figurita.getIdFigurita(), 0);
                int repetidas = Math.max(0, cantidad - 1);
                for (int indice = 0; indice < repetidas; indice++) {
                    panelCuadricula.add(new CartaRepetida(figurita, oscuro));
                    extras++;
                }
            }
        }

        desplazamiento1.setViewportView(panelCuadricula);

        if (extras == 0) {
            etiquetaContador.setText("No tenes repetidas");
        } else {
            String palabra;
            if (extras == 1) {
                palabra = " repetida";
            } else {
                palabra = " repetidas";
            }
            etiquetaContador.setText("Tenes " + extras + palabra);
        }
        etiquetaVacio.setVisible(extras == 0);
        desplazamiento1.setVisible(extras > 0);
    }

    /**
     * a single card representing one duplicate figurita.
     */
    private static class CartaRepetida extends JPanel {

        private static final int ANCHO = 193;
        private static final int ALTO = 256;

        CartaRepetida(Figurita figurita, boolean oscuro) {
            Color colorPastel;
            Color colorPastelClaro;
            Color colorTexto;
            Color colorTextoSuave;
            Color colorBorde;

            if (oscuro) {
                colorPastel = new Color(96, 106, 150);
                colorPastelClaro = new Color(74, 82, 122);
                colorTexto = new Color(240, 240, 250);
                colorTextoSuave = new Color(185, 190, 215);
                colorBorde = new Color(150, 160, 210);
            } else {
                int numero;
                if (figurita != null) {
                    numero = figurita.getNumero();
                } else {
                    numero = 0;
                }
                java.util.Random aleatorio = new java.util.Random(numero * 131542391L);
                int rojo = 180 + aleatorio.nextInt(60);
                int verde = 180 + aleatorio.nextInt(60);
                int azul = 180 + aleatorio.nextInt(60);
                colorPastel = new Color(rojo, verde, azul);
                colorPastelClaro = new Color(Math.min(255, rojo + 40), Math.min(255, verde + 40), Math.min(255, azul + 40));
                colorTexto = Color.BLACK;
                colorTextoSuave = new Color(120, 120, 120);
                colorBorde = Color.BLACK;
            }

      
  
            setBackground(colorPastelClaro);
            setBorder(BorderFactory.createLineBorder(colorBorde, 2));
            setLayout(null);

            String textoNumero;
            if (figurita != null) {
                textoNumero = String.valueOf(figurita.getNumero());
            } else {
                textoNumero = "?";
            }
            String jugador;
            if (figurita != null) {
                jugador = figurita.getJugador();
            } else {
                jugador = "???";
            }
            String posicion;
            if (figurita != null) {
                posicion = figurita.getPosicion();
            } else {
                posicion = "";
            }
            String rareza;
            if (figurita != null) {
                rareza = figurita.getRareza();
            } else {
                rareza = "";
            }
            String seleccion;
            if (figurita != null && figurita.getSeleccion() != null) {
                seleccion = figurita.getSeleccion().getNombre();
            } else {
                seleccion = "";
            }



            JLabel etiquetaImagen = new JLabel();
            etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
            etiquetaImagen.setBounds(0, 0, ANCHO, ALTO);
            if (figurita != null) {
                String ruta = figurita.getRutaImagen();
                java.net.URL enlace = MazoRepetidas.class.getResource(ruta);
                if (enlace != null) {
                    javax.swing.ImageIcon icono = new javax.swing.ImageIcon(enlace);
                    java.awt.Image imagenEscalada = icono.getImage().getScaledInstance(ANCHO, ALTO, java.awt.Image.SCALE_SMOOTH);
                    etiquetaImagen.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                }
            }
            add(etiquetaImagen);


        }
    }

    /**
     * @param argumentos the command line arguments
     */
    public static void main(String argumentos[]) {

        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo informacion : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(informacion.getName())) {
                    javax.swing.UIManager.setLookAndFeel(informacion.getClassName());
                    break;
                }
            }
        } catch (Exception excepcion) {
            registrador.log(java.util.logging.Level.SEVERE, null, excepcion);
        }

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> new MazoRepetidas(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiquetaContador;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel etiquetaVacio;
    private javax.swing.JScrollPane desplazamiento1;
    // End of variables declaration//GEN-END:variables
}