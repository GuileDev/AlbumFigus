package logic;

public class Usuario {
    private int idUsuario;
    private String username;
    private int monedas;

    public Usuario() {
    }

    public Usuario(int idUsuario, String username, int monedas) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.monedas = monedas;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public void sumarMonedas(int cantidad) {
        this.monedas += cantidad;
    }

    public boolean restarMonedas(int cantidad) {
        if (this.monedas >= cantidad) {
            this.monedas -= cantidad;
            return true;
        }
        return false;
    }
}
