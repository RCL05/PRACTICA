package com.practica02.base.controller.practica1;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;
import java.io.*;

public class Practica01 {
    private Integer[] matriz;
    private LinkedList<Integer> lista;
    private LinkedList<Integer> repetidosArreglo;
    private LinkedList<Integer> repetidosLista;

    public void cargar() {
        int cantidad = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("/home/usuario/Descargas/data.txt"))) {
            while (br.readLine() != null) {
                cantidad++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        matriz = new Integer[cantidad];
        lista = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("/home/usuario/Descargas/data.txt"))) {
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                try {
                    int num = Integer.parseInt(linea.trim());
                    matriz[i++] = num;
                    lista.add(num);
                } catch (NumberFormatException e) {
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detectarRepetidosArreglo() {
        repetidosArreglo = new LinkedList<>();
        for (int i = 0; i < matriz.length; i++) {
            boolean repetido = false;
            for (int j = 0; j < i; j++) {
                if (matriz[i].equals(matriz[j])) {
                    repetido = true;
                    break;
                }
            }
            if (repetido && !contiene(repetidosArreglo, matriz[i])) {
                repetidosArreglo.add(matriz[i]);
            }
        }
    }

  
    public void detectarRepetidosLista() {
        repetidosLista = new LinkedList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            Integer actual = lista.get(i);
            boolean repetido = false;
            for (int j = 0; j < i; j++) {
                if (actual.equals(lista.get(j))) {
                    repetido = true;
                    
                    break;
                }
            }
            if (repetido && !contiene(repetidosLista, actual)) {
                repetidosLista.add(actual);
            }
        }
    }

  
    private boolean contiene(LinkedList<Integer> lista, Integer valor) {
        for (int i = 0; i < lista.getLength(); i++) {
            if (lista.get(i).equals(valor)) {
                return true;
            }
        }
        return false;
    }

    public void mostrarRepetidos(LinkedList<Integer> repetidos, String tipo) {
        System.out.println("Elementos repetidos (" + tipo + "):");
        for (int i = 0; i < repetidos.getLength(); i++) {   
            System.out.print(repetidos.get(i) + " ");
        }
        System.out.println();
        System.out.println("Cantidad de elementos repetidos (" + tipo + "): " + repetidos.getLength());
        
    }

    public LinkedList<Integer> RepetidosArreglo() {
        return repetidosArreglo;
    }

    public LinkedList<Integer> RepetidosLista() {
        return repetidosLista;
    }

    public void mostrarRepetidos() {

        System.out.println("Elementos repetidos (Arreglo):");
        for (int i = 0; i < repetidosArreglo.getLength(); i++) {
            System.out.print(repetidosArreglo.get(i) + " ");

        }
        System.out.println();
        
        System.out.println("Elementos repetidos (Lista):");
        for (int i = 0; i < repetidosLista.getLength(); i++) {
            System.out.print(repetidosLista.get(i) + " ");
        }
        System.out.println();
        System.out.println("Cantidad de elementos repetidos (Arreglo): " + repetidosArreglo.getLength());
        System.out.println("Cantidad de elementos repetidos (Lista): " + repetidosLista.getLength());
    }
}