
package Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.ArrayList;
import logic.Figurita;
import logic.Seleccion;
public class Usuario {

    private Conexion conexion = Conexion.getInstancia();
    private String username;

    public Usuario() throws Exception {

        // Crear la base de datos y las tablas si no existen
        String crearBd = "CREATE DATABASE IF NOT EXISTS album";
        String crearUsuario = "CREATE TABLE IF NOT EXISTS usuario ( id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(20) NOT NULL, monedas INT NOT NULL)";
        String crearSeleccion = "CREATE TABLE IF NOT EXISTS seleccion (id INT PRIMARY KEY, nombre VARCHAR(50) NOT NULL, grupo CHAR(1) NOT NULL)";
        String crearFigurita = "CREATE TABLE IF NOT EXISTS figurita (id INT PRIMARY KEY, numero INT NOT NULL, jugador VARCHAR(100) NOT NULL, posicion VARCHAR(30) NOT NULL, rareza VARCHAR(20) NOT NULL, id_seleccion INT NOT NULL, FOREIGN KEY (id_seleccion) REFERENCES seleccion(id))";
        String crearAlmacenamiento = "CREATE TABLE IF NOT EXISTS almacenamiento (id_usuario INT NOT NULL, id_figurita INT NOT NULL, cantidad INT NOT NULL DEFAULT 0, PRIMARY KEY (id_usuario, id_figurita), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (id_figurita) REFERENCES figurita(id))";
        String seedSeleccion = "INSERT IGNORE INTO seleccion (id, nombre, grupo) VALUES (1, 'Uruguay', 'A'), (2, 'Brasil', 'B'), (3, 'Argentina', 'C'), (4, 'Francia', 'D'), (5, 'Bélgica', 'E'), (6, 'Croacia', 'F'), (7, 'Inglaterra', 'G'), (8, 'Canadá', 'H'), (9, 'Portugal', 'I'), (10, 'España', 'J'), (11, 'Países Bajos', 'K')";
        String seedFigurita = "INSERT IGNORE INTO figurita (id, numero, jugador, posicion, rareza, id_seleccion) VALUES (?, ?, ?, ?, ?, ?)";
        // Catalogo de figuritas: 11 Comun / 15 Estrella / 6 Especial
        String[][] catalogo = {
            {"1", "1", "Sergio Rochet", "Portero", "Común", "1"},
            {"2", "2", "Sebastián Cáceres", "Defensa", "Común", "1"},
            {"3", "3", "Ronald Araujo", "Defensa", "Común", "1"},
            {"4", "4", "José María Giménez", "Defensa", "Común", "1"},
            {"5", "5", "Manuel Ugarte", "Mediocampista", "Común", "1"},
            {"6", "6", "Federico Valverde", "Mediocampista", "Común", "1"},
            {"7", "7", "Rodrigo Bentancur", "Mediocampista", "Común", "1"},
            {"8", "8", "Nahitan Nández", "Mediocampista", "Común", "1"},
            {"9", "9", "Maximiliano Araújo", "Delantero", "Común", "1"},
            {"10", "10", "Luis Suárez", "Delantero", "Común", "1"},
            {"11", "11", "Darwin Núñez", "Delantero", "Común", "1"},
            {"12", "12", "Vinícius Júnior", "Delantero", "Estrella", "2"},
            {"13", "13", "Lionel Messi", "Delantero", "Estrella", "3"},
            {"14", "14", "Kylian Mbappé", "Delantero", "Estrella", "4"},
            {"15", "15", "Romelu Lukaku", "Delantero", "Estrella", "5"},
            {"16", "16", "Neymar Jr.", "Delantero", "Estrella", "2"},
            {"17", "17", "Luka Modrić", "Mediocampista", "Estrella", "6"},
            {"18", "18", "Ángel Di María", "Delantero", "Estrella", "3"},
            {"19", "19", "Harry Kane", "Delantero", "Estrella", "7"},
            {"20", "20", "Jude Bellingham", "Mediocampista", "Estrella", "7"},
            {"21", "21", "Alphonso Davies", "Defensa", "Estrella", "8"},
            {"22", "22", "Cristiano Ronaldo", "Delantero", "Estrella", "9"},
            {"23", "23", "Bruno Fernandes", "Mediocampista", "Estrella", "9"},
            {"24", "24", "Federico Valverde", "Mediocampista", "Estrella", "1"},
            {"25", "25", "Paul Pogba", "Mediocampista", "Estrella", "4"},
            {"26", "26", "Enzo Fernández", "Mediocampista", "Estrella", "3"},
            {"27", "27", "Lamine Yamal", "Delantero", "Especial", "10"},
            {"28", "28", "Antony Robinson", "Defensa", "Especial", "7"},
            {"29", "29", "Darwin Núñez", "Delantero", "Especial", "1"},
            {"30", "30", "Virgil van Dijk", "Defensa", "Especial", "11"},
            {"31", "31", "Federico Valverde", "Mediocampista", "Especial", "1"},
            {"32", "32", "Lautaro Martínez", "Delantero", "Especial", "3"}
        };
        String usar = "USE album";

        try (Connection con = conexion.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(crearBd)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(usar)) {
                ps.execute();
            }

            try (PreparedStatement ps = con.prepareStatement(crearUsuario)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(crearSeleccion)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(crearFigurita)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(crearAlmacenamiento)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(seedSeleccion)) {
                ps.executeUpdate();
            }

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

        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: " + e.getMessage());
        }
    }

    // Crear usuario
    public void CrearUsuarioActual(String nombre, String pwd, int monedas) throws Exception {

        String sql = "INSERT INTO usuario (username, password, monedas) VALUES (?, ?, ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, pwd);
            ps.setInt(3, monedas);

            ps.executeUpdate();

            this.username = nombre;

        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }
