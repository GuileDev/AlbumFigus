package logic;

import Persistencia.Usuario;
import java.util.ArrayList;

public class Album {

    private Usuario usuarioActual;
    public ArrayList<Integer> figuritas = new ArrayList<>();
    public ArrayList<Figurita> catalogo = new ArrayList<>();

    public Album(Usuario usuario) {

        this.usuarioActual = usuario;

        try {
            // Cada vez que se abre el juego, agarra los datos de la bd y los mete en el album
            this.figuritas = usuario.getFiguritasActuales();
            this.catalogo = usuario.getFiguritasCatalogo();
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del album: " + e.getMessage());
        }
    }

}
