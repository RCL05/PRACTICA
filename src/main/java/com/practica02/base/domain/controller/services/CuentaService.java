package com.practica02.base.domain.controller.services;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoCuenta;
import com.practica02.base.models.Cuenta;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.Email;

@BrowserCallable
@AnonymousAllowed
public class CuentaService {
     public DaoCuenta db;
        public CuentaService() {
            db = new DaoCuenta();
        }
        public void createCuenta(@NotNull @Email String gmail, @NotNull  String clave) throws Exception {
        if (gmail.trim().length() > 0 && clave.trim().length()>0) {
            db.getObj().setGmail(gmail);
            db.getObj().setClave(clave);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar la cuenta");
        }
    
    }
        public List<Cuenta> listAllCuenta(){
            return Arrays.asList(db.listAll().toArray());
        }
}
/*   private Integer id;
    private String gmail;
    private String clave;
    private Boolean estado;
    private Integer idPersona; */