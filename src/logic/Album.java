package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Album {
    
    private Usuario usuarioActual;
    private ArrayList<Figurita> figuritasDisponibles;
    private Random random;
    private Conexion conexion;
    
    public Album(Usuario usuario) {
        this.usuarioActual = usuario;
        this.figuritasDisponibles = new ArrayList<>();
        this.random = new Random();
        this.conexion = Conexion.getInstancia();
        cargarFiguritas();
    }
    
    private void cargarFiguritas() {
        String sql = "SELECT f.id_figurita, f.numero, f.jugador, f.posicion, f.rareza, " +
                     "s.id_seleccion, s.nombre, s.grupo " +
                     "FROM figurita f " +
                     "INNER JOIN seleccion s ON f.id_seleccion = s.id_seleccion " +
                     "ORDER BY f.numero";
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            figuritasDisponibles.clear();
            
            while (rs.next()) {
                Seleccion seleccion = new Seleccion(
                    rs.getInt("id_seleccion"),
                    rs.getString("nombre"),
                    rs.getString("grupo").charAt(0)
                );
                
                Figurita figurita = new Figurita(
                    rs.getInt("id_figurita"),
                    rs.getInt("numero"),
                    rs.getString("jugador"),
                    rs.getString("posicion"),
                    rs.getString("rareza"),
                    seleccion
                );
                
                figuritasDisponibles.add(figurita);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al cargar figuritas: " + e.getMessage());
        }
    }
    
    public Figurita obtenerFigurita() {
        if (figuritasDisponibles.isEmpty()) {
            return null;
        }
        int indice = random.nextInt(figuritasDisponibles.size());
        Figurita figuritaObtenida = figuritasDisponibles.get(indice);
        agregarAlAlmacenamiento(figuritaObtenida);
        return figuritaObtenida;
    }
    
    public Figurita obtenerFiguritaPorNumero(int numero) {
        for (Figurita f : figuritasDisponibles) {
            if (f.getNumero() == numero) {
                agregarAlAlmacenamiento(f);
                return f;
            }
        }
        return null;
    }
    
    private void agregarAlAlmacenamiento(Figurita figurita) {
        String sqlBuscar = "SELECT cantidad FROM almacenamiento WHERE id_usuario = ? AND id_figurita = ?";
        String sqlInsertar = "INSERT INTO almacenamiento (id_usuario, id_figurita, cantidad) VALUES (?, ?, 1)";
        String sqlActualizar = "UPDATE almacenamiento SET cantidad = ? WHERE id_usuario = ? AND id_figurita = ?";
        
        try (Connection con = conexion.getConnection()) {
            PreparedStatement psBuscar = con.prepareStatement(sqlBuscar);
            psBuscar.setInt(1, usuarioActual.getIdUsuario());
            psBuscar.setInt(2, figurita.getIdFigurita());
            ResultSet rs = psBuscar.executeQuery();
            
            if (rs.next()) {
                int cantidadActual = rs.getInt("cantidad");
                int cantidadNueva = cantidadActual + 1;
                
                PreparedStatement psActualizar = con.prepareStatement(sqlActualizar);
                psActualizar.setInt(1, cantidadNueva);
                psActualizar.setInt(2, usuarioActual.getIdUsuario());
                psActualizar.setInt(3, figurita.getIdFigurita());
                psActualizar.executeUpdate();
                psActualizar.close();
            } else {
                PreparedStatement psInsertar = con.prepareStatement(sqlInsertar);
                psInsertar.setInt(1, usuarioActual.getIdUsuario());
                psInsertar.setInt(2, figurita.getIdFigurita());
                psInsertar.executeUpdate();
                psInsertar.close();
            }
            
            rs.close();
            psBuscar.close();
            
        } catch (SQLException e) {
            System.err.println("Error al agregar figurita al almacenamiento: " + e.getMessage());
        }
    }
    
    public int getCantidadFigurita(Figurita figurita) {
        String sql = "SELECT cantidad FROM almacenamiento WHERE id_usuario = ? AND id_figurita = ?";
        int cantidad = 0;
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioActual.getIdUsuario());
            ps.setInt(2, figurita.getIdFigurita());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad de figurita: " + e.getMessage());
        }
        
        return cantidad;
    }
    
    public int getCantidadFiguritaPorNumero(int numero) {
        Figurita figurita = buscarFiguritaPorNumero(numero);
        if (figurita == null) {
            return 0;
        }
        return getCantidadFigurita(figurita);
    }
    
    public Figurita buscarFiguritaPorNumero(int numero) {
        for (Figurita f : figuritasDisponibles) {
            if (f.getNumero() == numero) {
                return f;
            }
        }
        return null;
    }
    
    public boolean tieneFigurita(Figurita figurita) {
        return getCantidadFigurita(figurita) > 0;
    }
    
    public boolean tieneFiguritaPorNumero(int numero) {
        return getCantidadFiguritaPorNumero(numero) > 0;
    }
    
    public int getCantidadTotalFiguritas() {
        String sql = "SELECT COALESCE(SUM(cantidad), 0) AS total FROM almacenamiento WHERE id_usuario = ?";
        int total = 0;
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioActual.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener total de figuritas: " + e.getMessage());
        }
        
        return total;
    }
    
    public int getCantidadDiferentes() {
        String sql = "SELECT COUNT(*) AS diferentes FROM almacenamiento WHERE id_usuario = ? AND cantidad >= 1";
        int diferentes = 0;
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioActual.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                diferentes = rs.getInt("diferentes");
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener figuritas diferentes: " + e.getMessage());
        }
        
        return diferentes;
    }
    
    public ArrayList<Figurita> getFiguritasPorGrupo(char grupo) {
        ArrayList<Figurita> lista = new ArrayList<>();
        for (Figurita f : figuritasDisponibles) {
            if (Character.toUpperCase(f.getSeleccion().getGrupo()) == Character.toUpperCase(grupo)) {
                lista.add(f);
            }
        }
        return lista;
    }
    
    public ArrayList<Figurita> getMisFiguritas() {
        ArrayList<Figurita> misFiguritas = new ArrayList<>();
        String sql = "SELECT f.id_figurita, f.numero, f.jugador, f.posicion, f.rareza, " +
                     "s.id_seleccion, s.nombre, s.grupo " +
                     "FROM almacenamiento a " +
                     "INNER JOIN figurita f ON a.id_figurita = f.id_figurita " +
                     "INNER JOIN seleccion s ON f.id_seleccion = s.id_seleccion " +
                     "WHERE a.id_usuario = ? AND a.cantidad >= 1 " +
                     "ORDER BY f.numero";
        
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioActual.getIdUsuario());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Seleccion seleccion = new Seleccion(
                    rs.getInt("id_seleccion"),
                    rs.getString("nombre"),
                    rs.getString("grupo").charAt(0)
                );
                
                Figurita figurita = new Figurita(
                    rs.getInt("id_figurita"),
                    rs.getInt("numero"),
                    rs.getString("jugador"),
                    rs.getString("posicion"),
                    rs.getString("rareza"),
                    seleccion
                );
                
                misFiguritas.add(figurita);
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener mis figuritas: " + e.getMessage());
        }
        
        return misFiguritas;
    }
    
    public void recargarFiguritas() {
        cargarFiguritas();
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public ArrayList<Figurita> getFiguritasDisponibles() {
        return figuritasDisponibles;
    }
    
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
