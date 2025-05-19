package com.practica02.base.domain.controller.dao.dao_models;

import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Artista;
import com.practica02.base.models.RolArtistaEnum;

public class DaoArtista extends AdapterDao<Artista> {


    private LinkedList<Artista> listaArtistas;

    public Artista obj;
    //instancia de DaoArtista
    //DaoArtista daoArtista = new DaoArtista();

    
    public DaoArtista() {
        super(Artista.class);

        //TODO Auto-generated constructor stub
    }

    //get and set
    public Artista getObj() {
        if (obj == null) {
        this.obj = new Artista();
            
        }
        return this.obj;
    }

    public void setObj(Artista obj) {
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

   public LinkedList<Artista> getListAll(){
    if (listaArtistas == null) {
        listaArtistas = listAll();
    }
    return listaArtistas;  
   }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoArtista da = new DaoArtista();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Luis");
        da.getObj().setApellido("Lopez");
        da.getObj().setNacionalidad("Mexicana");
        da.getObj().setRolArtista(RolArtistaEnum.FLAUTISTA);
        if(da.save()){
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }

    }
    
}
