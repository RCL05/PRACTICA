package com.practica02.base.domain.controller.dao.dao_models;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
//import com.practica02.base.models.Artista;
import com.practica02.base.models.ArtistaBanda;

public class DaoArtistaBanda extends AdapterDao<ArtistaBanda> {


    private LinkedList<ArtistaBanda> listaArtistaBanda;

    public ArtistaBanda obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoArtistaBanda() {
        super(ArtistaBanda.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public ArtistaBanda getObj() {
        if (obj == null) {
        this.obj = new ArtistaBanda();
            
        }
        return this.obj;
    }

    public void setObj(ArtistaBanda obj) {
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

   public LinkedList<ArtistaBanda> getListAll(){
    if (listaArtistaBanda == null) {
        listaArtistaBanda = listAll();
    }
    return listaArtistaBanda;  
   }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoArtistaBanda da = new DaoArtistaBanda();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setRol( null);
        da.getObj().setId_artista(1);
        da.getObj().setId_banda(1);
        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }

    }
    
}
