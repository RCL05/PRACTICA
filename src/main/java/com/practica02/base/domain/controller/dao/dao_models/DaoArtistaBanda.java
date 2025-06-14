package com.practica02.base.domain.controller.dao.dao_models;

import java.util.HashMap;

import com.practica02.base.domain.controller.Utiles;
import com.practica02.base.domain.controller.dao.AdapterDao;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import com.practica02.base.models.Artista;
//import com.practica02.base.models.Artista;
import com.practica02.base.models.ArtistaBanda;
import com.practica02.base.models.RolArtistaEnum;

import io.swagger.v3.oas.models.links.Link;

public class DaoArtistaBanda extends AdapterDao<ArtistaBanda> {

    private LinkedList<ArtistaBanda> listaArtistaBanda;

    public ArtistaBanda obj;
    // instancia de DaoArtista
    // DaoArtista daoArtista = new DaoArtista();

    public DaoArtistaBanda() {
        super(ArtistaBanda.class);

        // TODO Auto-generated constructor stub
    }

    // get and set
    public ArtistaBanda getObj() {
        if (obj == null)
            this.obj = new ArtistaBanda();

        return this.obj;
    }

    public void setObj(ArtistaBanda obj) {
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

    public Boolean updte(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            // LOG DE ERRORES

            return false;
            // TODO: handle exception
        }

    }

    // public LinkedList<ArtistaBanda> getListAll() {
    // if (listaArtistaBanda == null) {
    // listaArtistaBanda = listAll();
    // }
    // return listaArtistaBanda;
    // }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DaoArtistaBanda da = new DaoArtistaBanda();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setRol(RolArtistaEnum.BAJISTA);
        da.getObj().setId_artista(2);
        da.getObj().setId_banda(2);
        if (da.save()) {
            System.out.println("Se guardo correctamente");
        } else {
            System.out.println("Error al guardar");
        }

    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            ArtistaBanda[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(ArtistaBanda arreglo) throws Exception {
        DaoArtista da = new DaoArtista();
        DaoBanda db = new DaoBanda();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("rol", arreglo.getRol().toString());
        aux.put("artista", da.get(arreglo.getId_artista()).getNombre());
        aux.put("banda", db.get(arreglo.getId_banda()).getNombre());

        return aux;
    }

    public LinkedList<HashMap<String, String>> orderByArtist(Integer type, String attribute) throws Exception {

        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                // ascendente
                for (int i = 0; i < n - 1; i++) {
                    int min_index = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) < 0) {
                            min_index = j;
                        }
                    }

                    HashMap temp = arr[min_index];
                    arr[min_index] = arr[i];
                    arr[i] = temp;
                }
            } else {
                // descendente
                for (int i = 0; i < n - 1; i++) {
                    int min_index = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_index].get(attribute).toString().toLowerCase()) > 0) {
                            min_index = j;
                        }
                    }

                    HashMap temp = arr[min_index];
                    arr[min_index] = arr[i];
                    arr[i] = temp;
                }
            }

        }
        return lista;
    }
    private int binarySearch(HashMap<String, String>[] arr, String attribute, String text) {
    int low = 0;
    int high = arr.length - 1;
    text = text.toLowerCase();

    while (low <= high) {
        int mid = (low + high) / 2;
        String midVal = arr[mid].get(attribute).toString().toLowerCase();
        int cmp = midVal.compareTo(text);

        if (cmp < 0) {
            low = mid + 1;
        } else if (cmp > 0) {
            high = mid - 1;
        } else {
            return mid; // encontrado
        }
    }
    return -1; // no encontrado
}

    // public LinkedList<HashMap<String, String>> search(String attribute, String
    // text, Integer type) throws Exception {
    // LinkedList<HashMap<String, String>> lista = all();
    // LinkedList<HashMap<String, String>> resp = new LinkedList<>();
    // if (!lista.isEmpty()) {

    // HashMap<String, String>[] arr = lista.toArray();
    // // System.out.println(attribute+" "+text+" ** *** * * ** * * * *");
    // switch (type) {
    // case 1:
    // for (HashMap m : arr) {
    // if (m.get(attribute).toString().toLowerCase().startsWith(text.toLowerCase()))
    // {
    // resp.add(m);
    // }
    // }

    // break;
    // case 2:
    // for (HashMap m : arr) {
    // if (m.get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
    // resp.add(m);
    // }
    // }

    // break;

    // default:
    // for (HashMap m : arr) {
    // if (m.get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
    // resp.add(m);
    // }
    // }
    // break;
    // }
    // }
    // return resp;
    // }
public LinkedList<HashMap<String, String>> searchExact(String attribute, String text) throws Exception {
    LinkedList<HashMap<String, String>> lista = orderQ(Utiles.ASCEDENTE, attribute); // Ordena primero
    LinkedList<HashMap<String, String>> resp = new LinkedList<>();
    if (!lista.isEmpty()) {
        HashMap<String, String>[] arr = lista.toArray();
        int idx = binarySearch(arr, attribute, text);
        if (idx != -1) {
            resp.add(arr[idx]);
        }
    }
    return resp;
}
    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();
        if (!lista.isEmpty()) {
            lista = orderQ(Utiles.ASCEDENTE, attribute);
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
                        n *= -1;
                        for (int i = 0; i < n + n; i++) {
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
                        for (int i = 0; i < n + n; i++) {
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
                    // if (n > 0) {
                    // for (int i = n; i < arr.length; i++) {
                    // if
                    // (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase()))
                    // {
                    // resp.add(arr[i]);
                    // }
                    // }
                    // } else if (n < 0) {
                    // n *= -1;
                    // for (int i = 0; i < n + n; i++) {
                    // if
                    // (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase()))
                    // {
                    // resp.add(arr[i]);
                    // }
                    // }
                    // } else {
                    // for (int i = 0; i < arr.length; i++) {
                    // if
                    // (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase()))
                    // {
                    // resp.add(arr[i]);
                    // }
                    // }
                    // }
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }

    // private int partition(HashMap<String, String> arr[], int begin, int end,
    // Integer type, String attribute) {
    // HashMap<String, String> pivot = arr[end];
    // int i = (begin - 1);
    // if (type == Utiles.ASCEDENTE) {
    // for (int j = begin; j < end; j++) {
    // if (arr[j].get(attribute).toString().toLowerCase()
    // .compareTo(pivot.get(attribute).toString().toLowerCase()) < 0) {
    // i++;
    // HashMap<String, String> temp = arr[i];
    // arr[i] = arr[j];
    // arr[j] = temp;
    // }
    // }
    // } else {
    // for (int j = begin; j < end; j++) {
    // if (arr[j].get(attribute).toString().toLowerCase()
    // .compareTo(pivot.get(attribute).toString().toLowerCase()) > 0) {
    // i++;
    // HashMap<String, String> temp = arr[i];
    // arr[i] = arr[j];
    // arr[j] = temp;
    // }
    // }
    // }
    // HashMap<String, String> temp = arr[i + 1];
    // arr[i + 1] = arr[end];
    // arr[end] = temp;
    // return i + 1;

    // }

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

    private void quickSort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!all().isEmpty()) {
            HashMap<String, String> arr[] = all().toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }

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
