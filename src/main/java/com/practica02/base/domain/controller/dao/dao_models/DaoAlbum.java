package com.practica02.base.domain.controller.dao.dao_models;
import java.util.Date;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Album;

public class DaoAlbum extends AdapterDao<Album> {
    
    private LinkedList<Album> listaAlbum;

    public Album obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoAlbum() {
        super(Album.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Album getObj() {
        if (obj == null) {
            this.obj = new Album();
            
        }
        return this.obj;
    }

    public void setObj(Album    obj) {
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


    public LinkedList<Album> getListAll(){
        if (listaAlbum == null) {
            this.listaAlbum = listAll();
        }
        return listaAlbum;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoAlbum da = new DaoAlbum();
        da.getObj().setNombre("CAS");
        da.getObj().setId( 1);
        da.getObj().setFecha(new Date());

        da.getObj().setId_banda(87);//java.sql.Date.valueOf("2023-10-10")

        
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
