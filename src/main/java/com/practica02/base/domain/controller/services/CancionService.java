package com.practica02.base.domain.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.quality.NotNull;
import com.practica02.base.domain.controller.dao.dao_models.DaoAlbum;
import com.practica02.base.domain.controller.dao.dao_models.DaoArtista;
import com.practica02.base.domain.controller.dao.dao_models.DaoBanda;
import com.practica02.base.domain.controller.dao.dao_models.DaoCancion;
import com.practica02.base.domain.controller.dao.dao_models.DaoGenero;
import com.practica02.base.models.Album;
import com.practica02.base.models.ArtistaBanda;
import com.practica02.base.models.Banda;
import com.practica02.base.models.Cancion;
import com.practica02.base.models.Genero;
import com.practica02.base.models.TipoArchivoEnum;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CancionService{
private DaoCancion db;


    public CancionService(){
        db = new DaoCancion();
    }
 
    public void create(@NotEmpty String nombre, Integer id_genero, Integer duracion, 
    @NotEmpty String url, @NotEmpty String tipo, Integer id_albun) throws Exception {
        if(nombre.trim().length() > 0 && url.trim().length() > 0 && 
        tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_albun > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_albun);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.save())
                throw new  Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void update(Integer id, @NotEmpty String nombre, Integer id_genero,
     Integer duracion, @NotEmpty String url, @NotEmpty String tipo, 
     Integer id_albun) throws Exception {
        if(nombre.trim().length() > 0 && url.trim().length() > 0 && tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_albun > 0) {
            db.setObj(db.listAll().get(id));
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_albun);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.update(id))
                throw new  Exception("No se pudo modificar los datos de la banda");
        }        
    }
    
    public List<HashMap> listaAlbumCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if(!da.listAll().isEmpty()) {
            Album [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i)); 
                aux.put("label", arreglo[i].getNombre());   
                lista.add(aux); 
            }
        }
        return lista;
    }

    public List<HashMap> listaAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if(!da.listAll().isEmpty()) {
            Genero [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i)); 
                aux.put("label", arreglo[i].getNombre()); 
                lista.add(aux);  
            }
        }
        return lista;
    }

    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for(TipoArchivoEnum r: TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }        
        return lista;
    }

    public List<HashMap> listCancion(){
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()) {
            Cancion [] arreglo = db.listAll().toArray();           
            for(int i = 0; i < arreglo.length; i++) {
                
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString(i));                
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() -1).getNombre());
                aux.put("id_genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() -1).getId().toString());
                aux.put("albun", new DaoAlbum().listAll().get(arreglo[i].getId_album() -1).getNombre());
                aux.put("id_albun", new DaoAlbum().listAll().get(arreglo[i].getId_album() -1).getId().toString());
                aux.put("duracion", arreglo[i].getDuracion().toString());
                aux.put("url", arreglo[i].getUrl());
                aux.put("tipo", arreglo[i].getTipo().toString());
                lista.add(aux);
            }
        }
        return lista;
    }

      public List<Cancion> listAllCancion() {
        return Arrays.asList(db.listAll().toArray());
    }

}

/*import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
async function create_1(nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: string | undefined, id_albun: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "create", { nombre, id_genero, duracion, url, tipo, id_albun }, init); }
async function listCancion_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listCancion", {}, init); }
async function listTipo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("CancionService", "listTipo", {}, init); }
async function listaAlbumCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listaAlbumCombo", {}, init); }
async function listaAlbumGenero_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listaAlbumGenero", {}, init); }
async function update_1(id: number | undefined, nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: string | undefined, id_albun: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "update", { id, nombre, id_genero, duracion, url, tipo, id_albun }, init); }
export { create_1 as create, listaAlbumCombo_1 as listaAlbumCombo, listaAlbumGenero_1 as listaAlbumGenero, listCancion_1 as listCancion, listTipo_1 as listTipo, update_1 as update };
 */


/*    private Integer id;
    private String nombre;
    private Integer duracion;
    private Integer id_genero;
    private String url;
    private TipoArchivoEnum tipo;
    private Integer id_album; */


/*public class CancionService {
    private DaoCancion db;

    public CancionService() {
        db = new DaoCancion();
    }

    public void createCancion(@NotNull String nombre, @NotNull Integer duracion, @NotNull String url,
            TipoArchivoEnum tipo, Integer id_album,Integer id_genero) throws Exception {
        if (nombre.trim().length() > 0 && duracion.toString().length() > 0 && url.trim().length() > 0) {
            db.getObj().setTipo(tipo);
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setUrl(url);
            db.getObj().setId_album(id_album);
            db.getObj().setId_genero(id_genero);
        }
        if (!db.save()) {
            throw new Exception("Error al guardar la cancion");
        }

    }

    public void updateCancion(Integer id, @NotNull String nombre, @NotNull Integer duracion, @NotNull String url,
            TipoArchivoEnum tipo, Integer id_album, Integer id_genero) throws Exception {
        if (id != null && nombre.trim().length() > 0 && duracion.toString().length() > 0 && url.trim().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setUrl(url);
            db.getObj().setTipo(tipo);
            db.getObj().setId_album(id_album);
            db.getObj().setId_genero(id_genero);
            
        } else {
            throw new Exception("Cancion no encontrada");
        }
        if (!db.update(id - 1)) {
            throw new Exception("No se pudo modificar la cancion al guardar la cancion");
        }

    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        if (!db.listAll().isEmpty()) {
            Cancion[] arreglo = db.listAll().toArray();
            DaoAlbum da = new DaoAlbum();
            DaoGenero dg = new DaoGenero();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString());
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("duracion", arreglo[i].getDuracion().toString());
                aux.put("url", arreglo[i].getUrl());
                aux.put("tipo", arreglo[i].getTipo().toString());
                aux.put("id_album" , String.valueOf(arreglo[i].getId_album()));
                aux.put("nombre_album", da.listAll().get(arreglo[i].getId_album()-1).getNombre());
                aux.put("id_genero", String.valueOf(arreglo[i].getId_genero()));
                aux.put("nombre_genero", dg.listAll().get(arreglo[i].getId_genero()-1).getNombre());
                lista.add(aux);
                /*aux.put("genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() -1).getNombre());
                aux.put("id_genero", new DaoGenero().listAll().get(arreglo[i].getId_genero() -1).getId().toString());
                aux.put("albun", new DaoAlbum().listAll().get(arreglo[i].getId_album() -1).getNombre());
                aux.put("id_albun", new DaoAlbum().listAll().get(arreglo[i].getId_album() -1).getId().toString()); */
 /*           }

        }
        return lista;
    }
/*
    public List<Cancion> listAllCancion() {
        return Arrays.asList(db.listAll().toArray());
    }
}*/
