package logic;

/**
 * representa una seleccion de futbol.
 *
 * @author gerrz
 */
public class Seleccion {
    private int identificador;
    private String nombre;
    private char grupo;

    /**
     * constructor vacio.
     */
    public Seleccion() {
    }

    /**
     * constructor con datos.
     *
     * @param identificador identificador de la seleccion
     * @param nombre nombre de la seleccion
     * @param grupo grupo al que pertenece
     */
    public Seleccion(int identificador, String nombre, char grupo) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.grupo = grupo;
    }

    /**
     * devuelve el id.
     *
     * @return id de la seleccion
     */
    public int getId() {
        return identificador;
    }

    /**
     * asigna el id.
     *
     * @param identificador nuevo id
     */
    public void setId(int identificador) {
        this.identificador = identificador;
    }

    /**
     * devuelve el nombre.
     *
     * @return nombre de la seleccion
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * asigna el nombre.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * devuelve el grupo.
     *
     * @return grupo de la seleccion
     */
    public char getGrupo() {
        return grupo;
    }

    /**
     * asigna el grupo.
     *
     * @param grupo nuevo grupo
     */
    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return nombre + " (grupo " + grupo + ")";
    }
}