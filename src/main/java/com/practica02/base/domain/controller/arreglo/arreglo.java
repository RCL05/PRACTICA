package com.practica02.base.domain.controller.arreglo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class arreglo {
    
    public static void main(String[] args) {
        long inicioTiempo = System.currentTimeMillis();
        System.out.println("Inicio del programa");
        
        String filePath = "/home/usuario/Descargas/practica02/src/data.txt";
        
        ArrayList<String> dataList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
     
        String[] dataArray = dataList.toArray(new String[0]);
        HashMap<String, Integer> elementCount = new HashMap<>();
        
        for (String data : dataArray) {
            elementCount.put(data, elementCount.getOrDefault(data, 0) + 1);
        }
       
        List<String> repeatedElements = new ArrayList<>();
        
        for (String key : elementCount.keySet()) {
            if (elementCount.get(key) > 1) {
                repeatedElements.add(key);
            }
        }
        
        
        System.out.println("Elementos repetidos:");
        for (String element : repeatedElements) {
            System.out.println(element + " - Repetido " + elementCount.get(element) + " veces");
        }

        System.out.println("Total de elementos repetidos: " + repeatedElements.size());
    long finTiempo = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (finTiempo - inicioTiempo) + " ms");                                 
    }
}
/*long inicioTiempo = System.currentTimeMillis();
        System.out.println("Inicio del programa");
        long finTiempo = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (finTiempo - inicioTiempo) + " ms"); 
         */