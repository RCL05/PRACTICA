package com.practica02.base.domain.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Pageable;
/*import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;*/

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoArtista;
import com.practica02.base.models.Artista;
import com.practica02.base.models.RolArtistaEnum;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class ArtistaService {
    private DaoArtista db;

    public ArtistaService() {
        db = new DaoArtista();
    }

    /*
     * if (nombre.trim().length()> 0 && fecha.toString().length()>0) {
     * db.getObj().setNombre(nombre);
     * db.getObj().setFecha(fecha);
     * }
     * if (!db.save()){
     * throw new Exception("Error al guardar el album");
     * }
     * }
     */
    public void createArtista(@NotEmpty String nombre, @NotEmpty String nacionalidad, @NotEmpty String apellido,
            RolArtistaEnum rolArtista, String imagen) throws Exception {
        if (nombre.trim().length() > 0 && nacionalidad.trim().length() > 0 && apellido.trim().length() > 0) {

            db.getObj().setNombre(nombre);
            db.getObj().setNacionalidad(nacionalidad);
            db.getObj().setApellido(apellido);
            db.getObj().setRolArtista(rolArtista);
            db.getObj().setImagen(imagen);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar el artista");

        }
    }
    //
    public void updateArtista(Integer id, @NotNull String nombre, @NotNull String nacionalidad,
            RolArtistaEnum rolArtista, @NotNull String apellido) throws Exception {
        if (id != null && nombre.trim().length() > 0 && nacionalidad.trim().length() > 0 && apellido.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
            db.getObj().setNacionalidad(nacionalidad);
            db.getObj().setApellido(apellido);
            db.getObj().setRolArtista(rolArtista);
        } else {
            throw new Exception("Artista not found");

        }
        if (!db.update(id - 1)) {
            throw new Exception("No se pudo modificar el artista");

        }
    }
    //

    public List<Artista> list(Pageable pageable) {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<Artista> listAll() {
        return (List<Artista>) Arrays.asList(db.listAll().toArray());
    }

    public List<String> listCountry() {
        List<String> nacionalidades = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            nacionalidades.add(locale.getDisplayCountry());
        }
        return nacionalidades;
    }

    public List<String> listRolArtista() {
        List<String> lista = new ArrayList<>();
        for (RolArtistaEnum r : RolArtistaEnum.values()) {
            lista.add(r.toString());
        }
        return lista;
    }

}
