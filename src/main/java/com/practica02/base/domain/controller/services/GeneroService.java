package com.practica02.base.domain.controller.services;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoGenero;
import com.practica02.base.models.Genero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class GeneroService {
    private DaoGenero db;
    public GeneroService() {
        db = new DaoGenero();
    }

    public void createGenero(@NotNull String nombre) throws Exception {
        if (nombre.trim().length() > 0) {   
            db.getObj().setNombre(nombre);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar el genero");
        }
    }

    public void updateGenero(Integer id, @NotNull String nombre) throws Exception {
        if (id != null && nombre.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
        } else {
            throw new Exception("Genero no encontrado");
        }
        if (!db.update(id - 1)) {
            throw new Exception("No se pudo modificar el genero al guardar el genero");
        }
    }
    public List<Genero> listAllGenero() {
        return Arrays.asList(db.listAll().toArray());
}
}

