package com.practica02.base.domain.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.practica02.base.domain.controller.dao.dao_models.DaoArtistaBanda;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Album;
import com.practica02.base.models.ArtistaBanda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class ArtistaBandaService {
    private DaoArtistaBanda db;

    public ArtistaBandaService() {

        db = new DaoArtistaBanda();
    }

    public List<HashMap> listAll() throws Exception {

        return Arrays.asList(db.all().toArray());
    }
    /*
     * public List<ArtistaBanda> listAll() {
     * return Arrays.asList(db.listAll().toArray());
     * }
     */

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(db.orderByArtist(type, attribute).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.searchExact(attribute, text);
        if (!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

}
