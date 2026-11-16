/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import logic.Album;
import logic.Figurita;

/**
 * Main window that shows the sticker album grouped by rarity tabs.
 *
 * @author gerrz
 */
public class Index extends JFrame {

    private static final java.util.logging.Logger registrador = java.util.logging.Logger.getLogger(Index.class.getName());

    private JTabbedPane pestanas;
    private Album album;
    private final boolean oscuro;

    /** Fixed width of a sticker slot. */
    private static final int ANCHO_CASILLA = 193;
    /** Fixed height of a sticker slot. */
    private static final int ALTO_CASILLA = 256;

    /**
     * Creates the album window with the given album data.
     *
     * @param album the album to display.
     */
    public Index(Album album) {
        this.album = album;
        this.oscuro = Preferencias.modoOscuro;
        initComponents();

        // Comfortable window size; the scroll handles extra stickers
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Builds all the tabs, slots and scroll panels for the album.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestanas = new JTabbedPane();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Álbum de Figuritas");
        setResizable(false);

        Color fondoPanel;
        if (oscuro) {
            fondoPanel = new Color(30, 30, 36);
        } else {
            fondoPanel = new Color(245, 245, 245);
        }
        Color fondoPestana;
        if (oscuro) {
            fondoPestana = new Color(38, 38, 46);
        } else {
            fondoPestana = new Color(245, 245, 245);
        }

        // Split the stickers by rarity, one tab per rarity
        ArrayList<String> rarezas = new ArrayList<>();

        if (album != null) {
            for (Figurita figurita : album.catalogo) {
                if (!rarezas.contains(figurita.getRareza())) {
                    rarezas.add(figurita.getRareza());
                }
            }

            for (String rareza : rarezas) {
                GridPanel panelPagina = new GridPanel();
                panelPagina.setBackground(fondoPanel);
                panelPagina.setLayout(new WrapLayout(java.awt.FlowLayout.CENTER, 20, 20));
                panelPagina.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                // Each sticker keeps its own fixed slot (ordered by number)
                for (Figurita figurita : album.catalogo) {
                    if (figurita.getRareza().equals(rareza)) {
                        int cantidad = 0;
                        for (int identificador : album.figuritas) {
                            if (identificador == figurita.getIdFigurita()) {
                                cantidad++;
                            }
                        }
                        panelPagina.add(new SlotPanel(figurita, cantidad));
                    }
                }

                JScrollPane scrollPagina = new JScrollPane(panelPagina);
                scrollPagina.setBorder(null);
                scrollPagina.getViewport().setBackground(fondoPanel);
                scrollPagina.getVerticalScrollBar().setUnitIncrement(16);
                scrollPagina.getHorizontalScrollBar().setUnitIncrement(16);
                pestanas.addTab(rareza, scrollPagina);
            }
        }

        pestanas.setBackground(fondoPestana);
        if (oscuro) {
            pestanas.setForeground(new Color(235, 235, 235));
        } else {
            pestanas.setForeground(Color.BLACK);
        }
        pestanas.setFont(new Font("Segoe UI", Font.BOLD, 14));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(fondoPanel);
        getContentPane().add(pestanas, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Panel that represents a single sticker slot in the album.
     */
    private class SlotPanel extends JPanel {

        private Figurita figurita;
        private boolean tieneFigurita;
        private int cantidad;
        private JLabel etiquetaCantidad;
        private JLabel etiquetaVacio;
        private java.awt.Image imagenOriginal;
        private java.awt.Image imagenEscalada;
        private int anchoEscalado = -1;
        private int altoEscalado = -1;
        private Color colorPastelClaro;
        private Color colorTextoSuave;
        private Color colorBorde;

        /**
         * Creates a slot panel for the given sticker and its owned count.
         *
         * @param figurita the sticker to show.
         * @param cantidad how many copies are owned.
         */
        public SlotPanel(Figurita figurita, int cantidad) {
            this.figurita = figurita;
            this.cantidad = cantidad;
            this.tieneFigurita = cantidad > 0;
            generarColorPastel();
            initSlot();
        }

        /**
         * Picks the colors used by the slot, depending on theme and ownership.
         */
        private void generarColorPastel() {
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

            if (oscuro) {
                colorPastelClaro = new Color(74, 82, 122);
                colorTextoSuave = new Color(185, 190, 215);
                colorBorde = new Color(150, 160, 210);
            } else {
                colorPastelClaro = new Color(Math.min(255, rojo + 40), Math.min(255, verde + 40), Math.min(255, azul + 40));
                colorTextoSuave = new Color(120, 120, 120);
                colorBorde = Color.BLACK;
            }

            // Without the sticker, the slot looks faded
            if (!tieneFigurita) {
                if (oscuro) {
                    colorPastelClaro = new Color(32, 32, 38);
                    colorTextoSuave = new Color(85, 85, 95);
                    colorBorde = new Color(48, 48, 56);
                } else {
                    colorPastelClaro = new Color(215, 215, 215);
                    colorTextoSuave = new Color(170, 170, 170);
                    colorBorde = new Color(190, 190, 190);
                }
            }
        }

        /**
         * Sets up the slot layout, labels and sticker image.
         */
        private void initSlot() {
            setPreferredSize(new Dimension(ANCHO_CASILLA, ALTO_CASILLA));
            setMaximumSize(new Dimension(ANCHO_CASILLA, ALTO_CASILLA));
            setMinimumSize(new Dimension(ANCHO_CASILLA, ALTO_CASILLA));
            setBackground(colorPastelClaro);
            setBorder(BorderFactory.createLineBorder(colorBorde, 2));
            setLayout(null);

            if (cantidad > 1) {
                etiquetaCantidad = new JLabel("x" + cantidad);
                etiquetaCantidad.setFont(new Font("Segoe UI", Font.BOLD, 14));
                etiquetaCantidad.setHorizontalAlignment(SwingConstants.CENTER);
                etiquetaCantidad.setForeground(colorTextoSuave);
                etiquetaCantidad.setBounds(ANCHO_CASILLA - 48, 8, 44, 25);
                add(etiquetaCantidad);
            }

            if (figurita != null) {
                String ruta = figurita.getRutaImagen();
                java.net.URL enlace = Index.class.getResource(ruta);
                if (enlace != null) {
                    javax.swing.ImageIcon icono = new javax.swing.ImageIcon(enlace);
                    imagenOriginal = icono.getImage();
                }
            }

            if (!tieneFigurita) {
                etiquetaVacio = new JLabel("SIN FIGURITA");
                etiquetaVacio.setFont(new Font("Segoe UI", Font.BOLD, 11));
                etiquetaVacio.setHorizontalAlignment(SwingConstants.CENTER);
                etiquetaVacio.setForeground(colorTextoSuave);
                etiquetaVacio.setBounds(0, ALTO_CASILLA / 2, ANCHO_CASILLA, 25);
                add(etiquetaVacio);
            }
        }

        @Override
        protected void paintComponent(java.awt.Graphics graficos) {
            super.paintComponent(graficos);
            if (tieneFigurita && imagenOriginal != null) {
                int ancho = getWidth();
                int alto = getHeight();
                if (ancho <= 0 || alto <= 0) {
                    return;
                }
                if (imagenEscalada == null || anchoEscalado != ancho || altoEscalado != alto) {
                    imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
                    anchoEscalado = ancho;
                    altoEscalado = alto;
                }
                graficos.drawImage(imagenEscalada, 0, 0, this);
            }
        }
    }

    /**
     * Flow layout that wraps components to the next line.
     */
    static class WrapLayout extends java.awt.FlowLayout {
        /**
         * Creates a wrap layout with the given alignment and gaps.
         *
         * @param alineacion horizontal alignment.
         * @param espacioHorizontal horizontal gap.
         * @param espacioVertical vertical gap.
         */
        WrapLayout(int alineacion, int espacioHorizontal, int espacioVertical) {
            super(alineacion, espacioHorizontal, espacioVertical);
        }

        @Override
        public Dimension preferredLayoutSize(java.awt.Container objetivo) {
            synchronized (objetivo.getTreeLock()) {
                int anchoObjetivo;
                if (objetivo.getParent() != null) {
                    anchoObjetivo = objetivo.getParent().getWidth();
                } else {
                    anchoObjetivo = 0;
                }
                if (anchoObjetivo <= 0) {
                    anchoObjetivo = Integer.MAX_VALUE;
                }
                int espacioHorizontal = getHgap();
                int espacioVertical = getVgap();
                java.awt.Insets margenes = objetivo.getInsets();
                int anchoMaximo = anchoObjetivo - margenes.left - margenes.right;
                int posicionX = 0, posicionY = margenes.top, altoFila = 0, cantidadComponentes = objetivo.getComponentCount();
                for (int indice = 0; indice < cantidadComponentes; indice++) {
                    java.awt.Component componente = objetivo.getComponent(indice);
                    if (!componente.isVisible()) continue;
                    Dimension dimension = componente.getPreferredSize();
                    if (posicionX + dimension.width > anchoMaximo && posicionX > 0) {
                        posicionX = 0;
                        posicionY += altoFila + espacioVertical;
                        altoFila = 0;
                    }
                    posicionX += dimension.width + espacioHorizontal;
                    altoFila = Math.max(altoFila, dimension.height);
                }
                posicionY += altoFila + margenes.bottom;
                return new Dimension(anchoObjetivo, posicionY);
            }
        }
    }

    /**
     * Panel that implements scrollable behavior for the sticker grid.
     */
    static class GridPanel extends JPanel implements javax.swing.Scrollable {
        /**
         * Creates an empty grid panel.
         */
        GridPanel() {
            super();
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(java.awt.Rectangle rectVisible, int orientacion, int direccion) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(java.awt.Rectangle rectVisible, int orientacion, int direccion) {
            return 16;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    /**
     * Starts the album window.
     *
     * @param argumentos command line arguments.
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
        SwingUtilities.invokeLater(() -> new Index(null).setVisible(true));
    }
}