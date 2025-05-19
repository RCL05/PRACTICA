package com.practica02.base.domain.controller.services;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.github.javaparser.quality.NotNull;
//import com.helger.commons.annotation.Nonempty;
import com.practica02.base.domain.controller.dao.dao_models.DaoAlbum;
import com.practica02.base.models.Album;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;


@BrowserCallable
@AnonymousAllowed
public class AlbumService {

    private DaoAlbum db;

    public AlbumService() {
        db = new DaoAlbum();
    }

    public void createAlbum(@NotNull  String nombre, @NotNull  Date fecha) throws Exception {
     if (nombre.trim().length()> 0 && fecha.toString().length()>0) {
        db.getObj().setNombre(nombre);
        db.getObj().setFecha(fecha);
     } 
     if (!db.save()){
        throw new Exception("Error al guardar el album");
     }   
    }

    public void updateAlbum(Integer id, @NotNull String nombre, @NotNull Date fecha) throws Exception {
        if (id != null && nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            db.setObj(db.listAll().get(id-1));    
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
        } else {
            throw new Exception("Album not found");

        }
        if (!db.update(id-1)) {
            throw new Exception("No se pudo modificar el album al guardar el album");
            
        }
    }

    public List<Album> listAllAlbum() {
        return Arrays.asList(db.listAll().toArray());
    }


}
