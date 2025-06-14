package com.practica02.base.domain.controller.dataStruct.list;

public class Node<E> {
    // v,k  --> key, value
    //E  --> colecciones
    //T  --> dato generico
    private E data;
    private Node<E> next;

    public Node(E data, Node<E> next) {
        this.data = data;
        this.next = next;
        
    }
    public Node(E data){
        this.data = data;
        //this.next=next;
        this.next = null;
    }

    public Node(Node<E> next) {
        this.data = null;
        this.next = next;
    }
    

    //getters and setters
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}
