package org.example.edu.dao.Custom;

import javafx.collections.ObservableList;
import org.example.edu.dao.CrudDao;
import org.example.edu.entity.SupplierEntity;

public interface SupplierDao extends CrudDao<SupplierEntity,String> {

    ObservableList<SupplierEntity> searchAll();
}
