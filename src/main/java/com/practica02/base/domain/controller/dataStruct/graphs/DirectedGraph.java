package com.practica02.base.domain.controller.dataStruct.graphs;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

public class DirectedGraph extends Graph {
    private Integer nro_vertex;
    private Integer nro_edge;
    private LinkedList<Adyacency> list_adjacencies[];

    public DirectedGraph(Integer nro_vertex) {
        this.nro_vertex = nro_vertex;
        this.nro_edge = 0;
        list_adjacencies = new LinkedList[nro_vertex + 1];
        for (int i = 0; i <= nro_vertex; i++) {
            list_adjacencies[i] = new LinkedList<>();
        }

    }

    @Override
    public Integer nro_vertex() {
        return this.nro_vertex;
    }

    @Override
    public Integer nro_edge() {
        // TODO Auto-generated method stub
        return this.nro_edge;
    }

    @Override
    public Adyacency exists_Edge(Integer o, Integer d) {
        Adyacency band = null;
        if (o.intValue() <= nro_vertex.intValue() && d.intValue() <= nro_vertex.intValue()) {
            LinkedList<Adyacency> list = list_adjacencies[o];
            if (!list.isEmpty()) {
                Adyacency[] matrix = list.toArray();
                for (Adyacency adj : matrix) {
                    if (adj.getDestiny().intValue() == d.intValue()) {
                        band = adj;
                        break;
                    }
                }
            }
        }
        return band;
    }

    @Override
    public Float weight_Edge(Integer o, Integer d) {
        Adyacency adj = exists_Edge(o, d);
        if (adj != null) {
            return adj.getWeight();
        }
        return Float.NaN;
    }

    @Override
    public void insert(Integer o, Integer d, float weight)  {
        if (o.intValue() <= nro_vertex.intValue() && d.intValue() <= nro_vertex.intValue()) {

            if (exists_Edge(o, d) == null) {
                nro_edge++;
                Adyacency aux = new Adyacency(d, weight);
                aux.setWeight(weight);
                aux.setDestiny(d);

                list_adjacencies[o].add(aux);
            }

        } else {
            throw new IndexOutOfBoundsException("Vertice fuera de rango");
        }
    }

    @Override
    public void insert(Integer o, Integer d) {
        insert(o, d, Float.NaN);
    }

    @Override
    public LinkedList<Adyacency> adjacencies(Integer o) {
    return list_adjacencies[o];    
    }

    public LinkedList<Adyacency>[] getList_adjacencies() {
        return this.list_adjacencies;
    }

    public void setList_adjacencies(LinkedList<Adyacency>[] list_adjacencies) {
        this.list_adjacencies = list_adjacencies;
    }
    public void setNro_edge(Integer nro_edge) {
        this.nro_edge = nro_edge;
    }

}
