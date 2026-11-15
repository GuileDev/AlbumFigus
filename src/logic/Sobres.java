/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import Persistencia.Usuario;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase encargada de otorgar los sobres de figuritas.
 * Le otorga las figuritas al album localmente y a la base de datos con inserts.
 *
 * @author gerrz
 */
public class Sobres {

    public Sobres() {

    }

    // Abre un sobre y le otorga 5 figuritas al album y a la base de datos
    public ArrayList<Figurita> abrirSobre(Album album, Usuario usuario) throws Exception {

        Random random = new Random();
        ArrayList<Figurita> nuevas = new ArrayList<>();
        ArrayList<Figurita> catalogo = usuario.getFiguritasCatalogo();

        for (int i = 0; i < 5; i++) {
            int indice = random.nextInt(catalogo.size());
            nuevas.add(catalogo.get(indice));
        }

        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 0; i < nuevas.size(); i++) {
            ids.add(nuevas.get(i).getIdFigurita());
        }

        // Local: mete las figuritas en el array del album
        album.figuritas.addAll(ids);

        // Base de datos: inserts en almacenamiento (clase Usuario)
        usuario.GuardarProgreso(usuario.getMonedasActuales(), ids);

        return nuevas;
    }
}
