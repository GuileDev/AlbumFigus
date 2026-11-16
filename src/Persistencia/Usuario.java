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

public class Usuario {

    private Conexion conexion = Conexion.getInstancia();
    private int id;
    private String username;
    private int monedas;
    private int sesionActualId = -1;

    public Usuario() throws Exception {

        // Crear la base de datos y las tablas si no existen
        String serverUrl = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true";

        try (Connection con = DriverManager.getConnection(serverUrl, "root", "1234")) {

            con.createStatement().execute("CREATE DATABASE IF NOT EXISTS album");
            con.createStatement().execute("USE album");

            String crearUsuario = "CREATE TABLE IF NOT EXISTS usuario (id INT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, monedas INT NOT NULL, sobres_gratis INT NOT NULL DEFAULT 0)";
            String crearSeleccion = "CREATE TABLE IF NOT EXISTS seleccion (id INT PRIMARY KEY, nombre VARCHAR(50) NOT NULL, grupo CHAR(1) NOT NULL)";
            String crearFigurita = "CREATE TABLE IF NOT EXISTS figurita (id INT PRIMARY KEY, numero INT NOT NULL, jugador VARCHAR(100) NOT NULL, posicion VARCHAR(30) NOT NULL, rareza VARCHAR(20) NOT NULL, id_seleccion INT NOT NULL, FOREIGN KEY (id_seleccion) REFERENCES seleccion(id))";
            String crearAlmacenamiento = "CREATE TABLE IF NOT EXISTS almacenamiento (id_usuario INT NOT NULL, id_figurita INT NOT NULL, cantidad INT NOT NULL DEFAULT 0, PRIMARY KEY (id_usuario, id_figurita), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (id_figurita) REFERENCES figurita(id))";
            String crearCodigo = "CREATE TABLE IF NOT EXISTS codigo (codigo VARCHAR(50) PRIMARY KEY, recompensamonedas INT NOT NULL, recompensasobres INT NOT NULL)";
            String crearCanje = "CREATE TABLE IF NOT EXISTS canje (id_usuario INT NOT NULL, codigo VARCHAR(20) NOT NULL, fecha DATETIME NOT NULL, PRIMARY KEY (id_usuario, codigo), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (codigo) REFERENCES codigo(codigo))";
            String crearConfiguracion = "CREATE TABLE IF NOT EXISTS configuracion (id_usuario INT PRIMARY KEY, visionnocturna BOOLEAN NOT NULL, sonido BOOLEAN NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";
            String crearRevivir = "CREATE TABLE IF NOT EXISTS revivir (id INT PRIMARY KEY, id_usuario INT NOT NULL, consigna VARCHAR(100) NOT NULL, multiplicador INT NOT NULL, completado BOOLEAN NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";
            String crearSesion = "CREATE TABLE IF NOT EXISTS sesion (id INT PRIMARY KEY, id_usuario INT NOT NULL, fecha_entrada DATETIME NOT NULL, fecha_salida DATETIME, ultima_recompensa DATETIME NOT NULL, FOREIGN KEY (id_usuario) REFERENCES usuario(id))";

            con.createStatement().execute(crearUsuario);
            con.createStatement().execute(crearSeleccion);
            con.createStatement().execute(crearFigurita);
            con.createStatement().execute(crearAlmacenamiento);
            con.createStatement().execute(crearCodigo);
            con.createStatement().execute(crearCanje);
            con.createStatement().execute(crearConfiguracion);
            con.createStatement().execute(crearRevivir);
            con.createStatement().execute(crearSesion);
            try {
                con.createStatement().execute("ALTER TABLE usuario ADD COLUMN sobres_gratis INT NOT NULL DEFAULT 0");
            } catch (SQLException ignore) {
            }

            String seedSeleccion = "INSERT IGNORE INTO seleccion (id, nombre, grupo) VALUES (1, 'Uruguay', 'A'), (2, 'Brasil', 'B'), (3, 'Argentina', 'C'), (4, 'Francia', 'D'), (5, 'Belgica', 'E'), (6, 'Croacia', 'F'), (7, 'Inglaterra', 'G'), (8, 'Canada', 'H'), (9, 'Portugal', 'I'), (10, 'Espana', 'J'), (11, 'Paises Bajos', 'K')";
            con.createStatement().execute(seedSeleccion);

            String seedFigurita = "INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (?, ?, ?, ?, ?, ?)";
            String[][] catalogo = {
                    {"1", "1", "Sergio Rochet", "Portero", "Común", "1"},
                    {"2", "2", "Sebastian Caceres", "Defensa", "Común", "1"},
                    {"3", "3", "Ronald Araujo", "Defensa", "Común", "1"},
                    {"4", "4", "Jose Maria Gimenez", "Defensa", "Común", "1"},
                    {"5", "5", "Manuel Ugarte", "Mediocampista", "Común", "1"},
                    {"6", "6", "Federico Valverde", "Mediocampista", "Común", "1"},
                    {"7", "7", "Rodrigo Bentancur", "Mediocampista", "Común", "1"},
                    {"8", "8", "Nahitan Nandez", "Mediocampista", "Común", "1"},
                    {"9", "9", "Maximiliano Araujo", "Delantero", "Común", "1"},
                    {"10", "10", "Luis Suarez", "Delantero", "Común", "1"},
                    {"11", "11", "Darwin Nunez", "Delantero", "Común", "1"},
                    {"12", "12", "Vinicius Junior", "Delantero", "Estrella", "2"},
                    {"13", "13", "Lionel Messi", "Delantero", "Estrella", "3"},
                    {"14", "14", "Kylian Mbappe", "Delantero", "Estrella", "4"},
                    {"15", "15", "Romelu Lukaku", "Delantero", "Estrella", "5"},
                    {"16", "16", "Neymar Jr.", "Delantero", "Estrella", "2"},
                    {"17", "17", "Luka Modric", "Mediocampista", "Estrella", "6"},
                    {"18", "18", "Angel Di Maria", "Delantero", "Estrella", "3"},
                    {"19", "19", "Harry Kane", "Delantero", "Estrella", "7"},
                    {"20", "20", "Jude Bellingham", "Mediocampista", "Estrella", "7"},
                    {"21", "21", "Alphonso Davies", "Defensa", "Estrella", "8"},
                    {"22", "22", "Cristiano Ronaldo", "Delantero", "Estrella", "9"},
                    {"23", "23", "Bruno Fernandes", "Mediocampista", "Estrella", "9"},
                    {"24", "24", "Federico Valverde", "Mediocampista", "Estrella", "1"},
                    {"25", "25", "Paul Pogba", "Mediocampista", "Estrella", "4"},
                    {"26", "26", "Enzo Fernandez", "Mediocampista", "Estrella", "3"},
                    {"27", "27", "Lamine Yamal", "Delantero", "Especial", "10"},
                    {"28", "28", "Antony Robinson", "Defensa", "Especial", "7"},
                    {"29", "29", "Darwin Nunez", "Delantero", "Especial", "1"},
                    {"30", "30", "Virgil van Dijk", "Defensa", "Especial", "11"},
                    {"31", "31", "Federico Valverde", "Mediocampista", "Especial", "1"},
                    {"32", "32", "Lautaro Martinez", "Delantero", "Especial", "3"}
            };

            try (PreparedStatement ps = con.prepareStatement(seedFigurita)) {
                for (String[] f : catalogo) {
                    ps.setInt(1, Integer.parseInt(f[0]));
                    ps.setInt(2, Integer.parseInt(f[1]));
                    ps.setString(3, f[2]);
                    ps.setString(4, f[3]);
                    ps.setString(5, f[4]);
                    ps.setInt(6, Integer.parseInt(f[5]));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String seedCodigo = "INSERT IGNORE INTO codigo (codigo, recompensamonedas, recompensasobres) VALUES ('ITI2026', 500, 1), ('MUNDIAL', 1000, 2), ('FIGUS', 200, 0)";
            con.createStatement().execute(seedCodigo);

        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    private int nextId(Connection con, String tabla) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) FROM " + tabla);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int max = rs.getInt(1);
                if (rs.wasNull()) {
                    return 1;
                }
                return max + 1;
            }
        }
        return 1;
    }

