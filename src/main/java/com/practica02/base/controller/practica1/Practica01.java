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




    // ORDENAMIENTO DE LISTA ENLAZADA
    private void quickSortLista(LinkedList<Integer> lista, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partitionLista(lista, begin, end);
            quickSortLista(lista, begin, partitionIndex - 1);
            quickSortLista(lista, partitionIndex + 1, end);
        }
    }

    private int partitionLista(LinkedList<Integer> lista, int begin, int end) {
        int pivot = lista.get(end);
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (lista.get(j) <= pivot) {
                i++;
               
                int temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        
        int temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(end));
        lista.set(end, temp);
        return i + 1;
    }

    public void q_order_linkedlist() {

        long inicioArr = System.nanoTime();
        quickSortLista(lista, 0, lista.getLength() - 1);
        long finArr = System.nanoTime();
        System.out.println("=======================================================");
        System.out.printf("Tiempo usando quickSort(LISTA): %d ms\n", (finArr - inicioArr) / 1_000_000);
        

    }

    private void shellSortLista(LinkedList<Integer> lista) {
        int n = lista.getLength();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = lista.get(i);
                int j = i;
                while (j >= gap && lista.get(j - gap) > temp) {
                    lista.set(j, lista.get(j - gap));
                    j -= gap;
                }
                lista.set(j, temp);
            }
        }
    }

    public void ordenarListaShellSort() {
        long inicioArr = System.nanoTime();

        shellSortLista(lista);
        long finArr = System.nanoTime();
        System.out.printf("Tiempo usando shellSort(LISTA): %d ms\n", (finArr - inicioArr) / 1_000_000);
        System.out.println("=======================================================");

    }

    


    // ORDENAMIENTO DE ARREGLO
    private void quickSort(Integer arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public void q_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();
           
            long startTime = System.nanoTime();
            quickSort(arr, 0, arr.length - 1);
            
            long endTime = System.nanoTime() - startTime;
            System.out.printf("se ha demorado quicksort (ARREGLO) %d ms\n", endTime / 1000000);
            lista.toList(arr);
        }
    }

    public void s_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();

            long startTime = System.nanoTime();
            shell_sort(arr);
            long endTime = System.nanoTime() - startTime;
            System.out.printf("se ha demorado shellSort (ARREGLO) %d ms\n", endTime / 1_000_000);
            System.out.println("=======================================================");
            lista.toList(arr);
        }
    }

    public void shell_sort(Integer arrayToSort[]) {
        int n = arrayToSort.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arrayToSort[i];
                int j = i;
                while (j >= gap && arrayToSort[j - gap] > key) {
                    arrayToSort[j] = arrayToSort[j - gap];
                    j -= gap;
                }
                arrayToSort[j] = key;
            }
        }
    }

}