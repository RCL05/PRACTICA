package com.practica02.base.models;

import java.util.Date;

public class Banda {
    private Integer id;
    private String nombre;
    private Date fecha;
   // private Integer id_artista;
   // private Integer id_cancion;

    //get set

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }
    public void setFecha(Date fecha){
        this.fecha = fecha;
    }
    public Date getFecha(){
        return fecha;
    }
   /* public void setId_artista(Integer id_artista){
        this.id_artista = id_artista;
    }
    public Integer getId_artista(){
        return id_artista;
    }
    public void setId_cancion(Integer id_cancion){
        this.id_cancion = id_cancion;
    }
    public Integer getId_cancion(){
        return id_cancion;
    }*/
}
