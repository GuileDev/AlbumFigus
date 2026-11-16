package logic;

import Persistencia.Usuario;
import java.util.ArrayList;

/**
 * representa el album de un usuario.
 *
 * guarda las figuritas que tiene y el catalogo disponible.
 *
 * @author gerrz
 */
public class Album {

    public ArrayList<Integer> figuritas = new ArrayList<>();
    public ArrayList<Figurita> catalogo = new ArrayList<>();

    /**
     * crea el album para un usuario y carga sus datos desde la base.
     *
     * @param usuario usuario dueño del album
     */
    public Album(Usuario usuario) {
        try {
            // cada vez que se abre el juego, agarra los datos de la bd y los mete en el album
            this.figuritas = usuario.getFiguritasActuales();
            this.catalogo = usuario.getFiguritasCatalogo();
        } catch (Exception excepcion) {
            System.out.println("error al cargar los datos del album: " + excepcion.getMessage());
        }
    }
}