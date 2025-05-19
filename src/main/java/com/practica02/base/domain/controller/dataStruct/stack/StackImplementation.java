package com.practica02.base.domain.controller.dataStruct.stack;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

final class StackImplementation<E> extends LinkedList<E> {
    private Integer top;

    public StackImplementation(Integer top) {
        super();
        this.top = top;
    }
    protected Boolean isFullStack(){
        return getLength() > this.top;
    }

    protected void Push(E info)throws Exception{
        if (!isFullStack()){
            add(info, 0);
            
        } else {
            throw new ArrayIndexOutOfBoundsException("Stack is full");
        }
    }
    protected E pop() throws Exception{
        //todo
        return deleteFirst();
        
    }

    //get set
    public Integer getTop() {
        return top;
    }
}
