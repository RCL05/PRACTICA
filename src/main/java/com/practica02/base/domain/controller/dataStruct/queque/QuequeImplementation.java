package com.practica02.base.domain.controller.dataStruct.queque;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

final class QuequeImplementation<E> extends LinkedList<E> {
    private Integer top;

    public QuequeImplementation(Integer top) {
        super();
        this.top = top;
    }
    protected Boolean isFullQueque(){
        return this.top >= getLength();
    }

    protected void queque(E info)throws Exception{
        if (!isFullQueque()){
            add(info, 0);
            
        } else {
            throw new ArrayIndexOutOfBoundsException("QUEUQUE is full");
        }
    }
    protected E dequeque() throws Exception{
        //todo
        return deleteFirst();
        
    }

    //get set
    public Integer getTop() {
        return top;
    }

    /*PATRON DE DISENO FACAL O FACHADA, PRIMERO DAMOS LOS METDOOSS QUE QUEROMOS MOSTRAR, 
    Y EL RESTO LOS OCULTAMOS, STACK --> CERCO DE LA CAS
    STACK IMPLEMENTATION --> CASA
     */
}
