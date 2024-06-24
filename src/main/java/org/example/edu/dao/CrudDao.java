package org.example.edu.dao;

import javafx.collections.ObservableList;

public interface CrudDao<T,S> extends SuperDao {

    T Search(S s);

    ObservableList<T> SearchAll();

    boolean insert(T t);

    boolean update(T t);

    boolean delete(S s);
}
