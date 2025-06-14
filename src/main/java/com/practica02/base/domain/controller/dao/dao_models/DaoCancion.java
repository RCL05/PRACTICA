package com.practica02.base.domain.controller.dao.dao_models;

import java.util.HashMap;

import com.practica02.base.domain.controller.Utiles;
import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.ArtistaBanda;
import com.practica02.base.models.Cancion;
//import com.practica02.base.models.Genero;
import com.practica02.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {

    public Cancion obj;
    private LinkedList<Cancion> listaCanciones;

    public DaoCancion() {
        super(Cancion.class);

        // TODO Auto-generated constructor stub
    }

    // get and set
    public Cancion getObj() {
        if (obj == null) {
            this.obj = new Cancion();

        }
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;

        } catch (Exception e) {
            // LOG DE ERRORES

            return false;
            // TODO: handle exception
        }

    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            // LOG DE ERRORES

            return false;
            // TODO: handle exception
        }

    }

    public LinkedList<Cancion> getListAll() {
        if (listaCanciones == null) {
            listaCanciones = listAll();
        }
        return listaCanciones;
    }

    public static void main(String[] args) {

        // DaoGenero daoGenero = new DaoGenero();

        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);

        da.getObj().setNombre("The sound of silence 15");
        da.getObj().setDuracion(250);

        // da.getObj().setGenero(new Genero());
        da.getObj().setUrl("www.google.com");
        da.getObj().setTipo(TipoArchivoEnum.STREAMING);
        da.getObj().setId_album(1);
        da.getObj().setId_genero(1);

        // da.getObj().setId(da.listAll().getLength() + 1);

        if (da.save()) {
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }
        System.out.println("NUMERO EN LISTA: " + da.getListAll().getLength());

    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Cancion arreglo) throws Exception {
        DaoAlbum da = new DaoAlbum();
        DaoGenero dg = new DaoGenero();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("albun", da.get(arreglo.getId_album()).getNombre());
        aux.put("genero", dg.get(arreglo.getId_genero()).getNombre());

        return aux;
    }

    public LinkedList<HashMap<String, String>> orderByAttribute(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();

        if (!all().isEmpty()) {

            HashMap<String, String> arr[] = all().toArray();
             if (type == null || type == 0) {
            lista.toList(arr);
            return lista;
        }
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
            
        }
        return lista;
    }

    private void quickSort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    private int partition(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                String valJ = arr[j].get(attribute) != null ? arr[j].get(attribute).toString() : "";
                String valPivot = pivot.get(attribute) != null ? pivot.get(attribute).toString() : "";
                if (valJ.toLowerCase().compareTo(valPivot.toLowerCase()) < 0) {
                    i++;
                    HashMap<String, String> temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                String valJ = arr[j].get(attribute) != null ? arr[j].get(attribute).toString() : "";
                String valPivot = pivot.get(attribute) != null ? pivot.get(attribute).toString() : "";
                if (valJ.toLowerCase().compareTo(valPivot.toLowerCase()) > 0) {
                    i++;
                    HashMap<String, String> temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        } 
        HashMap<String, String> temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;
        return i + 1;
    }

    /*
     * public LinkedList<HashMap<String, String>> orderByCancion(Integer type,
     * String attribute) throws Exception {
     * 
     * LinkedList<HashMap<String, String>> lista = all();
     * if (!lista.isEmpty()) {
     * HashMap arr[] = lista.toArray();
     * int n = arr.length;
     * if (type == Utiles.ASCEDENTE) {
     * // ascendente
     * for (int i = 0; i < n - 1; i++) {
     * int min_index = i;
     * for (int j = i + 1; j < n; j++) {
     * if (arr[j].get(attribute).toString().toLowerCase()
     * .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) < 0) {
     * min_index = j;
     * }
     * }
     * 
     * HashMap temp = arr[min_index];
     * arr[min_index] = arr[i];
     * arr[i] = temp;
     * }
     * } else {
     * // descendente
     * for (int i = 0; i < n - 1; i++) {
     * int min_index = i;
     * for (int j = i + 1; j < n; j++) {
     * if (arr[j].get(attribute).toString().toLowerCase()
     * .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) > 0) {
     * min_index = j;
     * }
     * }
     * 
     * HashMap temp = arr[min_index];
     * arr[min_index] = arr[i];
     * arr[i] = temp;
     * }
     * }
     * 
     * }
     * return lista;
     * }
     * 
     */
// public LinkedList<HashMap<String, String>> getOriginalList() throws Exception {
//     return all();
// }
    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();
        if (!lista.isEmpty()) {
            lista = orderByAttribute(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute, text);
            System.out.println("La N de la mitad es: " + n);
            // System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
            switch (type) {
                case 1:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }

                    break;
                case 2:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }

                    break;

                default:

                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }

   /* public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!all().isEmpty()) {
            HashMap<String, String> arr[] = all().toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }*/

    public Integer bynaryLineal(HashMap<String, String>[] array, String attribute, String text)
            throws Exception {
        // 1 = lineal
        // 2 = binario
        Integer half = 0;

        if (!(array.length == 0) && !text.isEmpty()) {
            half = array.length / 2;
            int aux = 0;
            // System.out.println(text.trim().toLowerCase().charAt(0) + "*****" + half +
            // "***" +
            // array[half].get(attribute).toString().trim().toLowerCase().charAt(0));
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = 1;
            else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = -1;
            half = half * aux;

        }
        return half;
    }

}