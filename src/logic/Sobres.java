package logic;

import Persistencia.Usuario;
import java.util.ArrayList;
import java.util.Random;

/**
 * maneja la apertura de sobres y el reparto de figuritas.
 *
 * @author gerrz
 */
public class Sobres {

    public static final int COSTO_SOBRE = 100;

    /**
     * constructor vacio.
     */
    public Sobres() {
    }

    /**
     * abre un sobre pagando el costo normal.
     *
     * @param album album del usuario
     * @param usuario usuario que abre el sobre
     * @return figuritas obtenidas
     * @throws Exception si no hay monedas suficientes o el catalogo esta vacio
     */
    public ArrayList<Figurita> abrirSobre(Album album, Usuario usuario) throws Exception {
        return abrirSobre(album, usuario, COSTO_SOBRE);
    }

    /**
     * abre un sobre gratis.
     *
     * @param album album del usuario
     * @param usuario usuario que abre el sobre
     * @return figuritas obtenidas
     * @throws Exception si el catalogo esta vacio
     */
    public ArrayList<Figurita> abrirSobreGratis(Album album, Usuario usuario) throws Exception {
        return abrirSobre(album, usuario, 0);
    }

    /**
     * abre un sobre con un costo dado.
     *
     * @param album album del usuario
     * @param usuario usuario que abre el sobre
     * @param costo costo en monedas (0 es gratis)
     * @return figuritas obtenidas
     * @throws Exception si no hay monedas suficientes o el catalogo esta vacio
     */
    public ArrayList<Figurita> abrirSobre(Album album, Usuario usuario, int costo) throws Exception {
        if (costo > 0) {
            int monedas = usuario.getMonedasActuales();
            if (monedas < costo) {
                throw new Exception("monedas insuficientes para abrir el sobre.");
            }
            usuario.actualizarMonedas(monedas - costo);
        }

        Random aleatorio = new Random();
        ArrayList<Figurita> nuevas = new ArrayList<>();
        ArrayList<Figurita> catalogo;
        if (album.catalogo != null && !album.catalogo.isEmpty()) {
            catalogo = album.catalogo;
        } else {
            catalogo = usuario.getFiguritasCatalogo();
        }

        ArrayList<Integer> identificadores = new ArrayList<>();
        int recompensaMonedas = 0;

        for (int indice = 0; indice < 5; indice++) {
            int tirada = aleatorio.nextInt(100);
            String rarezaObjetivo;
            if (tirada < 70) {
                rarezaObjetivo = "comun";
            } else if (tirada < 95) {
                rarezaObjetivo = "estrella";
            } else {
                rarezaObjetivo = "especial";
            }

            ArrayList<Figurita> candidatos = new ArrayList<>();
            for (Figurita figurita : catalogo) {
                if (figurita.getRareza() != null) {
                    String rarezaActual = figurita.getRareza().toLowerCase();
                    if (rarezaObjetivo.equals("comun") && rarezaActual.contains("com")) {
                        candidatos.add(figurita);
                    } else if (rarezaObjetivo.equals("estrella") && rarezaActual.contains("estre")) {
                        candidatos.add(figurita);
                    } else if (rarezaObjetivo.equals("especial") && rarezaActual.contains("espec")) {
                        candidatos.add(figurita);
                    }
                }
            }
            if (candidatos.isEmpty()) {
                candidatos = catalogo;
            }
            if (candidatos.isEmpty()) {
                throw new Exception("el catalogo de figuritas esta vacio.");
            }

            Figurita elegida = candidatos.get(aleatorio.nextInt(candidatos.size()));
            int identificadorFigurita = elegida.getIdFigurita();
            if (album.figuritas.contains(identificadorFigurita)) {
                recompensaMonedas += 3;
            } else {
                recompensaMonedas += 1;
            }
            nuevas.add(elegida);
            identificadores.add(identificadorFigurita);
        }

        album.figuritas.addAll(identificadores);
        usuario.actualizarMonedas(usuario.getMonedasActuales() + recompensaMonedas);
        usuario.GuardarProgreso(usuario.getMonedasActuales(), identificadores);

        return nuevas;
    }
}