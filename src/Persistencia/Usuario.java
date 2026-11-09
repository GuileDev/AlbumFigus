
package Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;

public class Usuario {

    private Conexion conexion;
    private String username;

    public Usuario() throws Exception {

        try (Connection con = conexion.getConnection()) {

            String crear = "CREATE DATABASE IF NOT EXISTS album; USE album; CREATE TABLE usuario ( id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR(20) NOT NULL, monedas INT NOT NULL); CREATE TABLE seleccion (id INT PRIMARY KEY, nombre VARCHAR(50) NOT NULL, grupo CHAR(1) NOT NULL); CREATE TABLE figurita (id_figurita INT PRIMARY KEY, numero INT NOT NULL, jugador VARCHAR(100) NOT NULL, posicion VARCHAR(30) NOT NULL, rareza VARCHAR(20) NOT NULL, id_seleccion INT NOT NULL, FOREIGN KEY (id_seleccion) REFERENCES seleccion(id)); CREATE TABLE almacenamiento (id_usuario INT NOT NULL, id_figurita INT NOT NULL, cantidad INT NOT NULL DEFAULT 0, PRIMARY KEY (id_usuario, id_figurita), FOREIGN KEY (id_usuario) REFERENCES usuario(id), FOREIGN KEY (id_figurita) REFERENCES figurita(id));";

            try (PreparedStatement ps = con.prepareStatement(crear)) {
                ps.executeUpdate();
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
            // Guardar monedas
            try (PreparedStatement ps = con.prepareStatement(sqlMonedas)) {
                ps.setInt(1, monedas);
                ps.setString(2, username);
                ps.executeUpdate();
            }

            // Guardar figuritas
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
} // Obtener usuario actual
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

    // Obtener monedas actuales
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
