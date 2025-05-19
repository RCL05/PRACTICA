package com.practica02.base.domain.controller.dataStruct.stack;

public class Stack<E> {
    private StackImplementation<E> stack;
    
    public Stack(Integer top) {
        stack = new StackImplementation<E>(top);
    }
    public Boolean push(E data){
        try {
            stack.Push(data);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public E pop(){
        try {
            return stack.pop();
        } catch (Exception e) {
            return null;
        }
    }
    public Boolean isFullStack(){
        return stack.isFullStack();
    }

    public Boolean isEmptyStack(){
        return stack.isEmpty();
    }
    public Integer top(){
        return stack.getTop();
    }
    public Integer size(){
        return stack.getLength();
    }
}

