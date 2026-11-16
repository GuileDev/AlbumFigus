package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import logic.Figurita;
import logic.Seleccion;

/**
 * maneja los datos del usuario y la base de datos.
 *
 * @author gerrz
 */
public class Usuario {

    private Conexion conexion = Conexion.getInstancia();
    private int identificador;
    private String nombreUsuario;
    private int monedas;
    private int idSesionActual = -1;

    /**
     * crea la base de datos y las tablas si no existen.
     *
     * @throws Exception si falla la conexion
     */
    public Usuario() throws Exception {

        String direccionServidor = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true";

        try (Connection conexionBD = DriverManager.getConnection(direccionServidor, "root", "1234")) {

            conexionBD.createStatement().execute("CREATE DATABASE IF NOT EXISTS album");
            conexionBD.createStatement().execute("USE album");

            String crearUsuario = "CREATE TABLE IF NOT EXISTS usuario (id INT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, monedas INT NOT NULL, sobres_gratis INT NOT NULL DEFAULT 0)";
            String crearSeleccion = "CREATE TABLE IF NOT EXISTS seleccion (id INT PRIMARY KEY, nombre VARCHAR(50) NOT NULL, grupo CHAR(1) NOT NULL)";
            String crearFigurita = "CREATE TABLE IF NOT EXISTS figurita (id INT PRIMARY KEY, numero INT NOT NULL, jugador VARCHAR(100) NOT NULL, posicion VARCHAR(30) NOT NULL, rareza VARCHAR(20) NOT NULL, id_seleccion INT NOT NULL, imagen VARCHAR(100), FOREIGN KEY (id_seleccion) REFERENCES seleccion(id))";
            String crearAlmacenamiento = "CREATE TABLE IF NOT EXISTS almacenamiento (id_usuario INT NOT NULL, id_figurita INT NOT NULL, cantidad INT NOT NULL DEFAULT 0, PRIMARY KEY (id_usuario, id_figurita), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (id_figurita) REFERENCES figurita(id))";
            String crearCodigo = "CREATE TABLE IF NOT EXISTS codigo (codigo VARCHAR(50) PRIMARY KEY, recompensamonedas INT NOT NULL, recompensasobres INT NOT NULL)";
            String crearCanje = "CREATE TABLE IF NOT EXISTS canje (id_usuario INT NOT NULL, codigo VARCHAR(20) NOT NULL, fecha DATETIME NOT NULL, PRIMARY KEY (id_usuario, codigo), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (codigo) REFERENCES codigo(codigo))";
            String crearConfiguracion = "CREATE TABLE IF NOT EXISTS configuracion (id_usuario INT PRIMARY KEY, visionnocturna BOOLEAN NOT NULL, sonido BOOLEAN NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";
            String crearRevivir = "CREATE TABLE IF NOT EXISTS revivir (id INT PRIMARY KEY, id_usuario INT NOT NULL, consigna VARCHAR(100) NOT NULL, multiplicador INT NOT NULL, completado BOOLEAN NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";
            String crearSesion = "CREATE TABLE IF NOT EXISTS sesion (id INT PRIMARY KEY, id_usuario INT NOT NULL, fecha_entrada DATETIME NOT NULL, fecha_salida DATETIME, ultima_recompensa DATETIME NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";

            conexionBD.createStatement().execute(crearUsuario);
            conexionBD.createStatement().execute(crearSeleccion);
            conexionBD.createStatement().execute(crearFigurita);
            conexionBD.createStatement().execute(crearAlmacenamiento);
            conexionBD.createStatement().execute(crearCodigo);
            conexionBD.createStatement().execute(crearCanje);
            conexionBD.createStatement().execute(crearConfiguracion);
            conexionBD.createStatement().execute(crearRevivir);
            conexionBD.createStatement().execute(crearSesion);
            try {
                conexionBD.createStatement().execute("ALTER TABLE usuario ADD COLUMN sobres_gratis INT NOT NULL DEFAULT 0");
            } catch (SQLException excepcionIgnorada) {
            }
            try {
                conexionBD.createStatement().execute("ALTER TABLE figurita ADD COLUMN imagen VARCHAR(100)");
            } catch (SQLException excepcionIgnorada) {
            }

            String semillaSeleccion = "INSERT IGNORE INTO seleccion (id, nombre, grupo) VALUES (1, 'Uruguay', 'A'), (2, 'Brasil', 'B'), (3, 'Argentina', 'C'), (4, 'Francia', 'D'), (5, 'Belgica', 'E'), (6, 'Croacia', 'F'), (7, 'Inglaterra', 'G'), (8, 'Canada', 'H'), (9, 'Portugal', 'I'), (10, 'Espana', 'J'), (11, 'Paises Bajos', 'K')";
            conexionBD.createStatement().execute(semillaSeleccion);

            String semillaFigurita = "INSERT INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion, imagen) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE numero = VALUES(numero), jugador = VALUES(jugador), posicion = VALUES(posicion), rareza = VALUES(rareza), id_seleccion = VALUES(id_seleccion), imagen = VALUES(imagen)";
            String[][] catalogo = {
                    {"1", "1", "Lionel Messi", "Delantero", "Estrella", "3", "1_lionel_messi.png"},
                    {"2", "2", "Emiliano Martinez", "Portero", "Común", "3", "2_emiliano_martinez.png"},
                    {"3", "3", "Julian Alvarez", "Delantero", "Común", "3", "3_julian_alvarez.png"},
                    {"4", "4", "Lautaro Martinez", "Delantero", "Especial", "3", "4_lautaro_martinez.png"},
                    {"5", "5", "Enzo Fernandez", "Mediocampista", "Estrella", "3", "5_enzo_fernandez.png"},
                    {"6", "6", "Alexis Mac Allister", "Mediocampista", "Común", "3", "6_alexis_mac_allister.png"},
                    {"7", "7", "Rodrigo De Paul", "Mediocampista", "Común", "3", "7_rodrigo_de_paul.png"},
                    {"8", "8", "Nicolas Otamendi", "Defensa", "Común", "3", "8_nicolas_otamendi.png"},
                    {"9", "9", "Cristian Romero", "Defensa", "Común", "3", "9_cristian_romero.png"},
                    {"10", "10", "Nahuel Molina", "Defensa", "Común", "3", "10_nahuel_molina.png"},
                    {"11", "11", "Escudo Argentina", "Escudo", "Especial", "3", "11_escudo_argentina.png"},
                    {"12", "12", "Equipo Argentina", "Equipo", "Estrella", "3", "12_equipo_argentina.png"},
                    {"13", "13", "Neymar Jr.", "Delantero", "Estrella", "2", "13_neymar_jr.png"},
                    {"14", "14", "Vinicius Jr.", "Delantero", "Estrella", "2", "14_vinicius_jr.png"},
                    {"15", "15", "Matheus Cunha", "Delantero", "Común", "2", "15_matheus_cunha.png"},
                    {"16", "16", "Alisson Becker", "Portero", "Común", "2", "16_alisson_becker.png"},
                    {"17", "17", "Gabriel Martinelli", "Delantero", "Común", "2", "17_gabriel_martinelli.png"},
                    {"18", "18", "Casemiro", "Mediocampista", "Común", "2", "18_casemiro.png"},
                    {"19", "19", "Raphinha", "Delantero", "Común", "2", "19_raphinha.png"},
                    {"20", "20", "Marquinhos", "Defensa", "Común", "2", "20_marquinhos.png"},
                    {"21", "21", "Gabriel Magalhaes", "Defensa", "Común", "2", "21_gabriel_magalhaes.png"},
                    {"22", "22", "Bruno Guimaraes", "Mediocampista", "Común", "2", "22_bruno_guimaraes.png"},
                    {"23", "23", "Escudo Brasil", "Escudo", "Especial", "2", "23_escudo_brasil.png"},
                    {"24", "24", "Federico Valverde", "Mediocampista", "Estrella", "1", "24_federico_valverde.png"},
                    {"25", "25", "Mathias Olivera", "Defensa", "Común", "1", "25_mathias_olivera.png"},
                    {"26", "26", "Ronald Araujo", "Defensa", "Común", "1", "26_ronald_araujo.png"},
                    {"27", "27", "Darwin Nunez", "Delantero", "Especial", "1", "27_darwin_nunez.png"},
                    {"28", "28", "Jose Maria Gimenez", "Defensa", "Común", "1", "28_jose_maria_gimenez.png"},
                    {"29", "29", "Rodrigo Bentancur", "Mediocampista", "Común", "1", "29_rodrigo_bentancur.png"},
                    {"30", "30", "Manuel Ugarte", "Mediocampista", "Común", "1", "30_manuel_ugarte.png"},
                    {"31", "31", "Sergio Rochet", "Portero", "Común", "1", "31_sergio_rochet.png"},
                    {"32", "32", "Maximiliano Araujo", "Delantero", "Común", "1", "32_maxi_araujo.png"},
                    {"33", "33", "Facundo Pellistri", "Delantero", "Común", "1", "33_facundo_pellistri.png"}
            };

            try (PreparedStatement sentencia = conexionBD.prepareStatement(semillaFigurita)) {
                for (String[] fila : catalogo) {
                    sentencia.setInt(1, Integer.parseInt(fila[0]));
                    sentencia.setInt(2, Integer.parseInt(fila[1]));
                    sentencia.setString(3, fila[2]);
                    sentencia.setString(4, fila[3]);
                    sentencia.setString(5, fila[4]);
                    sentencia.setInt(6, Integer.parseInt(fila[5]));
                    sentencia.setString(7, fila[6]);
                    sentencia.addBatch();
                }
                sentencia.executeBatch();
            }

            String semillaCodigo = "INSERT IGNORE INTO codigo (codigo, recompensamonedas, recompensasobres) VALUES ('ITI2026', 500, 1), ('MUNDIAL', 1000, 2), ('FIGUS', 200, 0)";
            conexionBD.createStatement().execute(semillaCodigo);

        } catch (SQLException excepcion) {
            System.out.println("error al crear la base de datos: " + excepcion.getMessage());
        }
    }

