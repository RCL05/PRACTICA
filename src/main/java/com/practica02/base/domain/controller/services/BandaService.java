package com.practica02.base.domain.controller.services;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoBanda;
import com.practica02.base.models.Banda;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;


@BrowserCallable
@AnonymousAllowed
public class BandaService {

    private DaoBanda db;

    public BandaService() {
        db = new DaoBanda();
    }
    public void createBanda(@NotNull  String nombre, @NotNull  Date fecha) throws Exception {
        if (nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
        }
        
        if (!db.save()) {
            throw new Exception("Error al guardar la banda");
            
        }
    }

    public void updateBanda(  Integer id,@NotNull  String nombre, @NotNull  Date fecha) throws Exception {
        if (id != null && nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            db.setObj(db.listAll().get(id-1));    
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
        } else {
            throw new Exception("Banda not found");
            
        }
        if (!db.update(id-1)) {
            throw new Exception("No se pudo modificar la banda al guardar la banda");
            
        }
    }
        
    

    public List<Banda> listAllBanda() {
        return Arrays.asList(db.listAll().toArray());
    }


}
