package com.practica02.base.models;

public class Cancion {
    private Integer id;
    private String nombre;
    private Integer duracion;
    private Integer id_genero;
    private String url;
    private TipoArchivoEnum tipo;
    private Integer id_album;


    //get set
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }       

    public String getNombre() {
        return nombre;
    } 
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    public Integer getDuracion() {
        return duracion;
    }   
    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }
    public Integer getId_genero() {
        return id_genero;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setTipo(TipoArchivoEnum tipo) {
        this.tipo = tipo;
    }
    public TipoArchivoEnum getTipo() {
        return tipo;

    }

    public void setId_album(Integer id_album) {
        this.id_album = id_album;
    }
    public Integer getId_album() {
        return id_album;
    }

}
