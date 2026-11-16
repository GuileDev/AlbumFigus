package logic;

/**
 * representa una figurita del album.
 *
 * @author gerrz
 */
public class Figurita {
    private int identificadorFigurita;
    private int numero;
    private String jugador;
    private String posicion;
    private String rareza;
    private Seleccion seleccion;
    private String imagen;

    /**
     * constructor vacio.
     */
    public Figurita() {
    }

    /**
     * constructor sin imagen.
     *
     * @param identificadorFigurita id de la figurita
     * @param numero numero de la figurita
     * @param jugador nombre del jugador
     * @param posicion posicion en el campo
     * @param rareza rareza de la figurita
     * @param seleccion seleccion a la que pertenece
     */
    public Figurita(int identificadorFigurita, int numero, String jugador, String posicion, String rareza, Seleccion seleccion) {
        this(identificadorFigurita, numero, jugador, posicion, rareza, seleccion, null);
    }

    /**
     * constructor completo.
     *
     * @param identificadorFigurita id de la figurita
     * @param numero numero de la figurita
     * @param jugador nombre del jugador
     * @param posicion posicion en el campo
     * @param rareza rareza de la figurita
     * @param seleccion seleccion a la que pertenece
     * @param imagen nombre del archivo de imagen
     */
    public Figurita(int identificadorFigurita, int numero, String jugador, String posicion, String rareza, Seleccion seleccion, String imagen) {
        this.identificadorFigurita = identificadorFigurita;
        this.numero = numero;
        this.jugador = jugador;
        this.posicion = posicion;
        this.rareza = rareza;
        this.seleccion = seleccion;
        this.imagen = imagen;
    }

    /**
     * devuelve el id de la figurita.
     *
     * @return id de la figurita
     */
    public int getIdFigurita() {
        return identificadorFigurita;
    }

    /**
     * asigna el id de la figurita.
     *
     * @param identificadorFigurita nuevo id
     */
    public void setIdFigurita(int identificadorFigurita) {
        this.identificadorFigurita = identificadorFigurita;
    }

    /**
     * devuelve el numero.
     *
     * @return numero de la figurita
     */
    public int getNumero() {
        return numero;
    }

    /**
     * asigna el numero.
     *
     * @param numero nuevo numero
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * devuelve el jugador.
     *
     * @return nombre del jugador
     */
    public String getJugador() {
        return jugador;
    }

    /**
     * asigna el jugador.
     *
     * @param jugador nuevo nombre
     */
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    /**
     * devuelve la posicion.
     *
     * @return posicion del jugador
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * asigna la posicion.
     *
     * @param posicion nueva posicion
     */
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    /**
     * devuelve la rareza.
     *
     * @return rareza de la figurita
     */
    public String getRareza() {
        return rareza;
    }

    /**
     * asigna la rareza.
     *
     * @param rareza nueva rareza
     */
    public void setRareza(String rareza) {
        this.rareza = rareza;
    }

    /**
     * devuelve la seleccion.
     *
     * @return seleccion a la que pertenece
     */
    public Seleccion getSeleccion() {
        return seleccion;
    }

    /**
     * asigna la seleccion.
     *
     * @param seleccion nueva seleccion
     */
    public void setSeleccion(Seleccion seleccion) {
        this.seleccion = seleccion;
    }

    /**
     * devuelve el nombre de la imagen.
     *
     * @return nombre del archivo de imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * asigna el nombre de la imagen.
     *
     * @param imagen nuevo nombre
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * ruta del recurso de imagen asociado a esta figurita.
     * si no se asigno explicitamente, se deriva del numero y el nombre del jugador.
     *
     * @return ruta del recurso de imagen
     */
    public String getRutaImagen() {
        if (imagen != null && !imagen.trim().isEmpty()) {
            String imagenSinEspacios = imagen.trim();
            if (imagenSinEspacios.startsWith("/")) {
                return imagenSinEspacios;
            }
            return "/ImagenesParaDiseño/Figus/" + imagenSinEspacios;
        }
        String nombreNormalizado;
        if (jugador == null) {
            nombreNormalizado = "";
        } else {
            nombreNormalizado = jugador.toLowerCase().replace(" ", "_").replace(".", "").replaceAll("[^a-z0-9_]", "");
        }
        return "/ImagenesParaDiseño/Figus/" + numero + "_" + nombreNormalizado + ".png";
    }

    @Override
    public String toString() {
        return "#" + numero + " - " + jugador + " [rareza: " + rareza + "]";
    }
}