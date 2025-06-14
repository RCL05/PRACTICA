package com.practica02.base.domain.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoAlbum;
import com.practica02.base.domain.controller.dao.dao_models.DaoCancion;
import com.practica02.base.domain.controller.dao.dao_models.DaoGenero;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Album;

import com.practica02.base.models.Genero;
import com.practica02.base.models.TipoArchivoEnum;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CancionService {
    private DaoCancion db;

    public CancionService() {
        db = new DaoCancion();
    }

    public void create(@NotNull String nombre, Integer id_genero, Integer duracion,
            @NotEmpty String url, @NotEmpty String tipo, Integer id_albun) throws Exception {
        if (nombre.trim().length() > 0 && url.trim().length() > 0 &&
                tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_albun > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_albun);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void update(Integer id, @NotNull String nombre, Integer id_genero,
            Integer duracion, @NotNull String url, @NotNull String tipo,
            Integer id_albun) throws Exception {
        if (nombre.trim().length() > 0 && url.trim().length() > 0 && tipo.trim().length() > 0 && duracion > 0
                && id_genero > 0 && id_albun > 0) {
            db.setObj(db.listAll().get(id));
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_albun);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if (!db.update(id))
                throw new Exception("No se pudo modificar los datos de la banda");
        }
    }

    public List<HashMap> listaAlbumCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if (!da.listAll().isEmpty()) {
            Album[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString());
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if (!da.listAll().isEmpty()) {
            Genero[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString());
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivoEnum r : TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }
        return lista;
    }


    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(db.orderByAttribute(type, attribute).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.search(attribute, text, type);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public List<HashMap> listAllCancion() throws Exception {
        return Arrays.asList(db.all().toArray());
    }

}
