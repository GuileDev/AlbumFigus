package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Clase principal para probar la conexión y verificar el esquema.
 */
public class ConnSimple {

    public static void main(String[] args) {
        Conexion conexion = Conexion.getInstancia();

        System.out.println("Conectando a: " + conexion.getUrl());
        System.out.println("Usuario: " + conexion.getUsuario());

        try (Connection con = conexion.getConnection()) {
            if (!con.isValid(3)) {
                System.out.println("La conexión no es válida.");
                return;
            }

      

         

            // try-with-resources cierra la conexión automáticamente
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
