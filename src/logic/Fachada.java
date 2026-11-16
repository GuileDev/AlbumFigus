package logic;

import Persistencia.Usuario;
import java.util.ArrayList;

/**
 * fachada entre la interfaz grafica y la logica del juego.
 *
 * @author gerrz
 */
public class Fachada {

    private Usuario usuario;
    private Album album;
    private Sobres sobres = new Sobres();

    /**
     * constructor vacio.
     */
    public Fachada() {
    }

    /**
     * inicia sesion con un usuario y contraseña.
     *
     * @param nombreUsuario nombre de usuario
     * @param contraseña contraseña (no se valida por ahora)
     * @return true si el login fue correcto
     * @throws Exception si falla la base de datos
     */
    public boolean login(String nombreUsuario, String contraseña) throws Exception {
        usuario = new Usuario();
        if (!usuario.IniciarSesion(nombreUsuario, contraseña)) {
            return false;
        }
        album = new Album(usuario);
        usuario.iniciarSesionTiempo();
        return true;
    }

    /**
     * devuelve el usuario actual.
     *
     * @return usuario logueado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * devuelve el album actual.
     *
     * @return album del usuario
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * abre un sobre pagando.
     *
     * @return figuritas obtenidas
     * @throws Exception si no alcanzan las monedas
     */
    public ArrayList<Figurita> abrirSobre() throws Exception {
        return sobres.abrirSobre(album, usuario);
    }

    /**
     * abre un sobre gratis.
     *
     * @return figuritas obtenidas
     * @throws Exception si falla
     */
    public ArrayList<Figurita> abrirSobreGratis() throws Exception {
        return sobres.abrirSobreGratis(album, usuario);
    }

    /**
     * canjea un codigo de recompensa.
     *
     * @param codigo codigo a canjear
     * @return true si se canjeo correctamente
     * @throws Exception si falla
     */
    public boolean canjearCodigo(String codigo) throws Exception {
        int[] recompensas = usuario.canjearCodigo(codigo);
        if (recompensas == null) {
            return false;
        }
        for (int indice = 0; indice < recompensas[1]; indice++) {
            sobres.abrirSobreGratis(album, usuario);
        }
        return true;
    }

    /**
     * carga la configuracion del usuario.
     *
     * @return arreglo con [vision nocturna, sonido]
     * @throws Exception si falla
     */
    public boolean[] cargarConfiguracion() throws Exception {
        return usuario.cargarConfiguracion();
    }

    /**
     * guarda la configuracion del usuario.
     *
     * @param visionNocturna activa el modo oscuro
     * @param sonido activa el sonido
     * @throws Exception si falla
     */
    public void guardarConfiguracion(boolean visionNocturna, boolean sonido) throws Exception {
        usuario.guardarConfiguracion(visionNocturna, sonido);
    }

    /**
     * devuelve las monedas actuales.
     *
     * @return cantidad de monedas
     */
    public int getMonedas() {
        try {
            return usuario.getMonedasActuales();
        } catch (Exception excepcion) {
            return 0;
        }
    }

    /**
     * devuelve los sobres gratis disponibles.
     *
     * @return cantidad de sobres gratis
     */
    public int getSobresGratis() {
        try {
            return usuario.getSobresGratis();
        } catch (Exception excepcion) {
            return 0;
        }
    }

    /**
     * usa un sobre gratis.
     *
     * @throws Exception si falla
     */
    public void usarSobreGratis() throws Exception {
        int actual = usuario.getSobresGratis();
        if (actual > 0) {
            usuario.actualizarSobresGratis(actual - 1);
        }
    }

    /**
     * reclama sobres por tiempo.
     *
     * @return cantidad de sobres reclamados
     */
    public int reclamarSobres() {
        try {
            return usuario.reclamarSobresActuales();
        } catch (Exception excepcion) {
            System.err.println("error al reclamar sobres: " + excepcion.getMessage());
            return 0;
        }
    }

    /**
     * cierra la sesion por tiempo.
     */
    public void cerrarSesionTiempo() {
        try {
            usuario.cerrarSesionTiempo();
        } catch (Exception excepcion) {
            System.err.println("error al cerrar sesion: " + excepcion.getMessage());
        }
    }
}