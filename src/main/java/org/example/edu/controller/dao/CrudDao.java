package org.example.edu.controller.dao;

import org.example.edu.controller.dto.Employee;

public interface CrudDao<T,S> {

    public T search();
    public boolean insert(T t);

    public boolean update(T t);

    public boolean delete(S s);
}
