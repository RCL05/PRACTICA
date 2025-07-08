package com.practica02.base.controller.practica1;

import com.practica02.base.domain.controller.dataStruct.graphs.DirectLableGraph;
import com.practica02.base.domain.controller.dataStruct.graphs.DirectedGraph;
import com.practica02.base.domain.controller.dataStruct.graphs.Graph;

public class Ejecutar {
    
    public static void main(String[] args) {
        // Practica01 practica = new Practica01();
        // practica.cargar();

        // // long inicioArr = System.nanoTime();
        // // practica.detectarRepetidosArreglo();
        // // long finArr = System.nanoTime();
        // // practica.mostrarRepetidos(practica.RepetidosArreglo(), "Arreglo");
        // // System.out.printf("Tiempo usando arreglo: %d ms\n", (finArr - inicioArr) / 1_000_000);
        // // System.out.println("---------------------------------------------------------------------------");
        // // long inicioList = System.nanoTime();
        // // practica.detectarRepetidosLista();
        // // long finList = System.nanoTime();
        // // practica.mostrarRepetidos(practica.RepetidosLista(), "Lista Enlazada");
        // // System.out.printf("Tiempo usando lista enlazada: %d ms\n", (finList - inicioList) / 1_000_000);

        
        
        // practica.q_order_linkedlist();
        
        // practica.ordenarListaShellSort();
       
        // practica.q_order();
        // practica.s_order();

        // Graph grafo = new DirectedGraph(5);
        // System.out.println(grafo.toString());


        DirectLableGraph grafo = new DirectLableGraph(5, String.class);
        grafo.label_vertex(1, "Alex");
        grafo.label_vertex(2, "Bina");
        grafo.label_vertex(3, "Cris");
        grafo.label_vertex(4, "Dani");
        grafo.label_vertex(5, "Eli");
        grafo.insert_label("Alex", "Bina", 1.0f);
        grafo.insert_label("Bina", "Cris", 2.0f);
        grafo.insert_label("Cris", "Dani", 3.0f);
        grafo.insert_label("Dani", "Eli", 4.0f);
        grafo.insert_label("Eli", "Alex", 5.0f);
        System.out.println(grafo.toString());


    }
}