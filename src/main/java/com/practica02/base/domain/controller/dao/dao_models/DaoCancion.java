package com.practica02.base.domain.controller.dao.dao_models;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Cancion;
//import com.practica02.base.models.Genero;
import com.practica02.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {

    public Cancion obj;
    private LinkedList<Cancion> listaCanciones;

    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoCancion() {
        super(Cancion.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Cancion getObj() {
        if (obj == null) {
        this.obj = new Cancion();
            
        }
        return this.obj;
    }

    public void setObj(Cancion obj) {
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

    public LinkedList<Cancion> getListAll(){
        if ( listaCanciones == null) {
            listaCanciones = listAll();
        }
        return listaCanciones;
    }



    public static void main(String[] args) {

       // DaoGenero daoGenero = new DaoGenero();

        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);

        da.getObj().setNombre("The sound of silence 15");
        da.getObj().setDuracion(250);
        
        //da.getObj().setGenero(new Genero());
        da.getObj().setUrl("www.google.com");
        da.getObj().setTipo(TipoArchivoEnum.STREAMING);
        da.getObj().setId_album(1);
        da.getObj().setId_genero(1);

        //da.getObj().setId(da.listAll().getLength() + 1);
        


        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }
        System.out.println("NUMERO EN LISTA: " +da.getListAll().getLength());

    }


    /*1. Cancion

a. Crear el registro

b. Modificar

c. Listar */
    
    
}



//CANCION

/*    private String nombre;
    private String duracion;
    private String id_genero;
    private Integer url;
    private TipoArchivoEnum tipo;
    private Integer id_album; */