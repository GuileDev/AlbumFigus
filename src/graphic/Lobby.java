
package graphic;

import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;

public class Lobby extends JFrame {

    private final Color amarillo = new Color(255, 190, 0);
    private final Color oscuro = new Color(10, 20, 30);

    private final LobbyPanel panel = new LobbyPanel();

    private final JButton btnAlbum = new JButton("▤   ÁLBUM");
    private final JButton btnFiguritas = new JButton("▢   FIGURITAS");
    private final JButton btnConfiguracion = new JButton("⚙   CONFIGURACIÓN");
    private final JButton btnSalir = new JButton("⇥   SALIR");

    public Lobby() {
        setTitle("Álbum de Figuritas - Fútbol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        panel.setLayout(null);
        setContentPane(panel);

        configurarBoton(btnAlbum, true);
        configurarBoton(btnFiguritas, false);
        configurarBoton(btnConfiguracion, false);
        configurarBoton(btnSalir, false);

        panel.add(btnAlbum);
        panel.add(btnFiguritas);
        panel.add(btnConfiguracion);
        panel.add(btnSalir);

        // EVENTO: botón ÁLBUM
        btnAlbum.addActionListener(e -> {
         new Lobby2().setVisible(true);
         this.setVisible(false);
        });

        // EVENTO: botón FIGURITAS
        btnFiguritas.addActionListener(e -> {
  
        });

        // EVENTO: botón CONFIGURACIÓN
        btnConfiguracion.addActionListener(e -> {
  new Configuracion().setVisible(true);
        });

        // EVENTO: botón SALIR
        btnSalir.addActionListener(e -> {
            // TODO: Cerrar el programa.
            dispose();
        });

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(
                    java.awt.event.ComponentEvent e) {
                acomodarBotones();
            }
        });

