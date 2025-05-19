package com.practica02.base.domain.controller.services;


import java.util.Arrays;
import java.util.List;

import com.practica02.base.domain.controller.dao.dao_models.DaoPersona;
import com.practica02.base.models.Persona;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class PersonaService {
    private DaoPersona db;
    public PersonaService() {
        db = new DaoPersona();
    }
    
    public void createPersona(String usuario, Integer edad) throws Exception {
        if (usuario.trim().length() > 0 && edad.toString().length() > 0) {
            db.getObj().setUsauario(usuario);
            db.getObj().setEdad(edad);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar la persona");
        }
    }

    public List<Persona> listAllPersona() {
        return Arrays.asList(db.listAll().toArray());
}
}