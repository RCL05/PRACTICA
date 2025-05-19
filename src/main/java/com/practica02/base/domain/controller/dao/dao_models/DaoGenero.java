package com.practica02.base.domain.controller.dao.dao_models;
//import java.util.Date;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero> {
    
    private LinkedList<Genero> listaGenero;

    public Genero obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoGenero() {
        super(Genero.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Genero getObj() {
        if (obj == null) {
            this.obj = new Genero();
            
        }
        return this.obj;
    }

    public void setObj(Genero    obj) {
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


    public LinkedList<Genero> getListAll(){
        if (listaGenero == null) {
            this.listaGenero = listAll();
        }
        return listaGenero;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoGenero da = new DaoGenero();
        da.getObj().setId(1);
        da.getObj().setNombre("DubStupe");
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
