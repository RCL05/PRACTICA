package com.practica02.base.domain.controller.dataStruct.graphs;

public class Adyacency {
    private Float weight;
    private Integer destiny;


    public Float getWeight() {   
             return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getDestiny() {
        return destiny;
    }

    public void setDestiny(Integer destiny) {
        this.destiny = destiny;
    }

        public Adyacency(Integer destiny, Float weight) {
        this.destiny = destiny;
        this.weight = weight;
    }
}
