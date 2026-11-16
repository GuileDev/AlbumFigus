package logic;

import Persistencia.Usuario;
import java.util.ArrayList;
import java.util.Random;

public class Sobres {

    public static final int COSTO_SOBRE = 100;

    public Sobres() {
    }

    public ArrayList<Figurita> abrirSobre(Album album, Usuario usuario) throws Exception {
        return abrirSobre(album, usuario, COSTO_SOBRE);
    }

    public ArrayList<Figurita> abrirSobreGratis(Album album, Usuario usuario) throws Exception {
        return abrirSobre(album, usuario, 0);
    }

    public ArrayList<Figurita> abrirSobre(Album album, Usuario usuario, int costo) throws Exception {
        if (costo > 0) {
            int monedas = usuario.getMonedasActuales();
            if (monedas < costo) {
                throw new Exception("Monedas insuficientes para abrir el sobre.");
            }
            usuario.actualizarMonedas(monedas - costo);
        }

        Random random = new Random();
        ArrayList<Figurita> nuevas = new ArrayList<>();
        ArrayList<Figurita> catalogo = (album.catalogo != null && !album.catalogo.isEmpty())
                ? album.catalogo : usuario.getFiguritasCatalogo();

        ArrayList<Integer> ids = new ArrayList<>();
        int recompensaMonedas = 0;

        for (int i = 0; i < 5; i++) {
            int roll = random.nextInt(100);
            String rarezaObjetivo;
            if (roll < 70) {
                rarezaObjetivo = "comun";
            } else if (roll < 95) {
                rarezaObjetivo = "estrella";
            } else {
                rarezaObjetivo = "especial";
            }

            ArrayList<Figurita> candidatos = new ArrayList<>();
            for (Figurita f : catalogo) {
                if (f.getRareza() != null) {
                    String r = f.getRareza().toLowerCase();
                    if (rarezaObjetivo.equals("comun") && r.contains("com")) {
                        candidatos.add(f);
                    } else if (rarezaObjetivo.equals("estrella") && r.contains("estre")) {
                        candidatos.add(f);
                    } else if (rarezaObjetivo.equals("especial") && r.contains("espec")) {
                        candidatos.add(f);
                    }
                }
            }
            if (candidatos.isEmpty()) {
                candidatos = catalogo;
            }
            if (candidatos.isEmpty()) {
                throw new Exception("El catalogo de figuritas esta vacio.");
            }

            Figurita elegida = candidatos.get(random.nextInt(candidatos.size()));
            int idFig = elegida.getIdFigurita();
            if (album.figuritas.contains(idFig)) {
                recompensaMonedas += 3;
            } else {
                recompensaMonedas += 1;
            }
            nuevas.add(elegida);
            ids.add(idFig);
        }

        album.figuritas.addAll(ids);
        usuario.actualizarMonedas(usuario.getMonedasActuales() + recompensaMonedas);
        usuario.GuardarProgreso(usuario.getMonedasActuales(), ids);

        return nuevas;
    }
}
