
package graphic;
import java.io.File;
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

    private Image fondo;

    LobbyPanel() {
        setBackground(new Color(10, 20, 30));

        // La carpeta ImagenesParaDiseño está junto a los paquetes.
        File archivo = new File(
            "src/ImagenesParaDiseño/lobbyBackground.png"
        );

        if (archivo.exists()) {
            fondo = new ImageIcon(archivo.getPath()).getImage();
        } else {
            System.err.println(
                "No se encontró la imagen: "
                + archivo.getAbsolutePath()
            );
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fondo != null) {
            g.drawImage(
                fondo,
                0, 0,
                getWidth(), getHeight(),
                this
            );
        }
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
