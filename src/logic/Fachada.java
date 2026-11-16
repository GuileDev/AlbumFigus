package logic;

import Persistencia.Usuario;
import java.util.ArrayList;

public class Fachada {

    private Usuario usuario;
    private Album album;
    private Sobres sobres = new Sobres();

    public Fachada() {
    }

    public boolean login(String user, String password) throws Exception {
        usuario = new Usuario();
        if (!usuario.IniciarSesion(user, password)) {
            return false;
        }
        album = new Album(usuario);
        usuario.iniciarSesionTiempo();
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Album getAlbum() {
        return album;
    }

    public ArrayList<Figurita> abrirSobre() throws Exception {
        return sobres.abrirSobre(album, usuario);
    }

    public ArrayList<Figurita> abrirSobreGratis() throws Exception {
        return sobres.abrirSobreGratis(album, usuario);
    }

    public boolean canjearCodigo(String codigo) throws Exception {
        int[] recompensas = usuario.canjearCodigo(codigo);
        if (recompensas == null) {
            return false;
        }
        for (int i = 0; i < recompensas[1]; i++) {
            sobres.abrirSobreGratis(album, usuario);
        }
        return true;
    }

    public boolean[] cargarConfiguracion() throws Exception {
        return usuario.cargarConfiguracion();
    }

    public void guardarConfiguracion(boolean visionNocturna, boolean sonido) throws Exception {
        usuario.guardarConfiguracion(visionNocturna, sonido);
    }

    public int getMonedas() {
        try {
            return usuario.getMonedasActuales();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getSobresGratis() {
        try {
            return usuario.getSobresGratis();
        } catch (Exception e) {
            return 0;
        }
    }

    public void usarSobreGratis() throws Exception {
        int actual = usuario.getSobresGratis();
        if (actual > 0) {
            usuario.actualizarSobresGratis(actual - 1);
        }
    }

    public int reclamarSobres() {
        try {
            return usuario.reclamarSobresActuales();
        } catch (Exception e) {
            System.err.println("Error al reclamar sobres: " + e.getMessage());
            return 0;
        }
    }

    public void cerrarSesionTiempo() {
        try {
            usuario.cerrarSesionTiempo();
        } catch (Exception e) {
            System.err.println("Error al cerrar sesion: " + e.getMessage());
        }
    }
}
