package org.example.edu.controller.dao.impl;

import org.example.edu.controller.dao.CrudDao;
import org.example.edu.controller.dto.Product;

public interface ProductDao extends CrudDao<Product,Integer> {
    public int searchQty(Integer id);

}
