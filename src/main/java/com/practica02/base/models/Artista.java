package com.practica02.base.models;

public class Artista {
    private Integer id;
    private String nombre;
    private String nacionalidad;
    private String apellido;
    private RolArtistaEnum rolArtista;
    private String imagen;


    //GET AND SET
    public void setId(Integer id){
        this.id= id;
    }
    public Integer getId(){
        return id;
    }
    public void setNombre(String nombre){
        this.nombre= nombre;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNacionalidad(String nacionalidad){
        this.nacionalidad= nacionalidad;
    }
    public String getNacionalidad(){
        return nacionalidad;
    }
    public void setApellido(String apellido){
        this.apellido= apellido;
    }
    public String getApellido(){
        return apellido;
    }
    public void setRolArtista(RolArtistaEnum rolArtista){
        this.rolArtista= rolArtista;
    }
    public RolArtistaEnum getRolArtista(){
        return rolArtista;
    }
    public void setImagen(String imagen){
        this.imagen= imagen;
    }
    public String getImagen(){
        return imagen;
    }
   

}
