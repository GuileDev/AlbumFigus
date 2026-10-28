package logic;

public class Seleccion {
    private int id;
    private String nombre;
    private char grupo;

    public Seleccion() {
    }

    public Seleccion(int id, String nombre, char grupo) {
        this.id = id;
        this.nombre = nombre;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getGrupo() {
        return grupo;
    }

    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return nombre + " (Grupo " + grupo + ")";
    }
}
