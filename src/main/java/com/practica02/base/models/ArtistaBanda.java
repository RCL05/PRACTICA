package com.practica02.base.models;

public class ArtistaBanda {
    private Integer id;
    private RolArtistaEnum rol;
    private Integer id_artista;
    private Integer id_banda;
    
    //get set
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    public void setRol(RolArtistaEnum rol){
        this.rol = rol;
    }
    public RolArtistaEnum getRol(){
        return rol;
    }

    public void setId_artista(Integer id_artista){
        this.id_artista = id_artista;
    }
    public Integer getId_artista(){
        return id_artista;
    }
    public void setId_banda(Integer id_banda){
        this.id_banda = id_banda;
    }
    public Integer getId_banda(){
        return id_banda;
    }
}
