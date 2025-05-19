package com.practica02.base.domain.controller.dataStruct.queque;


public class Queque<E> {

    private QuequeImplementation<E> queque;

    public Queque(Integer top) {
        queque = new QuequeImplementation<E>(top);
    }

    public Boolean queque(E data){
        try {
            queque.queque(data);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public E dequeque(){
        try {
            return queque.dequeque();
        } catch (Exception e) {
            return null;
        }
    }
    public Boolean isFullQueque(E data){
        return queque.isFullQueque();
    }

    public Boolean isFullQueque(){
        return queque.isEmpty();
    }

    public Integer top(){
        return queque.getTop();
    }
    public Integer size(){
        return queque.getLength();
    }
}