public boolean IniciarSesion(String nombre, String pwd) throws Exception {

    String sql = "SELECT password FROM usuario WHERE username = ?";

    try (Connection con = conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombre);

        try (ResultSet rs = ps.executeQuery()) {

            // El usuario ya existe
            if (rs.next()) {

                // Comprobar contraseña
                if (rs.getString("password").equals(pwd)) {
                    this.username = nombre;
                    return true;
                }

                return false;
            }
        }

        // El usuario no existe: crearlo
        CrearUsuarioActual(nombre, pwd, 1000);

        return true;
    }
}
public void GuardarProgreso(int monedas, ArrayList<Integer> figuritas) throws Exception {

    String sqlMonedas = "UPDATE usuario SET monedas = ? WHERE username = ?";

    String sqlFiguritas = "INSERT INTO almacenamiento (id_usuario, id_figurita, cantidad) "
            + "SELECT id, ?, 1 FROM usuario WHERE username = ? "
            + "ON DUPLICATE KEY UPDATE cantidad = cantidad + 1";

    try (Connection con = conexion.getConnection()) {

        con.setAutoCommit(false);

        try {
            
            try (PreparedStatement ps = con.prepareStatement(sqlMonedas)) {
                ps.setInt(1, monedas);
                ps.setString(2, username);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlFiguritas)) {

                for (int id : figuritas) {
                    ps.setInt(1, id);
                    ps.setString(2, username);
                    ps.addBatch();
                }

                ps.executeBatch();
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

        String sql = "SELECT username FROM usuario WHERE username = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("username");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }

        return null;
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

        String sql = "SELECT id_figurita, cantidad FROM almacenamiento WHERE id_usuario = (SELECT id FROM usuario WHERE username = ?)";

        ArrayList<Integer> lista = new ArrayList<>();

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id_figurita");
                    int cantidad = rs.getInt("cantidad");

                    for (int i = 0; i < cantidad; i++) {
                        lista.add(id);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las figuritas del usuario: " + e.getMessage());
        }

        return lista;
    }

  
    public int getMonedasActuales() throws Exception {

        String sql = "SELECT monedas FROM usuario WHERE username = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("monedas");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener monedas: " + e.getMessage());
        }

        return 0;
    }
}
