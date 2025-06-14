package com.practica02.base.controller.practica1;

public class Ejecutar {
    
    public static void main(String[] args) {
        Practica01 practica = new Practica01();
        practica.cargar();

        // long inicioArr = System.nanoTime();
        // practica.detectarRepetidosArreglo();
        // long finArr = System.nanoTime();
        // practica.mostrarRepetidos(practica.RepetidosArreglo(), "Arreglo");
        // System.out.printf("Tiempo usando arreglo: %d ms\n", (finArr - inicioArr) / 1_000_000);
        // System.out.println("---------------------------------------------------------------------------");
        // long inicioList = System.nanoTime();
        // practica.detectarRepetidosLista();
        // long finList = System.nanoTime();
        // practica.mostrarRepetidos(practica.RepetidosLista(), "Lista Enlazada");
        // System.out.printf("Tiempo usando lista enlazada: %d ms\n", (finList - inicioList) / 1_000_000);

        
        
        practica.q_order_linkedlist();
        
        practica.ordenarListaShellSort();
       
        practica.q_order();
        practica.s_order();



    }
}