    public boolean IniciarSesion(String nombre, String pwd) throws Exception {
        try (Connection con = conexion.getConnection()) {
            String sql = "SELECT id, monedas FROM usuario WHERE username = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombre);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("id");
                        username = nombre;
                        monedas = rs.getInt("monedas");
                        return true;
                    }
                }
            }

            int newId = nextId(con, "usuario");
            String ins = "INSERT INTO usuario (id, username, monedas) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(ins)) {
                ps.setInt(1, newId);
                ps.setString(2, nombre);
                ps.setInt(3, 1000);
                ps.executeUpdate();
            }
            id = newId;
            username = nombre;
            monedas = 1000;

            try (PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?)")) {
                ps.setInt(1, newId);
                ps.setBoolean(2, false);
                ps.setBoolean(3, true);
                ps.executeUpdate();
            }

            return true;
        }
    }

    public void GuardarProgreso(int monedasNuevas, ArrayList<Integer> figuritas) throws Exception {
        this.monedas = monedasNuevas;

        try (Connection con = conexion.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
                    ps.setInt(1, monedasNuevas);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }

                if (figuritas != null && !figuritas.isEmpty()) {
                    String sql = "INSERT INTO almacenamiento (id_usuario, id_figurita, cantidad) VALUES (?, ?, 1) "
                            + "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";
                    try (PreparedStatement ps = con.prepareStatement(sql)) {
                        for (int fid : figuritas) {
                            ps.setInt(1, id);
                            ps.setInt(2, fid);
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar progreso: " + e.getMessage());
        }
    }

    public String getUsuarioActual() throws Exception {
        return username;
    }

    public ArrayList<Figurita> getFiguritasCatalogo() throws Exception {
        String sql = "SELECT f.id, f.numero, f.jugador, f.posicion, f.rareza, s.id AS sel_id, s.nombre AS sel_nombre, s.grupo AS sel_grupo "
                + "FROM figurita f JOIN seleccion s ON s.id = f.id_seleccion ORDER BY f.numero";

        ArrayList<Figurita> lista = new ArrayList<>();

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seleccion sel = new Seleccion(rs.getInt("sel_id"), rs.getString("sel_nombre"), rs.getString("sel_grupo").charAt(0));
                Figurita fig = new Figurita(rs.getInt("id"), rs.getInt("numero"), rs.getString("jugador"), rs.getString("posicion"), rs.getString("rareza"), sel);
                lista.add(fig);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el catalogo de figuritas: " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<Integer> getFiguritasActuales() throws Exception {
        String sql = "SELECT id_figurita, cantidad FROM almacenamiento WHERE id_usuario = ?";

        ArrayList<Integer> lista = new ArrayList<>();

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int fid = rs.getInt("id_figurita");
                    int cantidad = rs.getInt("cantidad");
                    for (int i = 0; i < cantidad; i++) {
                        lista.add(fid);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las figuritas del usuario: " + e.getMessage());
        }

        return lista;
    }

    public int getMonedasActuales() throws Exception {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT monedas FROM usuario WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    monedas = rs.getInt("monedas");
                    return monedas;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener monedas: " + e.getMessage());
        }
        return monedas;
    }

    public void actualizarMonedas(int nuevas) throws Exception {
        monedas = nuevas;
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
            ps.setInt(1, nuevas);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public boolean[] cargarConfiguracion() throws Exception {
        try (Connection con = conexion.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT visionnocturna, sonido FROM configuracion WHERE id_usuario = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new boolean[]{rs.getBoolean("visionnocturna"), rs.getBoolean("sonido")};
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?)")) {
                ps.setInt(1, id);
                ps.setBoolean(2, false);
                ps.setBoolean(3, true);
                ps.executeUpdate();
            }
            return new boolean[]{false, true};
        }
    }

    public void guardarConfiguracion(boolean vision, boolean sonido) throws Exception {
        try (Connection con = conexion.getConnection()) {
            String sql = "INSERT INTO configuracion (id_usuario, visionnocturna, sonido) VALUES (?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE visionnocturna = VALUES(visionnocturna), sonido = VALUES(sonido)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setBoolean(2, vision);
                ps.setBoolean(3, sonido);
                ps.executeUpdate();
            }
        }
    }

    public int[] canjearCodigo(String codigo) throws Exception {
        try (Connection con = conexion.getConnection()) {
            con.setAutoCommit(false);
            try {
                int monedasReward = 0;
                int sobresReward = 0;

                try (PreparedStatement ps = con.prepareStatement("SELECT recompensamonedas, recompensasobres FROM codigo WHERE codigo = ?")) {
                    ps.setString(1, codigo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return null;
                        }
                        monedasReward = rs.getInt("recompensamonedas");
                        sobresReward = rs.getInt("recompensasobres");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM canje WHERE id_usuario = ? AND codigo = ?")) {
                    ps.setInt(1, id);
                    ps.setString(2, codigo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            return null;
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement("INSERT INTO canje (id_usuario, codigo, fecha) VALUES (?, ?, NOW())")) {
                    ps.setInt(1, id);
                    ps.setString(2, codigo);
                    ps.executeUpdate();
                }

                monedas += monedasReward;
                try (PreparedStatement ps = con.prepareStatement("UPDATE usuario SET monedas = ? WHERE id = ?")) {
                    ps.setInt(1, monedas);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }

                con.commit();
                return new int[]{monedasReward, sobresReward};
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public int getSobresGratis() throws Exception {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT sobres_gratis FROM usuario WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("sobres_gratis");
                }
            }
        }
        return 0;
    }

    public void actualizarSobresGratis(int cantidad) throws Exception {
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE usuario SET sobres_gratis = ? WHERE id = ?")) {
            ps.setInt(1, cantidad);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void iniciarSesionTiempo() throws Exception {
        try (Connection con = conexion.getConnection()) {
            con.setAutoCommit(false);
            try {
                // Cerrar sesiones abiertas anteriores otorgando lo que corresponda
                try (PreparedStatement ps = con.prepareStatement("SELECT id, ultima_recompensa FROM sesion WHERE id_usuario = ? AND fecha_salida IS NULL");
                     ) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int sid = rs.getInt("id");
                            Timestamp ultima = rs.getTimestamp("ultima_recompensa");
                            otorgarSobresPorTiempo(con, sid, ultima);
                            try (PreparedStatement ps2 = con.prepareStatement("UPDATE sesion SET fecha_salida = NOW() WHERE id = ?")) {
                                ps2.setInt(1, sid);
                                ps2.executeUpdate();
                            }
                        }
                    }
                }

                int newId = nextId(con, "sesion");
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO sesion (id, id_usuario, fecha_entrada, fecha_salida, ultima_recompensa) VALUES (?, ?, NOW(), NULL, NOW())")) {
                    ps.setInt(1, newId);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                sesionActualId = newId;
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    private void otorgarSobresPorTiempo(Connection con, int sid, Timestamp ultima) throws SQLException {
        if (ultima == null) {
            return;
        }
        long diffMs = System.currentTimeMillis() - ultima.getTime();
        int bloques = (int) (diffMs / (15L * 60L * 1000L));
        if (bloques <= 0) {
            return;
        }
        int nuevos = bloques * 3;
        try (PreparedStatement ps = con.prepareStatement("UPDATE usuario SET sobres_gratis = sobres_gratis + ? WHERE id = ?")) {
            ps.setInt(1, nuevos);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        Timestamp nuevaUltima = new Timestamp(ultima.getTime() + bloques * 15L * 60L * 1000L);
        try (PreparedStatement ps = con.prepareStatement("UPDATE sesion SET ultima_recompensa = ? WHERE id = ?")) {
            ps.setTimestamp(1, nuevaUltima);
            ps.setInt(2, sid);
            ps.executeUpdate();
        }
    }

    public int reclamarSobresActuales() throws Exception {
        if (sesionActualId <= 0) {
            return 0;
        }
        try (Connection con = conexion.getConnection()) {
            Timestamp ultima;
            try (PreparedStatement ps = con.prepareStatement("SELECT ultima_recompensa FROM sesion WHERE id = ?")) {
                ps.setInt(1, sesionActualId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return 0;
                    }
                    ultima = rs.getTimestamp("ultima_recompensa");
                }
            }
            long diffMs = System.currentTimeMillis() - ultima.getTime();
            int bloques = (int) (diffMs / (15L * 60L * 1000L));
            if (bloques <= 0) {
                return 0;
            }
            try {
                con.setAutoCommit(false);
                otorgarSobresPorTiempo(con, sesionActualId, ultima);
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
            return bloques * 3;
        }
    }

    public void cerrarSesionTiempo() throws Exception {
        if (sesionActualId <= 0) {
            return;
        }
        try (Connection con = conexion.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT ultima_recompensa FROM sesion WHERE id = ?")) {
                ps.setInt(1, sesionActualId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Timestamp ultima = rs.getTimestamp("ultima_recompensa");
                        otorgarSobresPorTiempo(con, sesionActualId, ultima);
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement("UPDATE sesion SET fecha_salida = NOW() WHERE id = ?")) {
                ps.setInt(1, sesionActualId);
                ps.executeUpdate();
            }
        }
        sesionActualId = -1;
    }
}
