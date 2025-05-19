package com.practica02.base.models;

public class Persona {
    private Integer id;
    private String usauario;
    private Integer edad;

    //private Cuenta cuenta;





    // get and set
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }   
    public void setUsauario(String usauario) {
        this.usauario = usauario;
    }   
    public String getUsauario() {
        return usauario;
    }   
    public void setEdad(Integer edad) {
        this.edad = edad;
    }   
    public Integer getEdad() {
        return edad;
    }   
}
