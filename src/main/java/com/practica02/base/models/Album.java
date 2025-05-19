package com.practica02.base.models;

import java.util.Date;
public class Album {
    private Integer id;
    private String nombre;
    private Date fecha;
    private Integer id_banda;

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
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setId_banda(Integer id_banda) {
        this.id_banda = id_banda;
    }
    public Integer getId_banda() {
        return id_banda;
    }
}
