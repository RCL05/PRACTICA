package com.practica02.base.domain.controller.dataStruct.graphs;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;


public abstract class Graph {
    public abstract Integer nro_vertex();

    public abstract Integer nro_edge();

    public abstract Adyacency exists_Edge(Integer o, Integer d);

    public abstract Float weight_Edge(Integer o, Integer d);

    public abstract void insert(Integer o, Integer d);

    public abstract void insert(Integer o, Integer d, float weight);

    public abstract LinkedList<Adyacency> adjacencies(Integer o);

 @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1 ; i <= nro_vertex(); i++) {
            sb.append("Vertex =").append(String.valueOf(i)).append("\n");
            LinkedList<Adyacency> list = adjacencies(i);
            if (!list.isEmpty()) {
                Adyacency[] matrix = list.toArray();
                for (Adyacency ad : matrix) {
                    sb.append("Adyacency =").append("\n").append("Vertex =").append(String.valueOf(ad.getDestiny()));
                    if (!ad.getWeight().isNaN()) {
                         sb.append("Weight =" + ad.getWeight().toString()).append("\n");
                    }
                    
                }
            }
            
        }

        return sb.toString();
    }
}

/*public abstract class Graph {
    public abstract Integer nro_vetex();
    public abstract Integer nro_edge();
    public abstract Boolean exists_Edge(Integer o, Integer d);
    public abstract Float wiegth_edge(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d, Float weigth);

    public abstract Linkendlist<Adyacencia> adyacencies(Integer o);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1 ; i < nro_vetex(); i++) {
            sb.append("Vetex =").append(String.valueOf(i)).append("\n");
            Linkendlist<Adyacencia> list = adyacencies(i);
            if (!list.isEmpty()) {
                Adyacencia[] matrix = list.toArray();
                for (Adyacencia ad : matrix) {
                    sb.append("Adyacency =").append("\n").append("Vertex").append(String.valueOf(ad.getdestiny()));
                    if (!ad.getWeigth().isNaN()) {
                        sb.append("Weigth =" + ad.getWeigth().toString()).append("\n");
                    }
                    
                }
            }
            
        }

        return sb.toString();  
    }
} */