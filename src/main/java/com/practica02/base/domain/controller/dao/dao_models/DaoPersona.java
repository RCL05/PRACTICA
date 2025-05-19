package com.practica02.base.domain.controller.dao.dao_models;
//import java.util.Date;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Persona;

public class DaoPersona extends AdapterDao<Persona> {
    
    private LinkedList<Persona> listaPersona;

    public Persona obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoPersona() {
        super(Persona.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Persona getObj() {
        if (obj == null) {
            this.obj = new Persona();
            
        }
        return this.obj;
    }

    public void setObj(Persona    obj) {
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

    public Boolean updte(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            //LOG DE ERRORES
            
            return false;
            // TODO: handle exception
        }

    }


    public LinkedList<Persona> getListAll(){
        if (listaPersona == null) {
            listaPersona = listAll();
        }
        return listaPersona;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoPersona da = new DaoPersona();
        da.getObj().setId(1);
        da.getObj().setUsauario("Juan");
        da.getObj().setEdad(25);

        
        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }

    }
    /* private Integer id;
    private String nombre;
    private Date fecha;
    private Integer id_banda; */
}
