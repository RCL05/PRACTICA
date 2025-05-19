package com.practica02.base.domain.controller.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import com.nimbusds.jose.shaded.gson.Gson;
import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

public class AdapterDao <T> implements InterfaceDao<T>{

    private Class <T> clazz;
    private Gson g;
    protected  static String base_path = "data"+File.separatorChar;

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.g = new Gson();
    }


    private String readFile() throws Exception{

        File  file= new File(base_path+clazz.getSimpleName()+".json");
        if(!file.exists()){
         //todo crear archivo
         saveFile("[]");
        }
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner((new FileReader(file)))){
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }

    private void saveFile(String data) throws Exception{
        
        File  file= new File(base_path+clazz.getSimpleName()+".json");
        //file.getParentFile().mkdirs();
       // if(!file.exists()){
       if (!file.exists()) {
        System.out.println("Estot AQUI!!! " + file.getAbsolutePath());
        file.createNewFile();        
       }
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            //metodo para que el archivo se persisita de forma inmediata
            fw.flush();
            fw.close();
        
    }

    @Override
    public LinkedList<T> listAll() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'listAll'");
        LinkedList<T> lista = new LinkedList<>();
        //File  file= new File(base_path+clazz.getSimpleName()+".json");
        try {
            String data = readFile();
            T[] m = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            
            lista.toList(m);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            // TODO: handle exception
        }
        return lista;
    }

    @Override
    public void persist(T obj) throws Exception {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'persist'");

        LinkedList<T> lista = listAll();
        lista.add(obj);
        saveFile(g.toJson(lista.toArray()));
        //System.out.println(g.toJson(list.toArrary()));

    }

    
    @Override
    public void update(T obj, Integer pos) throws Exception {
        LinkedList<T> lista = listAll();
        lista.update(obj, pos);
        saveFile(g.toJson(lista.toArray()));
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void update_by_id(T obj, Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update_by_id'");
    }

    @Override
    public T get(Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }


    
}

