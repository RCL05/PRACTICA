package com.practica02.base.domain.controller.dao;

import com.practica02.base.domain.controller.dataStruct.list.LinkedList;

public interface InterfaceDao <T> {

    public LinkedList<T> listAll();
    public void persist(T obj) throws Exception;
    public void update(T obj, Integer pos) throws Exception;
    public void update_by_id (T obj, Integer id) throws Exception;
    public T get(Integer id ) throws Exception;
}
