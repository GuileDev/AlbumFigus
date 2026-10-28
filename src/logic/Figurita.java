package logic;

public class Figurita {
    private int idFigurita;
    private int numero;
    private String jugador;
    private String posicion;
    private String rareza;
    private Seleccion seleccion;

    public Figurita() {
    }

    public Figurita(int idFigurita, int numero, String jugador, String posicion, String rareza, Seleccion seleccion) {
        this.idFigurita = idFigurita;
        this.numero = numero;
        this.jugador = jugador;
        this.posicion = posicion;
        this.rareza = rareza;
        this.seleccion = seleccion;
    }

    public int getIdFigurita() {
        return idFigurita;
    }

    public void setIdFigurita(int idFigurita) {
        this.idFigurita = idFigurita;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getRareza() {
        return rareza;
    }

    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    public Seleccion getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Seleccion seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public String toString() {
        return "#" + numero + " - " + jugador + " [Rareza: " + rareza + "]";
    }
}
