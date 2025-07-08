package com.practica02.base.domain.controller.dataStruct.graphs;



import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

public class UndirectedGraph<E> extends DirectLableGraph<E>{

    public UndirectedGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex, clazz);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void insert_label(E o, E d, Float weight) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), weight);
            insert(getVertex(d), getVertex(o), weight);
        }
    }

    public static LinkedList<LinkedList<Adyacency>> constructAdj(int[][] edges, int V) {
        LinkedList<LinkedList<Adyacency>> adj = new LinkedList<>();
        // Inicializa la lista de adyacencia
        for (int i = 0; i < V; i++) {
            adj.add(new LinkedList<>());
        }
        // Llena la lista de adyacencia
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            float wt = edge[2];

            adj.get(u).add(new Adyacency(v, wt));
            adj.get(v).add(new Adyacency(u, wt)); // Si es no dirigido
        }
        return adj;
    }

    public static int[] dijkstra(LinkedList<LinkedList<Adyacency>> adj, int origen, int V) throws Exception {
        int[] dist = new int[V];
        boolean[] visitado = new boolean[V];
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;
        dist[origen] = 0;

        LinkedList<Integer> pendientes = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            pendientes.add(i);
        }

        while (!pendientes.isEmpty()) {
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int i = 0; i < pendientes.getLength(); i++) {
                int nodo = pendientes.get(i);
                if (!visitado[nodo] && dist[nodo] < minDist) {
                    minDist = dist[nodo];
                    u = nodo;
                }
            }
            if (u == -1)
                break;

            // Remover u de pendientes por valor
            for (int i = 0; i < pendientes.getLength(); i++) {
                if (pendientes.get(i) == u) {
                    pendientes.delete(i, pendientes.get(i));
                    break;
                }
            }
            visitado[u] = true;

            LinkedList<Adyacency> vecinos = adj.get(u);
            for (int i = 0; i < vecinos.getLength(); i++) {
                Adyacency v = vecinos.get(i);
                int destino = v.getDestiny();
                float peso = v.getWeight();
                if (!visitado[destino] && dist[destino] > dist[u] + peso) {
                    dist[destino] = (int) (dist[u] + peso);
                }
            }
        }
        return dist;
    }
}
