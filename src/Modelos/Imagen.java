package Modelos;

public class Imagen {
    private int id;
    private int id_coche;
    private String url;

    public Imagen(int id, int id_coche, String url) {
        this.id = id;
        this.id_coche = id_coche;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_coche() {
        return id_coche;
    }

    public void setId_coche(int id_coche) {
        this.id_coche = id_coche;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
