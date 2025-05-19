package com.practica02.base.models;

public class Cuenta {
    private Integer id;
    private String gmail;
    private String clave;
    private Boolean estado;
    private Integer idPersona;



    // get and set
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }       
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
    public String getGmail() {
        return gmail;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getClave() {
        return clave;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public Boolean getEstado() {
        return estado;
    }
    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
    public Integer getIdPersona() {
        return idPersona;
    }
    



    
}