    private int siguienteId(Connection conexionBD, String tabla) throws SQLException {
        try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT MAX(id) FROM " + tabla);
             ResultSet resultado = sentencia.executeQuery()) {
            if (resultado.next()) {
                int maximo = resultado.getInt(1);
                if (resultado.wasNull()) {
                    return 1;
                }
                return maximo + 1;
            }
        }
        return 1;
    }

    /**
     * inicia sesion con un usuario, lo crea si no existe.
     *
     * @param nombre nombre de usuario
     * @param contrasena contraseña (no se valida)
     * @return true si la sesion se inicio
     * @throws Exception si falla la base de datos
     */
    public boolean IniciarSesion(String nombre, String contrasena) throws Exception {
        try (Connection conexionBD = conexion.getConnection()) {
            String consulta = "SELECT id, monedas FROM usuario WHERE username = ?";
            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setString(1, nombre);
                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        identificador = resultado.getInt("id");
                        nombreUsuario = nombre;
                        monedas = resultado.getInt("monedas");
                        return true;
                    }
                }
            }

            int nuevoId = siguienteId(conexionBD, "usuario");
            String insercion = "INSERT INTO usuario (id, username, monedas) VALUES (?, ?, ?)";
            try (PreparedStatement sentencia = conexionBD.prepareStatement(insercion)) {
                sentencia.setInt(1, nuevoId);
                sentencia.setString(2, nombre);
                sentencia.setInt(3, 1000);
                sentencia.executeUpdate();
            }
            identificador = nuevoId;
            nombreUsuario = nombre;
            monedas = 1000;

            try (PreparedStatement sentencia = conexionBD.prepareStatement("INSERT IGNORE INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?)")) {
                sentencia.setInt(1, nuevoId);
                sentencia.setBoolean(2, false);
                sentencia.setBoolean(3, true);
                sentencia.executeUpdate();
            }

            return true;
        }
    }

    /**
     * guarda el progreso del usuario.
     *
     * @param monedasNuevas monedas actualizadas
     * @param figuritas lista de ids de figuritas nuevas
     * @throws Exception si falla la base de datos
     */
    public void GuardarProgreso(int monedasNuevas, ArrayList<Integer> figuritas) throws Exception {
        this.monedas = monedasNuevas;

        try (Connection conexionBD = conexion.getConnection()) {
            conexionBD.setAutoCommit(false);
            try {
                try (PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
                    sentencia.setInt(1, monedasNuevas);
                    sentencia.setInt(2, identificador);
                    sentencia.executeUpdate();
                }

                if (figuritas != null && !figuritas.isEmpty()) {
                    String consulta = "INSERT INTO almacenamiento (id_usuario, id_figurita, cantidad) VALUES (?, ?, 1) "
                            + "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";
                    try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                        for (int idFigurita : figuritas) {
                            sentencia.setInt(1, identificador);
                            sentencia.setInt(2, idFigurita);
                            sentencia.addBatch();
                        }
                        sentencia.executeBatch();
                    }
                }

                conexionBD.commit();
            } catch (SQLException excepcion) {
                conexionBD.rollback();
                throw excepcion;
            }
        } catch (SQLException excepcion) {
            System.err.println("error al guardar progreso: " + excepcion.getMessage());
        }
    }

    /**
     * devuelve el nombre del usuario actual.
     *
     * @return nombre de usuario
     * @throws Exception si falla
     */
    public String getUsuarioActual() throws Exception {
        return nombreUsuario;
    }

    /**
     * obtiene el catalogo de figuritas.
     *
     * @return lista de figuritas del catalogo
     * @throws Exception si falla
     */
    public ArrayList<Figurita> getFiguritasCatalogo() throws Exception {
        String consulta = "SELECT f.id, f.numero, f.jugador, f.posicion, f.rareza, f.imagen, s.id AS sel_id, s.nombre AS sel_nombre, s.grupo AS sel_grupo "
                + "FROM figurita f JOIN seleccion s ON s.id = f.id_seleccion ORDER BY f.numero";

        ArrayList<Figurita> lista = new ArrayList<>();

        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Seleccion seleccion = new Seleccion(resultado.getInt("sel_id"), resultado.getString("sel_nombre"), resultado.getString("sel_grupo").charAt(0));
                Figurita figurita = new Figurita(resultado.getInt("id"), resultado.getInt("numero"), resultado.getString("jugador"), resultado.getString("posicion"), resultado.getString("rareza"), seleccion, resultado.getString("imagen"));
                lista.add(figurita);
            }

        } catch (SQLException excepcion) {
            System.err.println("error al obtener el catalogo de figuritas: " + excepcion.getMessage());
        }

        return lista;
    }

    /**
     * obtiene las figuritas del usuario.
     *
     * @return lista de ids de figuritas
     * @throws Exception si falla
     */
    public ArrayList<Integer> getFiguritasActuales() throws Exception {
        String consulta = "SELECT id_figurita, cantidad FROM almacenamiento WHERE id_usuario = ?";

        ArrayList<Integer> lista = new ArrayList<>();

        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            sentencia.setInt(1, identificador);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    int idFigurita = resultado.getInt("id_figurita");
                    int cantidad = resultado.getInt("cantidad");
                    for (int indice = 0; indice < cantidad; indice++) {
                        lista.add(idFigurita);
                    }
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("error al obtener las figuritas del usuario: " + excepcion.getMessage());
        }

        return lista;
    }

    /**
     * obtiene las monedas actuales del usuario.
     *
     * @return cantidad de monedas
     * @throws Exception si falla
     */
    public int getMonedasActuales() throws Exception {
        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement("SELECT monedas FROM usuario WHERE id = ?")) {
            sentencia.setInt(1, identificador);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    monedas = resultado.getInt("monedas");
                    return monedas;
                }
            }
        } catch (SQLException excepcion) {
            System.err.println("error al obtener monedas: " + excepcion.getMessage());
        }
        return monedas;
    }

    /**
     * actualiza las monedas del usuario.
     *
     * @param nuevas nuevas monedas
     * @throws Exception si falla
     */
    public void actualizarMonedas(int nuevas) throws Exception {
        monedas = nuevas;
        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
            sentencia.setInt(1, nuevas);
            sentencia.setInt(2, identificador);
            sentencia.executeUpdate();
        }
    }

    /**
     * carga la configuracion del usuario.
     *
     * @return arreglo con [vision nocturna, sonido]
     * @throws Exception si falla
     */
    public boolean[] cargarConfiguracion() throws Exception {
        try (Connection conexionBD = conexion.getConnection()) {
            try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT visionnocturna, sonido FROM configuracion WHERE id_usuario = ?")) {
                sentencia.setInt(1, identificador);
                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        return new boolean[]{resultado.getBoolean("visionnocturna"), resultado.getBoolean("sonido")};
                    }
                }
            }
            try (PreparedStatement sentencia = conexionBD.prepareStatement("INSERT IGNORE INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?)")) {
                sentencia.setInt(1, identificador);
                sentencia.setBoolean(2, false);
                sentencia.setBoolean(3, true);
                sentencia.executeUpdate();
            }
            return new boolean[]{false, true};
        }
    }

    /**
     * guarda la configuracion del usuario.
     *
     * @param vision modo oscuro
     * @param sonido sonido activo
     * @throws Exception si falla
     */
    public void guardarConfiguracion(boolean vision, boolean sonido) throws Exception {
        try (Connection conexionBD = conexion.getConnection()) {
            String consulta = "INSERT INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE visionnocturna = VALUES(visionnocturna), sonido = VALUES(sonido)";
            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {
                sentencia.setInt(1, identificador);
                sentencia.setBoolean(2, vision);
                sentencia.setBoolean(3, sonido);
                sentencia.executeUpdate();
            }
        }
    }

    /**
     * canjea un codigo de recompensa.
     *
     * @param codigo codigo a canjear
     * @return arreglo con [monedas, sobres] o null si no es valido
     * @throws Exception si falla
     */
    public int[] canjearCodigo(String codigo) throws Exception {
        try (Connection conexionBD = conexion.getConnection()) {
            conexionBD.setAutoCommit(false);
            try {
                int monedasRecompensa = 0;
                int sobresRecompensa = 0;

                try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT recompensamonedas, recompensasobres FROM codigo WHERE codigo = ?")) {
                    sentencia.setString(1, codigo);
                    try (ResultSet resultado = sentencia.executeQuery()) {
                        if (!resultado.next()) {
                            conexionBD.rollback();
                            return null;
                        }
                        monedasRecompensa = resultado.getInt("recompensamonedas");
                        sobresRecompensa = resultado.getInt("recompensasobres");
                    }
                }

                try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT 1 FROM canje WHERE id_usuario = ? AND codigo = ?")) {
                    sentencia.setInt(1, identificador);
                    sentencia.setString(2, codigo);
                    try (ResultSet resultado = sentencia.executeQuery()) {
                        if (resultado.next()) {
                            conexionBD.rollback();
                            return null;
                        }
                    }
                }

                try (PreparedStatement sentencia = conexionBD.prepareStatement("INSERT INTO canje (id_usuario, codigo, fecha) VALUES (?, ?, NOW())")) {
                    sentencia.setInt(1, identificador);
                    sentencia.setString(2, codigo);
                    sentencia.executeUpdate();
                }

                monedas += monedasRecompensa;
                try (PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
                    sentencia.setInt(1, monedas);
                    sentencia.setInt(2, identificador);
                    sentencia.executeUpdate();
                }

                conexionBD.commit();
                return new int[]{monedasRecompensa, sobresRecompensa};
            } catch (SQLException excepcion) {
                conexionBD.rollback();
                throw excepcion;
            }
        }
    }

    /**
     * devuelve los sobres gratis del usuario.
     *
     * @return cantidad de sobres gratis
     * @throws Exception si falla
     */
    public int getSobresGratis() throws Exception {
        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement("SELECT sobres_gratis FROM usuario WHERE id = ?")) {
            sentencia.setInt(1, identificador);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("sobres_gratis");
                }
            }
        }
        return 0;
    }

    /**
     * actualiza los sobres gratis del usuario.
     *
     * @param cantidad nueva cantidad
     * @throws Exception si falla
     */
    public void actualizarSobresGratis(int cantidad) throws Exception {
        try (Connection conexionBD = conexion.getConnection();
             PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE usuario SET sobres_gratis = ? WHERE id = ?")) {
            sentencia.setInt(1, cantidad);
            sentencia.setInt(2, identificador);
            sentencia.executeUpdate();
        }
    }

    /**
     * inicia la sesion por tiempo y otorga sobres pendientes.
     *
     * @throws Exception si falla
     */
    public void iniciarSesionTiempo() throws Exception {
        try (Connection conexionBD = conexion.getConnection()) {
            conexionBD.setAutoCommit(false);
            try {
                try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT id, ultima_recompensa FROM sesion WHERE id_usuario = ? AND fecha_salida IS NULL");
                     ) {
                    sentencia.setInt(1, identificador);
                    try (ResultSet resultado = sentencia.executeQuery()) {
                        while (resultado.next()) {
                            int idSesion = resultado.getInt("id");
                            Timestamp ultima = resultado.getTimestamp("ultima_recompensa");
                            otorgarSobresPorTiempo(conexionBD, idSesion, ultima);
                            try (PreparedStatement sentencia2 = conexionBD.prepareStatement("UPDATE sesion SET fecha_salida = NOW() WHERE id = ?")) {
                                sentencia2.setInt(1, idSesion);
                                sentencia2.executeUpdate();
                            }
                        }
                    }
                }

                int nuevoId = siguienteId(conexionBD, "sesion");
                try (PreparedStatement sentencia = conexionBD.prepareStatement("INSERT INTO sesion (id, id_usuario, fecha_entrada, fecha_salida, ultima_recompensa) VALUES (?, ?, NOW(), NULL, NOW())")) {
                    sentencia.setInt(1, nuevoId);
                    sentencia.setInt(2, identificador);
                    sentencia.executeUpdate();
                }
                idSesionActual = nuevoId;
                conexionBD.commit();
            } catch (SQLException excepcion) {
                conexionBD.rollback();
                throw excepcion;
            }
        }
    }

    /**
     * otorga sobres por tiempo transcurrido.
     *
     * @param conexionBD conexion activa
     * @param idSesion id de sesion
     * @param ultima ultima recompensa
     * @throws SQLException si falla
     */
    private void otorgarSobresPorTiempo(Connection conexionBD, int idSesion, Timestamp ultima) throws SQLException {
        if (ultima == null) {
            return;
        }
        long diferenciaMs = System.currentTimeMillis() - ultima.getTime();
        int bloques = (int) (diferenciaMs / (15L * 60L * 1000L));
        if (bloques <= 0) {
            return;
        }
        int nuevos = bloques * 3;
        try (PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE usuario SET sobres_gratis = sobres_gratis + ? WHERE id = ?")) {
            sentencia.setInt(1, nuevos);
            sentencia.setInt(2, identificador);
            sentencia.executeUpdate();
        }
        Timestamp nuevaUltima = new Timestamp(ultima.getTime() + bloques * 15L * 60L * 1000L);
        try (PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE sesion SET ultima_recompensa = ? WHERE id = ?")) {
            sentencia.setTimestamp(1, nuevaUltima);
            sentencia.setInt(2, idSesion);
            sentencia.executeUpdate();
        }
    }

    /**
     * reclama los sobres por tiempo actuales.
     *
     * @return cantidad de sobres reclamados
     * @throws Exception si falla
     */
    public int reclamarSobresActuales() throws Exception {
        if (idSesionActual <= 0) {
            return 0;
        }
        try (Connection conexionBD = conexion.getConnection()) {
            Timestamp ultima;
            try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT ultima_recompensa FROM sesion WHERE id = ?")) {
                sentencia.setInt(1, idSesionActual);
                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (!resultado.next()) {
                        return 0;
                    }
                    ultima = resultado.getTimestamp("ultima_recompensa");
                }
            }
            long diferenciaMs = System.currentTimeMillis() - ultima.getTime();
            int bloques = (int) (diferenciaMs / (15L * 60L * 1000L));
            if (bloques <= 0) {
                return 0;
            }
            try {
                conexionBD.setAutoCommit(false);
                otorgarSobresPorTiempo(conexionBD, idSesionActual, ultima);
                conexionBD.commit();
            } catch (SQLException excepcion) {
                conexionBD.rollback();
                throw excepcion;
            }
            return bloques * 3;
        }
    }

    /**
     * cierra la sesion por tiempo.
     *
     * @throws Exception si falla
     */
    public void cerrarSesionTiempo() throws Exception {
        if (idSesionActual <= 0) {
            return;
        }
        try (Connection conexionBD = conexion.getConnection()) {
            try (PreparedStatement sentencia = conexionBD.prepareStatement("SELECT ultima_recompensa FROM sesion WHERE id = ?")) {
                sentencia.setInt(1, idSesionActual);
                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (resultado.next()) {
                        Timestamp ultima = resultado.getTimestamp("ultima_recompensa");
                        otorgarSobresPorTiempo(conexionBD, idSesionActual, ultima);
                    }
                }
            }
            try (PreparedStatement sentencia = conexionBD.prepareStatement("UPDATE sesion SET fecha_salida = NOW() WHERE id = ?")) {
                sentencia.setInt(1, idSesionActual);
                sentencia.executeUpdate();
            }
        }
        idSesionActual = -1;
    }
}