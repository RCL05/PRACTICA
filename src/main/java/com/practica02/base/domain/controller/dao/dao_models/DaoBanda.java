package com.practica02.base.domain.controller.dao.dao_models;

//import java.util.Date;

import com.practica02.base.domain.controller.dao.AdapterDao;
//import com.practica02.base.models.Artista;
import com.practica02.base.models.Banda;

public class DaoBanda extends AdapterDao<Banda> {

    public Banda obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoBanda() {
        super(Banda.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Banda getObj() {
        if (obj == null) {
        this.obj = new Banda();
            
        }
        return this.obj;
    }

    public void setObj(Banda obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;

        } catch (Exception e) {
            //LOG DE ERRORES
            
            return false;
            // TODO: handle exception
        }

    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            //LOG DE ERRORES
            
            return false;
            // TODO: handle exception
        }

    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub

        DaoBanda da = new DaoBanda();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Juan 05");
        //da.getObj().setFecha(new Date());
        //da.getObj().setFecha(java.sql.Date.valueOf("2023-10-10"));
        //da.getObj().setApellido("Pérez Gonzales");
        //da.getObj().setNacionalida("Argentina");
        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }

    }

    
}
//CANCION