        acomodarBotones();
    }

    private void configurarBoton(JButton boton, boolean principal) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 20));
        boton.setForeground(principal ? oscuro : Color.WHITE);
        boton.setBackground(principal ? amarillo : oscuro);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(amarillo);
                boton.setForeground(oscuro);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(principal
                        ? amarillo : oscuro);
                boton.setForeground(principal
                        ? oscuro : Color.WHITE);
            }
        });
    }

    private void acomodarBotones() {
        int ancho = panel.getWidth();
        int alto = panel.getHeight();

        if (ancho <= 0 || alto <= 0) {
            return;
        }

        int botonAncho = Math.min(430, ancho - 60);
        int botonAlto = 62;
        int x = (ancho - botonAncho) / 2;
        int y = Math.max(300, (int) (alto * 0.52));
        int separacion = 17;

        btnAlbum.setBounds(x, y, botonAncho, botonAlto);
        btnFiguritas.setBounds(
                x, y + botonAlto + separacion,
                botonAncho, botonAlto);
        btnConfiguracion.setBounds(
                x, y + 2 * (botonAlto + separacion),
                botonAncho, botonAlto);
        btnSalir.setBounds(
                x, y + 3 * (botonAlto + separacion),
                botonAncho, botonAlto);
    }

    private class LobbyPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Fondo del estadio.
            g2.setPaint(new GradientPaint(
                    0, 0, new Color(5, 17, 32),
                    0, h, new Color(9, 48, 35)));
            g2.fillRect(0, 0, w, h);

            // Gradas.
            g2.setColor(new Color(17, 33, 45));
            g2.fillRect(0, h / 3, w, h / 3);

            for (int i = 0; i < 9; i++) {
                int y = h / 3 + i * 16;
                g2.setColor(new Color(255, 255, 255, 12));
                g2.drawLine(0, y, w, y);
            }

            // Focos.
            dibujarFocos(g2, 35, 80);
            dibujarFocos(g2, w - 105, 80);

            // Cancha.
            int canchaY = (int) (h * 0.67);

            g2.setPaint(new GradientPaint(
                    0, canchaY, new Color(27, 91, 43),
                    0, h, new Color(3, 43, 24)));
            g2.fillRect(0, canchaY, w, h - canchaY);

            for (int i = 0; i < 8; i++) {
                g2.setColor(i % 2 == 0
                        ? new Color(255, 255, 255, 9)
                        : new Color(0, 0, 0, 12));

                g2.fillRect(i * w / 8, canchaY,
                        w / 8 + 1, h - canchaY);
            }

            g2.setColor(new Color(220, 240, 220, 55));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(w / 2, canchaY, w / 2, h);
            g2.drawOval(w / 2 - 55, canchaY + 20, 110, 110);

            // Título.
            dibujarTitulo(g2, w);

            // Pelota decorativa.
            dibujarPelota(g2, -65, h - 180, 250);

            // Figuritas decorativas.
            dibujarFigurita(g2, w - 180, h - 220,
                    new Color(22, 83, 145), -15);
            dibujarFigurita(g2, w - 120, h - 170,
                    new Color(31, 119, 73), 12);
            dibujarFigurita(g2, w - 205, h - 125,
                    new Color(202, 151, 12), -28);

            g2.dispose();
        }

        private void dibujarFocos(Graphics2D g2, int x, int y) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    int px = x + i * 15;
                    int py = y + j * 17;

                    g2.setColor(new Color(150, 200, 255, 28));
                    g2.fillOval(px - 8, py - 8, 28, 28);

                    g2.setColor(new Color(230, 240, 255, 190));
                    g2.fillOval(px, py, 10, 10);
                }
            }
        }

        private void dibujarTitulo(Graphics2D g2, int w) {
            int cx = w / 2;
            int ancho = Math.min(500, w - 80);
            int x = cx - ancho / 2;
            int y = 35;

            Path2D escudo = new Path2D.Double();
            escudo.moveTo(x + 45, y + 25);
            escudo.lineTo(x + ancho - 45, y + 25);
            escudo.lineTo(x + ancho, y + 65);
            escudo.lineTo(x + ancho - 20, y + 145);
            escudo.lineTo(x + ancho - 55, y + 165);
            escudo.lineTo(x + 55, y + 165);
            escudo.lineTo(x + 20, y + 145);
            escudo.lineTo(x, y + 65);
            escudo.closePath();

            g2.setColor(new Color(4, 12, 20, 235));
            g2.fill(escudo);

            g2.setColor(amarillo);
            g2.setStroke(new BasicStroke(4));
            g2.draw(escudo);

            g2.setColor(Color.WHITE);
            g2.fillOval(cx - 24, y - 12, 48, 48);

            g2.setColor(oscuro);
            g2.fillOval(cx - 8, y + 4, 16, 16);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 27));
            centrarTexto(g2, "ÁLBUM DE", cx, y + 66);

            g2.setFont(new Font("SansSerif", Font.BOLD, 43));
            centrarTexto(g2, "FIGURITAS", cx, y + 111);

            g2.setColor(amarillo);
            g2.setFont(new Font("SansSerif", Font.BOLD, 22));
            centrarTexto(g2, "F Ú T B O L", cx, y + 145);
        }

        private void centrarTexto(Graphics2D g2, String texto,
                                  int cx, int y) {
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(texto,
                    cx - fm.stringWidth(texto) / 2, y);
        }

        private void dibujarPelota(Graphics2D g2,
                                   int x, int y, int tam) {
            g2.setColor(new Color(0, 0, 0, 65));
            g2.fillOval(x + 8, y + 8, tam, tam);

            g2.setColor(new Color(235, 235, 225));
            g2.fillOval(x, y, tam, tam);

            g2.setColor(new Color(20, 25, 30));
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(x, y, tam, tam);

            int cx = x + tam / 2;
            int cy = y + tam / 2;
            int r = tam / 5;

            Polygon pentagono = new Polygon();

            for (int i = 0; i < 5; i++) {
                double a = Math.toRadians(-90 + i * 72);
                pentagono.addPoint(
                        cx + (int) (r * Math.cos(a)),
                        cy + (int) (r * Math.sin(a)));
            }

            g2.fillPolygon(pentagono);
        }

        private void dibujarFigurita(Graphics2D g2,
                                     int x, int y,
                                     Color color, double angulo) {
            Graphics2D card = (Graphics2D) g2.create();
            card.rotate(Math.toRadians(angulo), x + 45, y + 60);

            card.setColor(new Color(225, 225, 215));
            card.fillRoundRect(x, y, 90, 120, 4, 4);

            card.setColor(color);
            card.fillRect(x + 6, y + 6, 78, 108);

            card.setColor(new Color(5, 20, 28, 200));
            card.fillOval(x + 33, y + 24, 25, 25);

            card.setStroke(new BasicStroke(10));
            card.drawLine(x + 45, y + 50, x + 43, y + 82);
            card.drawLine(x + 43, y + 60, x + 25, y + 74);
            card.drawLine(x + 43, y + 82, x + 25, y + 104);
            card.drawLine(x + 43, y + 82, x + 65, y + 101);

            card.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            new Lobby().setVisible(true);
        });
    }
}
